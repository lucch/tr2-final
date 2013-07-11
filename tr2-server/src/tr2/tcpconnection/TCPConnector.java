/*
 * Faz o papel do Dispatcher.
 */

package tr2.tcpconnection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPConnector implements Runnable {

	private ServerSocket incomingRequest;

	private TCPConnectionsManager manager;

	private int timeout;

	private int port;

	public TCPConnector(TCPConnectionsManager manager, int port, int timeout) {
		this.port = port;
		this.manager = manager;
		try {
			incomingRequest = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.timeout = timeout;
	}

	public boolean connectTo(String address) {
		return connectTo(address, port);
	}

	public boolean connectTo(String address, int port) {
		Socket socket;
		try {
			socket = new Socket();
			socket.connect(new InetSocketAddress(address, port), timeout);

			manager.newConnection(socket);

			return true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			System.out.println("(!) Connection to " + address + " failed");
			return false;
		}
	}

	public void run() {
		while (true) {
			Socket socket;
			try {
				socket = incomingRequest.accept();

				manager.newConnection(socket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
