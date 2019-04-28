package resources;

import org.apache.log4j.Level;

public class Constants {

	public static Level LOG_LEVEL = Level.INFO;
	
	public final static String ASSET_INSTANCE_MANAGE_URL = "https://%s/ibm/water/api/asset/%s/model/%s/assetType/%s/assetInstanceManage";
	public final static String ASSET_INSTANCE_GET_COLLECTION_URL = "https://%s/ibm/water/api/asset/%s/model/%s/assetType/%s/assetInstance%s";
	public final static String ASSET_INSTANCE_GET_ITEM_URL = "https://%s/ibm/water/api/asset/%s/model/%s/assetType/%s/assetInstance/%s%s";
	public final static String ASSET_INSTANCE_DELETE_URL = "https://%s/ibm/water/api/asset/%s/model/%s/assetType/%s/assetInstanceManage/%s";
	public final static String GENERIC_SERVICE_URL = "%s://%s:%s%s%s"; 

	public final static String DEFAULT_ID_NAME = "id";
	public final static String DISABLE_IDSAVE_HEADER = "saveid:false";
	
	public final static String TESTFILES_PATH = "/Fitnesse_WikiFramework/testfiles/";
}
