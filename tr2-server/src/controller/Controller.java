package controller;

import tr2.util.MulticastConstants;
import multicast.Multicast;
import data.DataHolder;

public class Controller {
	private DataHolder data;
	private Multicast multicast;

	public Controller() {
		data = new DataHolder();
		multicast = new Multicast(this);
	}

	public void addServer(String message, String address) {
		data.addServerInfo(address);
		if (message.equals(MulticastConstants.HELLO)) {
			multicast.sendMessage(MulticastConstants.HELLO_RESPONSE);
		}
	}
	
	public void start() {
		
	}
	
}
