package tr2.server.common.util;

public class NetworkConstants {

	public final static int TCP_SERVER_TO_SERVER_PORT = 7000;
	
	public final static int TCP_TIMEOUT = 500;
	
	/* Multicast settings */
	public final static String CLIENT_MULTICAST_ADDRESS = "224.2.2.3";

	public final static int CLIENT_MULTICAST_PORT = 9000;
	
	public static final String SERVERS_MULTICAST_IP = "224.2.2.4";
	
	public static final int SERVERS_MULTICAST_PORT = 10000;
	
	/* Sync messages */
	public static final String HELLO = "HELLO";
	
	public static final String SERVER_PREFIX = "SRV/";
	
	public static final String MANAGER_PREFIX = "MNG/";
	
	public static final String MANAGER_REQUEST = "MNG/REQUEST";
	
	public static final String MANAGER_RESPONSE = "MNG/YES";
	
	public static final String MANAGER_STATEMENT = "MNG/STATEMENT";
	
	public static final String HELLO_RESPONSE = "HELLOACK";
	
	public static final long PERIODIC_TIME = 2000;

	/* Proxy/Remote Servers settings */
	public final static int PROXY_PORT = 8081;

	public static final int LOCAL_CLIENT_PORT = 8080;
	
	public static final int REMOTE_HTTP_SERVER_PORT = 8082;

	public static final int REMOTE_SERIES_SERVER_PORT = 8083;

}
