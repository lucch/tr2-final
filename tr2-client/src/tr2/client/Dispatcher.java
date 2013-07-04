package tr2.client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Dispatcher implements Listener, Runnable {
	
	private static Dispatcher dispatcher;
	
	private ServerSocket incomingSocket;
	
	private Dispatcher() throws IOException {
		incomingSocket = new ServerSocket(8080);
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
				Thread worker = new Thread(new Worker(this, socket));
				worker.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void notify(Socket socket, String response) {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			System.out.println("Writing to the browser:");
			System.out.println(response);
			writer.write(response);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
