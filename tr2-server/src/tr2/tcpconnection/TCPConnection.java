package tr2.tcpconnection;

import java.net.Socket;

public class TCPConnection {

	private TCPConnectionsManager manager;
	private Socket socket;
	private TCPListener listener;
	private TCPSpeaker speaker;
	private boolean connected;

	public TCPConnection(TCPConnectionsManager manager, Socket socket) {
		this.manager = manager;
		this.socket = socket;
		connected = true;
	}
	
	public boolean isConnected() {
		return connected;
	}
	
	public void start() {
		listener = new TCPListener(this);
		Thread listenerThread = new Thread(listener);
		listenerThread.start();
		
		speaker = new TCPSpeaker(this);
	}

	public void speak(String message) {
		speaker.speak(message);
	}

	public void connectionDown() {
		connected = false;
		manager.connectionDown();
	}
	
	public void parser(String message) {
		// TODO
		System.out.println("Received: " + message);
	}
	
	public Socket getSocket() {
		return socket;
	}

	@Override
	public boolean equals(Object obj) {
		TCPConnection connection = (TCPConnection) obj;
		return this.socket.getInetAddress().getHostAddress()
				.equals(connection.socket.getInetAddress().getHostAddress());
	}

	@Override
	public String toString() {
		return socket.getInetAddress().getHostAddress();
	}
}
