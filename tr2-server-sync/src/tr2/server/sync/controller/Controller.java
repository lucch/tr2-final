package tr2.server.sync.controller;

import tr2.server.common.data.DataHolder;
import tr2.server.common.multicast.Multicast;
import tr2.server.common.multicast.MulticastListener;
import tr2.server.common.util.MulticastConstants;
import tr2.server.sync.tcp.TCPConnectionsManager;

public class Controller implements MulticastListener {
	private DataHolder data;
	private Multicast multicast;
	private TCPConnectionsManager p2p;

	public Controller() {
		p2p = new TCPConnectionsManager(this);
		data = new DataHolder();
		multicast = new Multicast(this,
				MulticastConstants.SERVERS_MULTICAST_IP,
				MulticastConstants.SERVERS_MULTICAST_PORT);
	}

	public void notifyServerFound(String message, String address) {
		addServer(address);

		if (message.equals(MulticastConstants.HELLO)) {
			multicast.sendMessage(MulticastConstants.HELLO_RESPONSE);
		}
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

	public void notifyDisconnected(String address) {
		data.removeServerInfo(address);
	}

	public String getPeriodicMessage() {
		return MulticastConstants.HELLO;
	}

	public long getPeriodicTime() {
		return MulticastConstants.PERIODIC_TIME;
	}

	// synchronization
	public void sendServersInfoUpdate() {
		String message = data.serversInfoToString();
		p2p.sendToAllConnections("SRV/" + message);
	}

	public void notifyUpdateReceived(String message) {

		if (message.startsWith("SRV/")) {
			String servers[];
			// it's a serversInfo update message
			message.replace("SRV/", ""); // erases "header"
			servers = message.split("/");

			for (int i = 0; i < servers.length; i++) {
				addServer(servers[i]);
			}
		}
	}
}
