package com.ibm.cognitivecities.fitnesse.scenarios.util;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.*;

public class FileScenarios {
	public long delay = 0L;
	public String response = "";
	public String responseMessage = "";

	public void copyFile(String source, String target) {
		if (source == null || target == null) {
			errorResponse("Source or target cannot be null");
			return;
		}
		try {
			Path sourcePath = FileSystems.getDefault().getPath(source);
			Path targetPath = FileSystems.getDefault().getPath(target);
			Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
			okResponse(String.format("\"%s\" copied successfully to \"%s\"", source, target));
		} catch (IOException e) {
			errorResponse(e.getMessage());
		}
	}
	
	private void okResponse(String message) {
		responseMessage = "OK";
		response = message;
	}
	
	private void errorResponse(String message) {
		responseMessage = "KO";
		response = message;
	}
	
	public void replaceStringsInFile(String filePath, String searchReplacePairs, String separator, String pairSeparator) {
		if (separator == null || separator.trim().length() == 0) {
			separator = ":";
		}
		if (pairSeparator == null || pairSeparator.trim().length() == 0) {
			pairSeparator = ";";
		}
		try {
			Path path = Paths.get(filePath);
			Charset charset = StandardCharsets.UTF_8;
			String content = new String(Files.readAllBytes(path), charset);
			
			String[] pairs = searchReplacePairs.split(pairSeparator);
			for (int i=0; i<pairs.length; i++) {
				String[] keyValuePair = pairs[i].split(separator);
				if (keyValuePair[0] != null && keyValuePair[1] != null)
					content = content.replaceAll(keyValuePair[0],keyValuePair[1]);						
			}
			Files.write(path, content.getBytes(charset));
			okResponse(String.format("Search/Replace pattern \"%s\" applied to file \"%s\"", searchReplacePairs, filePath));
		} catch (Exception e) {
			errorResponse(e.getMessage());
		}
	}

	public void copyFileRemote(String source, String target, String host, String user, String keyFile, String keyPwd) {
		response = "";
		if (source == null || target == null) {
			errorResponse("Source or target cannot be null");
			return;
		}
		
		try {
			if (keyFile != null && keyFile.length() == 0)
				keyFile = null;
			Session session = createSession(user, host, keyFile, keyPwd);
			if (session == null) {
				errorResponse(String.format("Failed to create session with remote host \"%s\"", host));
			}
			else if (copyLocalToRemote(session, source, target)) {
				okResponse(String.format("\"%s\" copied successfully to \"%s\"", source, target));
			} else {
				errorResponse(response + String.format("\r\nError while copying \"%s\" to \"%s\"", source, target));				
			}
		} catch (Exception e) {
			errorResponse(e.getMessage());
		}
	}
	
	public void deleteFile(String sourceFile) {
		if (sourceFile == null) {
			errorResponse("Source file cannot be null");
			return;
		}
		try {
			Path sourcePath = FileSystems.getDefault().getPath(sourceFile);
			Files.delete(sourcePath);
			okResponse(String.format("\"%s\" successfully deleted", sourceFile));
		} catch (IOException e) {
			errorResponse(e.getMessage());
		}
	}

	public String fileUtilResponse() {
		return response;
	}
	
	public String fileUtilResponseMessage() {
		return responseMessage;
	}

	private Session createSession(String user, String host, String keyFile, String keyPassword) {
    	int port = 22;
        JSch jsch = new JSch();
    	
        try {

            if (keyFile != null) {
                if (keyPassword != null) {
                    jsch.addIdentity(keyFile, keyPassword);
                } else {
                    jsch.addIdentity(keyFile);
                }
            }

            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");

            Session session = jsch.getSession(user, host, port);
            session.setConfig(config);
            session.connect();

            return session;
        } catch (JSchException e) {
            System.out.println(e);
            return null;
        }
    }
		
    private boolean copyLocalToRemote(Session session, String source, String target) throws JSchException, IOException {
        boolean ptimestamp = true;
        boolean retValue = true;

        // exec 'scp -t rfile' remotely
        String command = "scp " + (ptimestamp ? "-p" : "") + " -t " + target;
        Channel channel = session.openChannel("exec");
        ((ChannelExec) channel).setCommand(command);

        // get I/O streams for remote scp
        OutputStream out = channel.getOutputStream();
        InputStream in = channel.getInputStream();

        channel.connect();

        if (checkAck(in) != 0) {
            retValue = false;
        }

        File _lfile = new File(source);
        if (retValue) {
            if (ptimestamp) {
                command = "T" + (_lfile.lastModified() / 1000) + " 0";
                // The access time should be sent here,
                // but it is not accessible with JavaAPI ;-<
                command += (" " + (_lfile.lastModified() / 1000) + " 0\n");
                out.write(command.getBytes());
                out.flush();
                if (checkAck(in) != 0) {
                    retValue = false;
                }
            }
        }

        if (retValue) {
            // send "C0644 filesize filename", where filename should not include '/'
            long filesize = _lfile.length();
            command = "C0644 " + filesize + " ";
            if (source.lastIndexOf('/') > 0) {
                command += source.substring(source.lastIndexOf('/') + 1);
            } else {
                command += source;
            }

            command += "\n";
            out.write(command.getBytes());
            out.flush();

            if (checkAck(in) != 0) {
                retValue = false;
            }
        }

        FileInputStream fis = null;
        if (retValue) {
            // send a content of lfile
            fis = new FileInputStream(source);
            byte[] buf = new byte[1024];
            while (true) {
                int len = fis.read(buf, 0, buf.length);
                if (len <= 0) break;
                out.write(buf, 0, len); //out.flush();
            }

            // send '\0'
            buf[0] = 0;
            out.write(buf, 0, 1);
            out.flush();

            if (checkAck(in) != 0) {
                retValue = false;
            }
        }
        out.close();

        try {
            if (fis != null) fis.close();
        } catch (Exception e) {
        	retValue = false;
            appendErrorToResponse(e.getMessage());
        }

        channel.disconnect();
        session.disconnect();
        
        return retValue;
    }

    public int checkAck(InputStream in) throws IOException {
        int b = in.read();
        // b may be 0 for success,
        //          1 for error,
        //          2 for fatal error,
        //         -1
        if (b == 0) return b;
        if (b == -1) return b;

        if (b == 1 || b == 2) {
            StringBuffer sb = new StringBuffer();
            int c;
            do {
                c = in.read();
                sb.append((char) c);
            }
            while (c != '\n');
            if (b == 1) { // error
                appendErrorToResponse(sb.toString());
            }
            if (b == 2) { // fatal error
                appendErrorToResponse(sb.toString());
            }
        }
        return b;
    }
	
	public void appendErrorToResponse(String message) {
		response = response.length() == 0 ? message 
					: response + "\r\n" + message;
	}
}
