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

	public void startClientsMulticast() {
		multicast = new Multicast(this,
				NetworkConstants.CLIENT_MULTICAST_ADDRESS,
				NetworkConstants.CLIENT_MULTICAST_PORT, "[MULTICAST_CLIENTS]");
		
		multicast.startSpeaker(NetworkConstants.HELLO, NetworkConstants.PERIODIC_TIME);
	}

	public void startServersMulticast() {
		multicast = new Multicast(this, NetworkConstants.SERVERS_MULTICAST_IP,
				NetworkConstants.SERVERS_MULTICAST_PORT, "[MULTICAST_SERVERS]");
		
		multicast.startSpeaker(NetworkConstants.HELLO, NetworkConstants.PERIODIC_TIME);

	}

	public void start() {
		data.setPassive();

		startServersMulticast();

		new Timer(this, NetworkConstants.PERIODIC_TIME * 4);
	}

	public void notifyTimeIsOver() throws IOException {
		// when time is over
		// if no connection has been made
		if (p2p.getNumberOfConnections() == 0) {
			// delegates himself the "manager"
			setActive();
			System.out.println(label + " I am the new manager");
		} else {
			// asks who is the manager
			getManager();
			System.out.println(label + " There's probably a new manager");
		}
	}

	private void setActive() {
		data.setActive();
		startClientsMulticast();
	}

	public void getManager() throws IOException {
		p2p.sendToAllConnections(NetworkConstants.MANAGER_REQUEST);
	}

	private void addServer(String address) {
		if (data.addServerInfo(address)) {
			// tries to connect to server
			if (!p2p.requestConnection(address)) {
				// if the connection was unsuccessful
				data.removeServerInfo(address);
			}

		}
	}

	// multicast
	public void notifyServerFound(String message, String address) {
		addServer(address);

		if (message.equals(NetworkConstants.HELLO)) {
			multicast.sendMessage(NetworkConstants.HELLO_RESPONSE);
		}
	}

	// tcp
	public void sendServersInfoUpdate() {
		String message = data.serversInfoToString();
		try {
			p2p.sendToAllConnections("SRV/" + message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void notifyDisconnected(String address) {
		data.removeServerInfo(address);
	}

	// TODO mandar sincronizacao

	public void notifyMessageReceived(String message, String localAddress,
			String address) {

		if (message.startsWith("SRV/")) {
			String servers[];
			// it's a serversInfo update message
			message.replace("SRV/", ""); // erases "header"
			servers = message.split("/");

			for (int i = 0; i < servers.length; i++) {
				if (!servers[i].equals(localAddress)) {
					addServer(servers[i]);
				}
			}
		} else if (message.startsWith(NetworkConstants.MANAGER_PREFIX)) {
			if (message.equals(NetworkConstants.MANAGER_REQUEST)) {
				if (data.isActive()) {
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
