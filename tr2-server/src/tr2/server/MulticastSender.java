package tr2.server;

import java.io.*;
import java.net.*;

/**
 * @author lycog
 */
public class MulticastSender extends Multicast {

	public void speak(String message) {

		DatagramSocket socket = null;
		DatagramPacket outPacket = null;
		byte[] outBuf;

		try {
			socket = new DatagramSocket();

			outBuf = message.getBytes();

			// Send to multicast IP address and port
			InetAddress address = InetAddress.getByName(multicastAddress);
			outPacket = new DatagramPacket(outBuf, outBuf.length, address,
					multicastPort);

			socket.send(outPacket);

			System.out.println("You : " + message);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ie) {
			}
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
	}
}