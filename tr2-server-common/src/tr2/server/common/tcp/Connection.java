package tr2.server.common.tcp;

import java.net.Socket;

public class Connection {

	private ConnectionsManager manager;
	
	private Socket socket;
	
	private Listener listener;
	
	private Speaker speaker;
	
	private boolean connected;

	public Connection(ConnectionsManager manager, Socket socket) {
		this.manager = manager;
		this.socket = socket;
		connected = true;
	}

	public boolean isConnected() {
		return connected;
	}

	public void start() {
		listener = new Listener(this);
		Thread listenerThread = new Thread(listener);
		listenerThread.start();

		speaker = new Speaker(this);
	}

	public void speak(String message) {
		speaker.speak(message);
	}

	public void connectionDown() {
		connected = false;
		manager.connectionDown();
	}

	public void parser(String message) {
		manager.parser(message);
	}

	public Socket getSocket() {
		return socket;
	}
	
	public String getAddress() {
		return socket.getInetAddress().getHostAddress();
	}

	@Override
	public boolean equals(Object obj) {
		Connection connection = (Connection) obj;
		return this.socket.getInetAddress().getHostAddress()
				.equals(connection.socket.getInetAddress().getHostAddress());
	}

	@Override
	public String toString() {
		return getAddress();
	}
}
