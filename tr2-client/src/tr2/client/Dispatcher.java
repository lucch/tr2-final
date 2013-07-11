package tr2.client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import tr2.client.util.NetworkConstants;

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
				// Delegates it to a worker
				Thread worker = new Thread(new Worker(socket));
				worker.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
