package tr2.server.common.multicast;

public interface MulticastController {

	public void notifyMessageReceived(String message, String address);
}
