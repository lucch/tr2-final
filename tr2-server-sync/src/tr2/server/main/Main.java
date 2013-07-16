package tr2.server.main;

import java.io.IOException;
import java.net.UnknownHostException;
import tr2.server.common.util.NetworkConstants;
import tr2.server.sync.controller.P2PController;

public class Main {

	public static void main(String[] args) {

		try {
			P2PController servers = new P2PController(
					NetworkConstants.TCP_SERVER_TO_SERVER_PORT, 
					NetworkConstants.REMOTE_SERIES_SERVER_PORT);
			servers.start();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TCPConnectionsManager manager = new TCPConnectionsManager(9001);

		// TCPConnectionsManager manager = new TCPConnectionsManager(9002);
		// manager.requestConnection("192.168.0.1", 9001);
		// manager.requestConnection("localhost", 9001);
		// manager.requestConnection("localhost", 9001);
		// manager.requestConnection("localhost", 9001);
		// manager.sendToAllConnections("teste");

	}

}
