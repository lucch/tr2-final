package tr2.client.http.util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

import tr2.client.http.ServerIPsListener;
import tr2.server.common.util.NetworkConstants;

public class MulticastReceiver implements Runnable {

	private ServerIPsListener listener;

	public MulticastReceiver(ServerIPsListener listener) {
		this.listener = listener;
	}

	public void run() {
		while(true) {
			ArrayList<String> ipList = new ArrayList<String>();
			MulticastSocket socket = null;
			DatagramPacket inPacket = null;
			byte[] inBuf = new byte[256];
			try {
				// Prepare to join multicast group
				socket = new MulticastSocket(NetworkConstants.CLIENT_MULTICAST_PORT);
				InetAddress address = InetAddress.getByName(NetworkConstants.CLIENT_MULTICAST_ADDRESS);

				socket.joinGroup(address);

				while (true) {
					inPacket = new DatagramPacket(inBuf, inBuf.length);
					socket.receive(inPacket);
					//String message = new String(inBuf, 0, inPacket.getLength());
					//System.out.println("From " + inPacket.getAddress() + " : " + message);
					ipList.add(inPacket.getAddress().getHostAddress());
					break;
				}
			} catch (IOException ioe) {
				System.out.println(ioe);
			}

			listener.onIPListChangedListener(ipList);
		}
	}
}
