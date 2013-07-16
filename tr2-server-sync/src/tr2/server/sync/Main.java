package tr2.server.sync;

import java.net.UnknownHostException;

import tr2.server.common.tcp.TCPController;
import tr2.server.sync.controller.Controller;


public class Main {

	public static void main(String[] args) {

		try {
			new Controller();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//		TCPConnectionsManager manager = new TCPConnectionsManager(9001);

		//		TCPConnectionsManager manager = new TCPConnectionsManager(9002);
		//		manager.requestConnection("192.168.0.1", 9001);
		//		manager.requestConnection("localhost", 9001);
		//		manager.requestConnection("localhost", 9001);
		//		manager.requestConnection("localhost", 9001);
		//		manager.sendToAllConnections("teste");

	}

}
