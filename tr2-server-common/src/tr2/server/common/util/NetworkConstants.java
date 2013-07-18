package tr2.server.common.util;

import tr2.server.common.series.protocol.Messages;

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

	public static final String SERVER_UPDATE_PREFIX = "SRV"
			+ Messages.SEPARATOR;

	public static final String INTERVALS_UPDATE_PREFIX = "INT"
			+ Messages.SEPARATOR;
	
	public static final String USERS_UPDATE_PREFIX = "USERS" + Messages.SEPARATOR;
	
	public static final String MANAGER_PREFIX = "MNG" + Messages.SEPARATOR;

	public static final String MANAGER_REQUEST = "MNG" + Messages.SEPARATOR
			+ "REQUEST";

	public static final String MANAGER_RESPONSE = "MNG" + Messages.SEPARATOR
			+ "YES";

	public static final String MANAGER_STATEMENT = "MNG" + Messages.SEPARATOR
			+ "STATEMENT";

	public static final String HELLO_RESPONSE = "HELLOACK";

	public static final long PERIODIC_TIME = 2000;

	public static final long SYNC_TIME = 30000;

	/* Proxy/Remote Servers settings */
	public final static int PROXY_PORT = 8081;

	public static final int LOCAL_CLIENT_PORT = 8080;

	public static final int REMOTE_HTTP_SERVER_PORT = 8082;

	public static final int REMOTE_SERIES_SERVER_PORT = 8083;

	public static final String INTERVAL_ADD_PREFIX = "IADD" + Messages.SEPARATOR;

	public static final String PENDING_INTERVAL_ADD_PREFIX = "PIAD" + Messages.SEPARATOR;

	public static final String INTERVAL_REMOVE_PREFIX = "IREM" + Messages.SEPARATOR;

	public static final String PENDING_INTERVAL_REMOVE_PREFIX = "PIRE" + Messages.SEPARATOR;

}
