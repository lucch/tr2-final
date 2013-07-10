package multicast;

import tr2.util.MulticastConstants;
import controller.Controller;

public class Multicast {

	private Controller controller;

	private MCSpeaker speaker;
	
	private MCListener listener;

	public Multicast(Controller controller) {
		this.controller = controller;
		speaker = new MCSpeaker(MulticastConstants.SERVERS_MULTICAST_IP, MulticastConstants.SERVERS_MULTICAST_PORT);
		Thread speakerThread = new Thread(speaker);
		speakerThread.start();
		listener = new MCListener(this, MulticastConstants.SERVERS_MULTICAST_IP, MulticastConstants.SERVERS_MULTICAST_PORT);
		Thread listenerThread = new Thread(listener);
		listenerThread.start();
	}

	public void sendMessage(String message) {
		speaker.speak(message);
	}

	public void parser(String message, String sourceAddress) {
		controller.addServer(message, sourceAddress);
	}
}
