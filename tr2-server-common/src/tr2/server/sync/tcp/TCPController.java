package tr2.server.sync.tcp;

public interface TCPController {
	public void notifyDisconnected(String address);
	public void notifyMessageReceived(String address, String localAddress);
}
