package multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MCListener implements Runnable {
	private Multicast controller;
	private String address;
	private int port;

	public MCListener(Multicast controller, String address, int port) {
		this.controller = controller;
		this.address = address;
		this.port = port;
	}

	public void run() {
		MulticastSocket socket = null;
		DatagramPacket inPacket = null;
		byte[] inBuf = new byte[256];
		try {
			// join multicast group
			socket = new MulticastSocket(this.port);
			InetAddress address = InetAddress.getByName(this.address);

			socket.joinGroup(address);

			while (true) {
				inPacket = new DatagramPacket(inBuf, inBuf.length);
				socket.receive(inPacket);

				// ignores my own messages
				if (inPacket.getAddress().getHostAddress()
						.equals(InetAddress.getLocalHost().getHostAddress())) {
					continue;
				}

				String message = new String(inBuf, 0, inPacket.getLength());
				controller.parser(message, inPacket.getAddress()
						.getHostAddress());

				System.out.println("Msg Rcvd From " + inPacket.getAddress()
						+ " : " + message);
			}
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
	}
}
