package tr2.client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import tr2.client.util.SimpleHttpParser;

public class Worker implements Runnable {
		
	private Socket socket;
	
	public Worker(final Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		Proxy proxy = Proxy.instance();
		String request = null, response;
		//char[] buffer = new char[1024];
		
		try {
			while(true) {
				SimpleHttpParser parser = new SimpleHttpParser();
				request = parser.parse(socket.getInputStream());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		response = proxy.request(request.toString());
		
		notify(response);
	}
	
	/**
	 * Send response to the browser.
	 * 
	 * @param response Calculated response obtained from the server.
	 */
	public void notify(String response) {
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
