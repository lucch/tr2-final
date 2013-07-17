package tr2.server.sync.controller;

import java.io.IOException;

import tr2.server.common.data.ServerData;
import tr2.server.common.multicast.Multicast;
import tr2.server.common.multicast.MulticastController;
import tr2.server.common.tcp.ConnectionsManager;
import tr2.server.common.tcp.TCPController;
import tr2.server.common.util.NetworkConstants;

public class P2PController implements MulticastController, TCPController,
		TimerController {

	private ServerData serverData;

	private Multicast multicast;

	private ConnectionsManager p2p;
	
	private String label = "[P2P CONTROLLER]";

	public P2PController(int serversPort, int clientsPort) throws IOException {
		p2p = new ConnectionsManager(this, serversPort);
		serverData = new ServerData();
	}

	public void startMulticast() {
		multicast = new Multicast(this,
				NetworkConstants.CLIENT_MULTICAST_ADDRESS,
				NetworkConstants.CLIENT_MULTICAST_PORT, "[MULTICAST_MANAGER]");
	}

	public void start() {
		serverData.setPassive();

		startMulticast();

		new Timer(this, NetworkConstants.PERIODIC_TIME * 5);
	}

	private void setActive() {
		serverData.setActive();

		startMulticast();

		multicast.startSpeaker(NetworkConstants.HELLO,
				NetworkConstants.PERIODIC_TIME);
	}

	private void connectAndAddServer(String address) {
		if (serverData.addServerInfo(address)) {
			// tries to connect to server
			if (p2p.requestConnection(address)) {
				if (p2p.getNumberOfConnections() == 1 && !serverData.isActive()) {
					// it is the manager
					serverData.setServerActive(address);
				}
			} else {
				// if the connection was unsuccessful
				serverData.removeServerInfo(address);
			}
		}
	}

	// multicast
	public void notifyTimeIsOver() throws IOException {
		// when time is over
		// if no connection has been made
		if (p2p.getNumberOfConnections() == 0) {
			// delegates himself the "manager"
			setActive();
			System.out.println(label + " I am the new manager");
		} else {
			System.out.println(label + " There's a manager");
		}
	}

	public void notifyMessageReceived(String message, String address) {
		connectAndAddServer(address);
	}

	// tcp
	public void sendServersInfoUpdate() {
		String message = serverData.serversInfoToString();
		try {
			p2p.sendToAllConnections(NetworkConstants.SERVER_PREFIX + message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void notifyDisconnected(String address) {
		// disconnected after being connected
		// the disconnected server can be the manager
		if (!serverData.removeServerInfo(address)) {
			System.out.println(label + " Manager Dropped");
			System.out.println(label + " Initializing Recovery Routine...");
			try {
				Thread.sleep((long)(Math.random() * (NetworkConstants.TCP_TIMEOUT * 20)));
				if (serverData.getActiveIndex() == -1) {
					// delegates this server the manager
					setActive();
					p2p.sendToAllConnections(NetworkConstants.MANAGER_STATEMENT);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	// connection made notification
	// adds it to data and sends update to all connected
	public void notifyConnected(String address) {
		// is connected
		serverData.addServerInfo(address);
		if (serverData.isActive()) {
			// send all data
			sendServersInfoUpdate();
		}
	}

	public void notifyMessageReceived(String message, String localAddress,
			String address) {

		if (message.startsWith(NetworkConstants.SERVER_PREFIX)) {
			String servers[];
			// it's a serversInfo update message
			message.replace(NetworkConstants.SERVER_PREFIX, ""); // erases
																	// "header"
			servers = message.split("/");

			for (int i = 0; i < servers.length; i++) {
				if (!servers[i].equals(localAddress)) {
					connectAndAddServer(servers[i]);
				}
			}
		} else if (message.startsWith(NetworkConstants.MANAGER_PREFIX)) {
			if (message.equals(NetworkConstants.MANAGER_REQUEST)) {
				if (serverData.isActive()) {
					// send manager_response
				}
			} else if (message.equals(NetworkConstants.MANAGER_RESPONSE)) {
				// puts sender as manager
			} else if (message.equals(NetworkConstants.MANAGER_STATEMENT)) {
				// puts sender as manager
			}
		}
	}
}
