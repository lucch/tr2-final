package tr2.server;

import tr2.tcpconnection.TCPConnectionsManager;
import tr2.util.NetworkConstants;

public class Main {

	public static void main(String[] args) {

//		Controller controller = new Controller();
//		controller.start();

//		TCPConnectionsManager manager = new TCPConnectionsManager(9001);
		
		TCPConnectionsManager manager = new TCPConnectionsManager(9002);
		manager.requestConnection("localhost", 9001);
		manager.sendToAllConnections("teste");
	
	}

}
