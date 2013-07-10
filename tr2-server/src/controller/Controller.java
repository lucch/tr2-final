package controller;

import multicast.Multicast;
import data.DataHolder;

public class Controller {
	private DataHolder data;
	private Multicast multicast;

	public Controller() {
		data = new DataHolder();
		multicast = new Multicast(this);
	}

	public void addServer(String address) {
		data.addServerInfo(address);
	}
}
