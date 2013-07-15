package tr2.tcpconnection;

import java.io.*;

public class TCPSpeaker {

	private BufferedWriter writer;

	public TCPSpeaker(TCPConnection connection) {
		try {
			writer = new BufferedWriter(new OutputStreamWriter(connection
					.getSocket().getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void speak(String message) {
		// Verificar se a conex‹o ainda est‡ aberta
		try {
			writer.write(message + "\n");
			writer.flush();

			System.out.println("You : " + message);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
