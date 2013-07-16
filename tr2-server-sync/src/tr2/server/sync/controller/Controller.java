package tr2.server.sync.controller;

import java.net.UnknownHostException;

import tr2.server.common.data.DataHolder;
import tr2.server.common.multicast.Multicast;
import tr2.server.common.multicast.MulticastController;
import tr2.server.common.util.MulticastConstants;
import tr2.server.common.util.NetworkConstants;
import tr2.server.sync.tcp.ConnectionsManager;
import tr2.server.sync.tcp.TCPController;

public class Controller implements MulticastController, TCPController {
	private DataHolder data;
	private Multicast multicast;
	private ConnectionsManager p2p;

	public Controller() throws UnknownHostException {
		p2p = new ConnectionsManager(this,
				NetworkConstants.TCP_SERVER_TO_SERVER_PORT);
		data = new DataHolder();
		multicast = new Multicast(this,
				MulticastConstants.SERVERS_MULTICAST_IP,
				MulticastConstants.SERVERS_MULTICAST_PORT);
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
		return MulticastConstants.HELLO;
	}

	public long getPeriodicTime() {
		return MulticastConstants.PERIODIC_TIME;
	}

	public void notifyServerFound(String message, String address) {
		addServer(address);

		if (message.equals(MulticastConstants.HELLO)) {
			multicast.sendMessage(MulticastConstants.HELLO_RESPONSE);
		}
	}
	
	// tcp
	public void sendServersInfoUpdate() {
		String message = data.serversInfoToString();
		p2p.sendToAllConnections("SRV/" + message);
	}

	public void notifyDisconnected(String address) {
		data.removeServerInfo(address);
	}
	
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
