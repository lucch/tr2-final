package tr2.server.common.multicast;

public class Multicast {

	private MulticastController multicastListener;

	private Speaker speaker;

	private Listener listener;

	private String periodicMessage;

	private long periodicTime;

	private final String label;

	public Multicast(MulticastController controller, String address, int port,
			String label) {

		this.multicastListener = controller;
		this.label = label;
		
		speaker = new Speaker(this, address, port);
		
		listener = new Listener(this, address, port);
		Thread listenerThread = new Thread(listener);
		listenerThread.start();
	}

	public void startSpeaker(String periodicMessage, long periodicTime) {
		this.periodicMessage = periodicMessage;
		this.periodicTime = periodicTime;
		Thread speakerThread = new Thread(speaker);
		speakerThread.start();
	}

	public void sendMessage(String message) {
		speaker.speak(message);
	}

	public void parser(String message, String sourceAddress) {
		System.out.println(label + " RCVD FROM " + sourceAddress + ": " + message);
		multicastListener.notifyMessageReceived(message, sourceAddress);
	}
	
	public void notifyMessageSpoken(String message) {
		System.out.println(label + " SENT: " + message);
	}
	
	public String getPeriodicMessage() {
		return periodicMessage;
	}

	public long getPeriodicTime() {
		return periodicTime;
	}

}
