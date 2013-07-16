package tr2.server.common.multicast;

public interface MulticastListener {

	public void notifyServerFound(String message, String address);

	public long getPeriodicTime();

	public String getPeriodicMessage();

}
