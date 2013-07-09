package tr2.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Multicast implements Runnable {
	public static final String serverCommIP = "224.2.2.3";
	public static final int serverCommPort = 8888;
	public static final String serverServerDiscoveryIP = "224.2.2.4";
	public static final int serverServerDiscoveryPort = 8888;

	private String address;
	private int port;

	public Multicast(String address, int port) {
		this.address = address;
		this.port = port;
	}

	public String getAddress() {
		return address;
	}

	public int getPort() {
		return port;
	}

	public void sendAlive() {
		speak(Messages.Alive);
	}

	public void speak(String message) {

		DatagramSocket socket = null;
		DatagramPacket outPacket = null;
		byte[] outBuf;

		try {
			socket = new DatagramSocket();

			outBuf = message.getBytes();

			// Send to multicast IP address and port
			InetAddress address = InetAddress.getByName(this.address);
			outPacket = new DatagramPacket(outBuf, outBuf.length, address, port);

			socket.send(outPacket);

			System.out.println("You : " + message);
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
	}

	public void run() {
		MulticastSocket socket = null;
		DatagramPacket inPacket = null;
		byte[] inBuf = new byte[256];
		try {
			// Prepare to join multicast group
			socket = new MulticastSocket(port);
			InetAddress address = InetAddress.getByName(this.address);

			socket.joinGroup(address);

			while (true) {
				inPacket = new DatagramPacket(inBuf, inBuf.length);
				socket.receive(inPacket); // trava aqui

				// Verifica se o src address e o dst address s‹o iguais
				if (inPacket.getAddress().getHostAddress()
						.equals(InetAddress.getLocalHost().getHostAddress())) {
					continue;
				}

				String message = new String(inBuf, 0, inPacket.getLength());
				String response = Messages.parser(message);

				// Verifica se deve retornar algo para o sender
				if (!response.equals(Messages.NoOp)) {
					speak(response);
				}

				System.out.println("Msg Rcvd From " + inPacket.getAddress()
						+ " : " + message);
			}
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
	}
}
