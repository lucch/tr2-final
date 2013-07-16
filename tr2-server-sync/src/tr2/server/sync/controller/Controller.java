package tr2.server.sync.controller;

import java.io.IOException;

import tr2.server.common.data.Data;
import tr2.server.common.multicast.Multicast;
import tr2.server.common.multicast.MulticastController;
import tr2.server.common.tcp.ConnectionsManager;
import tr2.server.common.tcp.TCPController;
import tr2.server.common.util.NetworkConstants;

public class Controller implements MulticastController, TCPController,
		TimerController {

	private Data data;

	private Multicast multicast;

	private ConnectionsManager p2p;

	private String label = "[CONTROLLER]";

	public Controller(int portTCP) throws IOException {
		p2p = new ConnectionsManager(this, portTCP);
		data = new Data();
	}

	public void startMulticast() {
		multicast = new Multicast(this,
				NetworkConstants.CLIENT_MULTICAST_ADDRESS,
				NetworkConstants.CLIENT_MULTICAST_PORT, "[MULTICAST_CLIENTS]");
	}

	public void start() {
		data.setPassive();

		startMulticast();

		new Timer(this, NetworkConstants.PERIODIC_TIME * 5);
	}

	private void setActive() {
		data.setActive();

		startMulticast();

		multicast.startSpeaker(NetworkConstants.HELLO,
				NetworkConstants.PERIODIC_TIME);
	}

	private void connectAndAddServer(String address) {
		if (data.addServerInfo(address)) {
			// tries to connect to server
			if (p2p.requestConnection(address)) {
				if (p2p.getNumberOfConnections() == 1 && !data.isManager()) {
					// it is the manager
					data.setServerActive(address);
				}
			} else {
				// if the connection was unsuccessful
				data.removeServerInfo(address);
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

	public void notifyServerFound(String message, String address) {
		connectAndAddServer(address);

		if (message.equals(NetworkConstants.HELLO)) {
			multicast.sendMessage(NetworkConstants.HELLO_RESPONSE);
		}
	}

	// tcp
	public void sendServersInfoUpdate() {
		String message = data.serversInfoToString();
		try {
			p2p.sendToAllConnections(NetworkConstants.SERVER_PREFIX + message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void notifyDisconnected(String address) {
		data.removeServerInfo(address);
	}

	public void notifyConnected(String address) {
		data.addServerInfo(address);
	}

	// TODO mandar sincronizacao

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
				if (data.isManager()) {
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
