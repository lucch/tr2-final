package tr2.multicast;

import tr2.controller.Controller;

public class Multicast {

	private Controller controller;

	private MCSpeaker speaker;
	
	private MCListener listener;
	
	public Multicast(Controller controller, String address, int port) {
		this.controller = controller;
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
		controller.notifyServerFound(message, sourceAddress);
	}
	
	public String getPeriodicMessage() {
		return controller.getPeriodicMessage();
	}
	
	public long getPeriodicTime() {
		return controller.getPeriodicTime();
	}
	
}
