package tr2.controller;

import tr2.data.DataHolder;
import tr2.multicast.Multicast;
import tr2.util.MulticastConstants;

public class Controller {
	private DataHolder data;
	private Multicast multicast;

	public Controller() {
		data = new DataHolder();
		multicast = new Multicast(this,
				MulticastConstants.SERVERS_MULTICAST_IP,
				MulticastConstants.SERVERS_MULTICAST_PORT);
		;
	}

	public void addServer(String message, String address) {
		if (data.addServerInfo(address) != null) {
			// estabelece conex‹o
		}

		if (message.equals(MulticastConstants.HELLO)) {
			multicast.sendMessage(MulticastConstants.HELLO_RESPONSE);
		}
	}

	public String getPeriodicMessage() {
		return MulticastConstants.HELLO;
	}

	public long getPeriodicTime() {
		return MulticastConstants.PERIODIC_TIME;
	}

	public void start() {

	}

}
