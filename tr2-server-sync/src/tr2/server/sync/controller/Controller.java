package tr2.server.sync.controller;

import java.io.IOException;

import tr2.server.common.data.DataHolder;
import tr2.server.common.multicast.Multicast;
import tr2.server.common.multicast.MulticastController;
import tr2.server.common.tcp.ConnectionsManager;
import tr2.server.common.tcp.TCPController;
import tr2.server.common.util.NetworkConstants;


public class Controller implements MulticastController, TCPController {
	
	private DataHolder data;
	
	private Multicast multicast;
	
	private ConnectionsManager p2p;

	public Controller(int portTCP) throws IOException {
		p2p = new ConnectionsManager(this, portTCP);
		data = new DataHolder();
	}

	public void startMulticast() {
		multicast = new Multicast(this,
				NetworkConstants.SERVERS_MULTICAST_IP,
				NetworkConstants.SERVERS_MULTICAST_PORT);
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
	public String getPeriodicMessage() {
		return NetworkConstants.HELLO;
	}

	public long getPeriodicTime() {
		return NetworkConstants.PERIODIC_TIME;
	}

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

	public void notifyMessageReceived(String message, String localAddress) {

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
		}
	}
}
