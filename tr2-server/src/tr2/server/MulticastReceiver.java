package tr2.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastReceiver extends Multicast implements Runnable {

	public void run() {
		MulticastSocket socket = null;
		DatagramPacket inPacket = null;
		byte[] inBuf = new byte[256];
		try {
			// Prepare to join multicast group
			socket = new MulticastSocket(multicastPort);
			InetAddress address = InetAddress.getByName(multicastAddress);

			socket.joinGroup(address);
			
			while (true) {
				inPacket = new DatagramPacket(inBuf, inBuf.length);
				socket.receive(inPacket);
				String message = new String(inBuf, 0, inPacket.getLength());
				System.out.println("From " + inPacket.getAddress() + " : " + message);
			}
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
	}
}
