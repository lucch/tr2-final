package tr2.tcpconnection;

import java.io.*;

public class TCPListener implements Runnable {

	private TCPConnection connection;

	private BufferedReader reader;

	public TCPListener(TCPConnection connection) {
		this.connection = connection;
	}

	public void run() {
		try {
			reader = new BufferedReader(new InputStreamReader(connection
					.getSocket().getInputStream()));
			while (true) {
				String message = reader.readLine();
				// indicates that server is down
				if (message == null) {
					connection.connectionDown();
					break;
				}
				connection.parser(message);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
