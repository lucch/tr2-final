package tr2.tcpconnection;

import java.net.Socket;
import java.util.ArrayList;

import tr2.util.NetworkConstants;

public class TCPConnectionsManager {

	private TCPConnector connector;

	private ArrayList<TCPConnection> connections;

	public TCPConnectionsManager() {
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

	public void removeObsoleteConnections() {
		for (int i = 0; i < connections.size(); i++) {
			if (!connections.get(i).isConnected()) {
				System.out.println("(!) Connection to " + connections.get(i)
						+ " removed");
				connections.remove(i);
			}
		}
	}

	public void connectionDown() {
		System.out.println("(!) Connection is down!");
		removeObsoleteConnections();
	}

	public void requestConnection(String address) {
		// TODO - ver caso onde a conex‹o n‹o ocorre (deleta a info do
		// cliente/server relacionado?)
		if (!connector.connectTo(address))
			System.out.println("(!) Connection to " + address + " failed");
	}

	public void requestConnection(String address, int port) {
		// TODO - ver caso onde a conex‹o n‹o ocorre (deleta a info do
		// cliente/server relacionado?)
		if (!connector.connectTo(address, port))
			System.out.println("(!) Connection to " + address + " failed");
	}

	public void sendToAllConnections(String message) {
		for (int i = 0; i < connections.size(); i++) {
			TCPConnection connection = connections.get(i);
			connection.speak(message);
		}
	}
}
