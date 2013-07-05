package tr2.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

import tr2.client.util.MulticastReceiver;
import tr2.client.util.NetworkConstants;

public class Proxy implements ServerIPsListener  {
	
	private static Proxy proxy;
		
	private static Map<String, Socket> remoteServerSockets;
	
	private Proxy() throws IOException {
		Thread multicastListener = new Thread(new MulticastReceiver(this));
		multicastListener.start();
	}
	
	public static Proxy instance() throws IOException {
		if (proxy == null) {
			proxy = new Proxy();
		}
		return proxy;	
	}

	/**
	 * Make a request to a remote server.
	 * 
	 * @param request The request to be processed by the remote server.
	 * @return Response from the remote server.
	 */
	public String request(String request) {
		String response = null;
		
		// Add a timeout!!!
		while(remoteServerSockets == null);
		
		Socket socket = remoteServerSockets.get(0);
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			writer.write(request);
			writer.flush();
			writer.close();
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// Temporary... Testing...
			response = reader.readLine();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return response;
	}

	@Override
	public void onIPListChangedListener(ArrayList<String> ipList) {
		for(int i=0; i<ipList.size(); i++) {
			String ip = ipList.get(i);
			if(!remoteServerSockets.containsKey(ip)) {
				try {
					remoteServerSockets.put(ip, new Socket(ip, NetworkConstants.REMOTE_SERVER_PORT));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}








