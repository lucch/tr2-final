package tr2.client.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import tr2.server.common.util.NetworkConstants;

public class Dispatcher implements Runnable {
	
	private static Dispatcher dispatcher;
	
	private ServerSocket incomingSocket;
	
	private Dispatcher() throws IOException {
		incomingSocket = new ServerSocket(NetworkConstants.LOCAL_CLIENT_PORT);
	}
	
	public static Dispatcher instance() throws IOException {
		if (dispatcher == null) {
			dispatcher = new Dispatcher();
		}
		return dispatcher;
	}
	
	@Override
	public void run() {
		while(true) {
			Socket socket;
			try {
				// Gets an incomming connection
				socket = incomingSocket.accept();
				System.out.println("[DISPATCHER] New incomming connection: " + socket.getInetAddress());
				// Delegates it to a worker
				Thread worker = new Thread(new Worker(socket));
				worker.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
