package tr2.client.http.util;

import java.io.*;
import java.net.*;

/**
 * @author lycog
 */
public class MulticastSender extends NetworkConstants {

	public void speak(String message) {

		DatagramSocket socket = null;
		DatagramPacket outPacket = null;
		byte[] outBuf;

		try {
			socket = new DatagramSocket();

			outBuf = message.getBytes();

			// Send to multicast IP address and port
			InetAddress address = InetAddress.getByName(MULTICAST_ADDRESS);
			outPacket = new DatagramPacket(outBuf, outBuf.length, address,
					MULTICAST_PORT);

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