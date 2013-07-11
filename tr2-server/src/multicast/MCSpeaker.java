package multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import tr2.util.MulticastConstants;

public class MCSpeaker implements Runnable {
	private String address;
	private int port;

	public MCSpeaker(String address, int port) {
		this.address = address;
		this.port = port;
	}

	public void speak(String message) {
		DatagramSocket socket = null;
		DatagramPacket outPacket = null;
		byte[] outBuf;

		try {
			socket = new DatagramSocket();

			outBuf = message.getBytes();

			// sends packet to address and port
			InetAddress address = InetAddress.getByName(this.address);
			outPacket = new DatagramPacket(outBuf, outBuf.length, address,
					this.port);

			socket.send(outPacket);

			System.out.println("You : " + message);
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
	}

	@Override
	public void run() {
		while(true) {
			speak(MulticastConstants.HELLO);
			try {
				Thread.sleep(4513);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
