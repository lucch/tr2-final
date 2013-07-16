package tr2.server.common.tcp;

import java.io.*;

public class Speaker {

	private BufferedWriter writer;

	public Speaker(Connection connection) throws IOException {
		writer = new BufferedWriter(new OutputStreamWriter(connection
				.getSocket().getOutputStream()));
	}

	public void speak(String message) throws IOException {
		writer.write(message + "\n");
		writer.flush();

		System.out.println("You : " + message);
	}
}
