package resources;

public class Messages {
	public final static String DB_QUERY_RESPONSE_OK = "OK";
	public final static String DB_UNSUPPORTED_STATEMENT_TYPE = "Unsupported statement type \"%s\"";
	public final static String DB_UNSUPPORTED_RESPONSE_FORMAT = "Unsupported response format \"%s\"";
	public final static String DB_INVALID_CONNECTION_NAME = "No active connection found for connection name \"%s\"";
	
	public final static String REST_ERROR_BAD_REQUEST_TYPE = "ERROR: Request type not set correctly (must be one of GET/PUT/POST/DELETE)";
	public final static String REST_ERROR_INVALID_REQUEST_TYPE = "Error: Request type must be %s";
	public final static String REST_ERROR_REQUEST_BODY_EMPTY = "Error: Request body cannot be empty";
	public final static String REST_ERROR_INVALID_DELETE_URL = "Asset ID must be provided for the asset instance DELETE service";
}
