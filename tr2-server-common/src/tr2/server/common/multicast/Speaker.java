package tr2.server.common.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Speaker implements Runnable {
	private String address;
	private int port;
	private Multicast multicast;

	public Speaker(Multicast multicast, String address, int port) {
		this.multicast = multicast;
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
			
			multicast.notifyMessageSpoken(message);
			
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
	}

	@Override
	public void run() {
		while (true) {
			speak(multicast.getPeriodicMessage());
			try {
				Thread.sleep(multicast.getPeriodicTime());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
