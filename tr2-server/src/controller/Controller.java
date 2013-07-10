package controller;

import data.DataHolder;


public class Controller {
	private DataHolder data;
	
	public Controller() {
		data = new DataHolder();
	}
	
	public void addServer(String address) {
		data.addServerInfo(address);
	}
}
