package tr2.server.common.tcp;

import java.io.*;

public class Listener implements Runnable {

	private Connection connection;

	private BufferedReader reader;

	public Listener(Connection connection) {
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
			e.printStackTrace();
		} catch (Exception e) {
			connection.connectionDown();
		}
	}
}
