package multicast;

import controller.Controller;

public class Multicast {
	private final String hello = "HELLO";
	private final String helloResponse = "HELLOACK";

	private final String serversMulticastIP = "224.2.2.3";
	private final int serversMulticastPort = 10000;

	private Controller controller;

	private String serverAddress = serversMulticastIP;
	private int serverPort = serversMulticastPort;
	private MCSpeaker speaker;
	private MCListener listener;
	private Thread listenerThread;

	public Multicast(Controller controller) {
		this.controller = controller;
		speaker = new MCSpeaker(serverAddress, serverPort);
		listener = new MCListener(this, serverAddress, serverPort);
		listenerThread = new Thread(listener);
		listenerThread.start();
		sendHello();
	}

	private void sendMessage(String message) {
		speaker.speak(message);
	}

	private void sendHello() {
		sendMessage(hello);
	}

	public void parser(String message, String sourceAddress) {
		controller.addServer(sourceAddress);

		if (message.equals(hello)) {
			sendMessage(helloResponse);
		}
	}
}
