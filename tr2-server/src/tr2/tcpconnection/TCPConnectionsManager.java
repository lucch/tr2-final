package tr2.tcpconnection;

import java.net.Socket;
import java.util.ArrayList;

import tr2.util.NetworkConstants;

public class TCPConnectionsManager {
	
	private TCPConnector connector;
	
	private ArrayList<TCPConnection> connections;
	
	public TCPConnectionsManager() {
		connector = new TCPConnector(this, NetworkConstants.TCP_SERVER_TO_SERVER_PORT);
		Thread connectorThread = new Thread(connector);
		connectorThread.start();
		
		connections = new ArrayList<TCPConnection>();
	}
	
	public TCPConnectionsManager(int serverPort) {
		connector = new TCPConnector(this, serverPort);
		Thread connectorThread = new Thread(connector);
		connectorThread.start();
		
		connections = new ArrayList<TCPConnection>();
	}
	
	public void newConnection(Socket socket) {
		TCPConnection connection = new TCPConnection(socket);
				
		// checks if connection already exists
		for (int i = 0; i < connections.size(); i++) {
			if (connections.get(i).equals(connection)) {
				return;
			}
		}
		
		connections.add(connection);
		
		System.out.println("Connection made to: " + connection);
		System.out.println(connections.size());
	}
	
	public void requestConnection(String address, int port) {
		connector.connectTo(address, port);
	}
	
	public void requestConnection(String address) {
		connector.connectTo(address);
	}
	
	
	public void sendToAllConnections(String message) {
		for (int i = 0; i < connections.size(); i++) {
			TCPConnection connection = connections.get(i);
			connection.speak(message);
		}
	}
}
