package tr2.client.http.util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import tr2.server.common.util.NetworkConstants;

/**
 * @author lycog
 */
public class MulticastSender {

	public void speak(String message) {

		DatagramSocket socket = null;
		DatagramPacket outPacket = null;
		byte[] outBuf;

		try {
			socket = new DatagramSocket();

			outBuf = message.getBytes();

			// Send to multicast IP address and port
			InetAddress address = InetAddress.getByName(NetworkConstants.MULTICAST_ADDRESS);
			outPacket = new DatagramPacket(outBuf, outBuf.length, address,
					NetworkConstants.MULTICAST_PORT);

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