package tr2.server.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServerDispatcher implements Runnable {

	private static HttpServerDispatcher httpserver;
	
	private ServerSocket incomingSocket;
	
	private HttpServerDispatcher() throws IOException {
		incomingSocket = new ServerSocket(8080);
	}
	
	public static HttpServerDispatcher instance() throws IOException {
		if (httpserver == null) {
			httpserver = new HttpServerDispatcher();
		}
		return httpserver;
	}
	
	@Override
	public void run() {
		while(true) {
			Socket socket;
			try {
				// Gets an incomming connection
				socket = incomingSocket.accept();
				//Creates a new socket with a random port
				Thread newthread = new Thread(new HttpServerInstance(socket));
				newthread.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}