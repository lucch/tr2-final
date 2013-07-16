package tr2.server.sync.tcp;

import java.net.Socket;
import java.util.ArrayList;

import tr2.server.common.util.NetworkConstants;
import tr2.server.sync.controller.Controller;

public class TCPConnectionsManager {

	private TCPConnector connector;

	private ArrayList<TCPConnection> connections;

	private Controller controller;

	public TCPConnectionsManager(Controller controller) {
		this.controller = controller;
		connector = new TCPConnector(this,
				NetworkConstants.TCP_SERVER_TO_SERVER_PORT,
				NetworkConstants.TCP_TIMEOUT);
		Thread connectorThread = new Thread(connector);
		connectorThread.start();

		connections = new ArrayList<TCPConnection>();
	}

	public TCPConnectionsManager(int serverPort) {
		connector = new TCPConnector(this, serverPort,
				NetworkConstants.TCP_TIMEOUT);
		Thread connectorThread = new Thread(connector);
		connectorThread.start();

		connections = new ArrayList<TCPConnection>();
	}

	public void newConnection(Socket socket) {
		TCPConnection connection = new TCPConnection(this, socket);

		// checks if connection already exists
		for (int i = 0; i < connections.size(); i++) {
			if (connections.get(i).equals(connection)) {
				System.out.println("(!) Connection " + connection
						+ " already exists");
				return;
			}
		}

		// initiates connection
		connection.start();
		connections.add(connection);
		System.out.println("(!) Connection made to: " + connection);
	}

	public int findConnection(Socket socket) {
		TCPConnection conn = new TCPConnection(this, socket);

		int i;
		for (i = 0; i < connections.size(); i++) {
			if (connections.get(i).equals(conn)) {
				break;
			}
		}

		return i;
	}

	public void removeDisconnected() {
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
			TCPConnection connection = connections.get(i);
			connection.speak(message);
		}
	}
	
	public void parser(String message) {
		System.out.println("Received: " + message);
		controller.notifyUpdateReceived(message);
	}
}
