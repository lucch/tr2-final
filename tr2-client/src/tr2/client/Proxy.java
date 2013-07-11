package tr2.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import tr2.client.util.MulticastReceiver;
import tr2.client.util.NetworkConstants;

public class Proxy implements ServerIPsListener {

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
	 * @param request
	 *            The request to be processed by the remote server.
	 * @param type
	 *            TODO
	 * @return Response from the remote server.
	 */
	public String request(String request, RequestType type) {
		StringBuilder response = new StringBuilder();

		Socket socket = getSocket(type);

		System.out.println("[PROXY] Sending request to: "
				+ socket.getRemoteSocketAddress());

		try {
			System.out.println("[PROXY] Writing request to remote server...");
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream()));
			writer.write(request);
			writer.flush();
			// writer.close();

			System.out
					.println("[PROXY] Waiting for response from remote server...");
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			String s;
			while ((s = reader.readLine()) != null) {
				if (s.equals("EOF"))
					break;
				response.append(s + "\n");
			}
			// reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return response.toString();
	}

	private Socket getSocket(RequestType type) {
		int found = 0;
		Set<String> keys;
		Socket socket;
		Iterator<String> it;

		do {
			// Waits until a server is found!
			// TODO: Add a timeout!!!
			while (remoteServerSockets.size() == 0) {
				System.out.print(""); // This line IS necessary (believe...).
			}

			keys = remoteServerSockets.keySet();
			socket = null;
			it = keys.iterator();
			while (it.hasNext()) {
				String ip = it.next();
				if (type == RequestType.HTTP) {
					try {
						socket = new Socket(ip,
								NetworkConstants.REMOTE_HTTP_SERVER_PORT);
						found = 1;
						break;
					} catch (Exception e) {
						remoteServerSockets.remove(ip);
						System.out.println("[PROXY] Removed server: " + ip
								+ " from list.");
					}
				} else {
					socket = remoteServerSockets.get(ip);
					if (socket == null || !socket.isBound()) {
						remoteServerSockets.remove(ip);
						System.out.println("[PROXY] Removed server: " + ip
								+ " from list.");
						continue;
					}
					found = 1;
					break;
				}
			}
		} while (found == 0);

		return socket;
	}

	@Override
	public void onIPListChangedListener(ArrayList<String> ipList) {
		for (int i = 0; i < ipList.size(); i++) {
			String ip = ipList.get(i);
			if (!remoteServerSockets.containsKey(ip)) {
				try {
					remoteServerSockets.put(ip, new Socket(ip,
							NetworkConstants.REMOTE_JACOPO_SERVER_PORT));
					System.out.println("New IP " + ip + ".");
					System.out.println("Remote Servers Size: "
							+ remoteServerSockets.size());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
