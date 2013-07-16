package tr2.server.common.multicast;

public class Multicast {

	private MulticastController multicastListener;

	private Speaker speaker;
	
	private Listener listener;
	
	public Multicast(MulticastController controller, String address, int port) {
		this.multicastListener = controller;
		speaker = new Speaker(this, address, port);
		Thread speakerThread = new Thread(speaker);
		speakerThread.start();
		listener = new Listener(this, address, port);
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
