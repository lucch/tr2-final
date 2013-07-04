package tr2.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Worker implements Runnable {
	
	private Dispatcher dispatcher;
	
	private Socket socket;
	
	public Worker(final Dispatcher dispatcher, final Socket socket) {
		this.dispatcher = dispatcher;
		this.socket = socket;
	}
	
	@Override
	public void run() {
		Proxy proxy = Proxy.instance();
		BufferedReader reader;
		StringBuilder request = new StringBuilder();
		String response;
		//char[] buffer = new char[1024];
		
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			int c, end = 0;
			while(true) {
				// TODO: This implementation is inefficient!!! Optimize!
				c = reader.read();
				request.append((char) c);
				
				if(c == '\r')
					continue;
				
				if(c == '\n') {
					if(end == 0) {
						end = 1;
					} else {
						break;
					}
				} else {
					end = 0;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		response = proxy.request(request.toString());
		
		// Push notification
		dispatcher.notify(socket, response);
	}

}
