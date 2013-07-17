package tr2.server.common.tcp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import tr2.server.common.util.NetworkConstants;

public class ConnectionsManager {

	private Connector connector;

	private ArrayList<Connection> connections;

	private TCPController controller;

	private String localAddress;

	private final String label = "[CONNECTIONS MANAGER]";

	public ConnectionsManager(TCPController controller, int port)
			throws IOException {

		localAddress = InetAddress.getLocalHost().getHostAddress();

		this.controller = controller;
		connector = new Connector(this, port, NetworkConstants.TCP_TIMEOUT);
		Thread connectorThread = new Thread(connector);
		connectorThread.start();

		connections = new ArrayList<Connection>();
	}

	public void newConnection(Socket socket) throws IOException {
		Connection connection = new Connection(this, socket);

		// checks if connection already exists
		for (int i = 0; i < connections.size(); i++) {
			if (connections.get(i).equals(connection)) {
				System.out.println(label + " Connection " + connection
						+ " already exists");
				return;
			}
		}

		// if connection doesn't exists, initiates connection
		connection.start();
		connections.add(connection);
		controller.notifyConnected(connection.getAddress());
		System.out.println(label + " Connection made to: " + connection);
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

	public int findConnection(String address) {
		int i;
		for (i = 0; i < connections.size(); i++) {
			if (connections.get(i).getAddress().equals(address)) {
				break;
			}
		}

		return i;
	}

	private void removeDisconnected() {
		for (int i = 0; i < connections.size(); i++) {
			if (!connections.get(i).isConnected()) {
				System.out.println(label + " Connection to "
						+ connections.get(i) + " removed");
				controller.notifyDisconnected(connections.get(i).getAddress());
				connections.remove(i);

			}
		}
	}

	public void connectionDown() {
		System.out.println(label + " Connection is down!");
		removeDisconnected();
	}

	public boolean requestConnection(String address) {
		boolean connected = connector.connectTo(address);
		if (!connected)
			System.out.println(label + " Connection to " + address + " failed");

		return connected;
	}

	public boolean requestConnection(String address, int port) {
		boolean connected = connector.connectTo(address, port);
		if (!connected)
			System.out.println(label + " Connection to " + address + " failed");

		return connected;
	}

	public void sendToAllConnections(String message) throws IOException {
		for (int i = 0; i < connections.size(); i++) {
			Connection connection = connections.get(i);
			connection.speak(message);
			System.out.println(label + " Sent to " + connection.getAddress()
					+ ": " + message);

		}
	}

	public void sendTo(String message, String address) throws IOException {
		int i = findConnection(address);

		if (i >= connections.size()) {
			// address invalid
			System.out.println(label + " Failed to send message");
			System.out.println(label + " Invalid address");
		} else {
			connections.get(i).speak(message);
			System.out.println(label + " Sent to " + address + ": " + message);

		}
	}

	public ArrayList<Connection> getConnections() {
		return connections;
	}

	private String getLocalAddress() {
		return localAddress;
	}

	public void parser(String message, String address) {
		System.out
				.println(label + " Received from " + address + ": " + message);
		controller.notifyMessageReceived(message, getLocalAddress(), address);
	}

	public int getNumberOfConnections() {
		return connections.size();
	}
}
