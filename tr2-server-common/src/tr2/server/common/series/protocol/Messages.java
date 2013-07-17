package tr2.server.common.series.protocol;

/**
 * This class specifies the messages used in the communication protocol between
 * Client and Series Server.
 * 
 * Every message must be of the form:
 * 
 * <COMMAND> 	  + "\n"
 * <PAYLOAD> 	  + "\n"
 * <FINAL MARKER> + "\n"
 * 
 * For instance:
 * 
 * INTERVAL CALCULATED
 * <JSON string holding the data>
 * END
 * 
 * @author alexandrelucchesi
 * 
 */
public class Messages {

	public static final String END = "END";

	public static final String GET_INTERVAL = "GET INTERVAL";

	public static final String INTERVAL_CALCULATED = "INTERVAL CALCULATED";

}
