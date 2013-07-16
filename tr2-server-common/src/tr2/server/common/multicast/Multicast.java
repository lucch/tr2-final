package tr2.server.common.multicast;

public class Multicast {

	private MulticastListener multicastListener;

	private MCSpeaker speaker;
	
	private MCListener listener;
	
	public Multicast(MulticastListener controller, String address, int port) {
		this.multicastListener = controller;
		speaker = new MCSpeaker(this, address, port);
		Thread speakerThread = new Thread(speaker);
		speakerThread.start();
		listener = new MCListener(this, address, port);
		Thread listenerThread = new Thread(listener);
		listenerThread.start();
	}

	public void sendMessage(String message) {
		speaker.speak(message);
	}

	public void parser(String message, String sourceAddress) {
		multicastListener.notifyServerFound(message, sourceAddress);
	}
	
	public String getPeriodicMessage() {
		return multicastListener.getPeriodicMessage();
	}
	
	public long getPeriodicTime() {
		return multicastListener.getPeriodicTime();
	}
	
}
