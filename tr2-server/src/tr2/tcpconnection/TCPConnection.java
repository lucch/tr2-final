package tr2.tcpconnection;

import java.net.Socket;

public class TCPConnection {

	private Socket socket;
	private TCPListener listener;
	Thread listenerThread;
	private TCPSpeaker speaker;

	public TCPConnection(Socket socket) {
		this.socket = socket;
		
		listener = new TCPListener(this);
		listenerThread = new Thread(listener);
		listenerThread.start();

		speaker = new TCPSpeaker(this);
	}

	public void speak(String message) {
		speaker.speak(message);
	}

	public void connectionDown() {
		// TODO
		if (socket.isClosed())
			System.out.println("(!) Socket is closed!");
		System.out.println("(!) Connection is down!");
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
