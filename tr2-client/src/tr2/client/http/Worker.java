package tr2.client.http;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import tr2.client.http.exception.BadRequestException;
import tr2.client.http.util.SimpleHttpParser;

public class Worker implements Runnable {

	private Socket socket;

	public Worker(final Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			Proxy proxy = Proxy.instance();
			String request = null, response;
			SimpleHttpParser parser = new SimpleHttpParser();
			System.out.println("[WORKER] Parsing request...");
			request = parser.parse(socket.getInputStream());
			System.out.println("[WORKER] Sending request to server...");
			response = proxy.request(request, RequestType.HTTP);
			notify(response);
		} catch (BadRequestException e) {
			System.out.println("[WORKER] Bad request. Aborting...");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Send response to the browser.
	 * 
	 * @param response Calculated response obtained from the server.
	 */
	public void notify(String response) {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			System.out.println("[WORKER] Writing to the browser:");
			System.out.println(response);
			writer.write(response);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
