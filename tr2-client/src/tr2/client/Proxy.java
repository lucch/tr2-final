package tr2.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import tr2.client.util.MulticastReceiver;
import tr2.client.util.NetworkConstants;

public class Proxy implements ServerIPsListener  {

	private static Proxy proxy;

	private static Map<String, Socket> remoteServerSockets;

	private Proxy() throws IOException {
		remoteServerSockets = new HashMap<String, Socket>();
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
		StringBuilder response = new StringBuilder();

		// Add a timeout!!!
		while(remoteServerSockets.size() == 0) {
			System.out.print("");
		}

		int found = 0;
		Set<String> keys = remoteServerSockets.keySet();
		Socket socket = null;
		while(keys.iterator().hasNext()) {
			socket = remoteServerSockets.get(keys.iterator().next());
			if(socket != null && socket.isBound()) {
				System.out.println("[PROXY] Sending request to: " + socket.getRemoteSocketAddress());
				found = 1;
				break;
			} else {
				System.out.println("[PROXY] Server " + socket.getRemoteSocketAddress() + " is not bound");
			}
		}
		
		if(found == 0)
			return null;

		try {
			System.out.println("[PROXY] Writing request to remote server...");
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			writer.write(request);
			writer.flush();
//			writer.close();
			
			System.out.println("[PROXY] Waiting for response from remote server...");
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String s;
			while((s = reader.readLine()) != null) {
				if(s.equals("EOF")) break;
				response.append(s + "\n");
			}
//			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return response.toString();
	}

	@Override
	public void onIPListChangedListener(ArrayList<String> ipList) {
		for(int i=0; i<ipList.size(); i++) {
			String ip = ipList.get(i);
			if(!remoteServerSockets.containsKey(ip)) {
				try {
					remoteServerSockets.put(ip, new Socket(ip, NetworkConstants.REMOTE_SERVER_PORT));
					System.out.println("New IP " + ip + ".");
					System.out.println("Remote Servers Size: " + remoteServerSockets.size());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}








