package tr2.server.sync.tcp;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import tr2.server.common.util.NetworkConstants;

public class ConnectionsManager {

	private Connector connector;

	private ArrayList<Connection> connections;

	private TCPController controller;

	private String localAddress;

	public ConnectionsManager(TCPController controller, int port)
			throws UnknownHostException {

		localAddress = InetAddress.getLocalHost().getHostAddress();

		this.controller = controller;
		connector = new Connector(this, port, NetworkConstants.TCP_TIMEOUT);
		Thread connectorThread = new Thread(connector);
		connectorThread.start();

		connections = new ArrayList<Connection>();
	}

	public void newConnection(Socket socket) {
		Connection connection = new Connection(this, socket);

		// checks if connection already exists
		for (int i = 0; i < connections.size(); i++) {
			if (connections.get(i).equals(connection)) {
				System.out.println("(!) Connection " + connection
						+ " already exists");
				return;
			}
		}

		// if connection doesn't exists, initiates connection
		connection.start();
		connections.add(connection);
		System.out.println("(!) Connection made to: " + connection);
	}

	public int findConnection(Socket socket) {
		Connection conn = new Connection(this, socket);

		int i;
		for (i = 0; i < connections.size(); i++) {
			if (connections.get(i).equals(conn)) {
				break;
			}
		}

		return i;
	}

	private void removeDisconnected() {
		for (int i = 0; i < connections.size(); i++) {
			if (!connections.get(i).isConnected()) {
				System.out.println("(!) Connection to " + connections.get(i)
						+ " removed");
				connections.remove(i);
				controller.notifyDisconnected(connections.get(i).getAddress());

			}
		}
	}

	public void connectionDown() {
		System.out.println("(!) Connection is down!");
		removeDisconnected();
	}

	public boolean requestConnection(String address) {
		boolean connected = connector.connectTo(address);
		if (!connected)
			System.out.println("(!) Connection to " + address + " failed");

		return connected;
	}

	public boolean requestConnection(String address, int port) {
		boolean connected = connector.connectTo(address, port);
		if (!connected)
			System.out.println("(!) Connection to " + address + " failed");

		return connected;
	}

	public void sendToAllConnections(String message) {
		for (int i = 0; i < connections.size(); i++) {
			Connection connection = connections.get(i);
			connection.speak(message);
		}
	}

	private String getLocalAddress() {
		return localAddress;
	}

	public void parser(String message) {
		System.out.println("Received: " + message);
		controller.notifyMessageReceived(message, getLocalAddress());
	}
}
