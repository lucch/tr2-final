/*
 * Faz o papel do Dispatcher.
 */

package tr2.tcpconnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPConnector implements Runnable {

	private ServerSocket incomingRequest;

	private TCPConnectionsManager manager;

	private int port;

	public TCPConnector(TCPConnectionsManager manager, int port) {
		this.port = port;
		this.manager = manager;
		try {
			incomingRequest = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void connectTo(String address) {
		Socket socket;
		try {
			socket = new Socket(address, port);

			manager.newConnection(socket);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void connectTo(String address, int port) {
		Socket socket;
		try {
			socket = new Socket(address, port);

			manager.newConnection(socket);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
