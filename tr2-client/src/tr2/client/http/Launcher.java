package tr2.client.http;

import java.io.IOException;

public class Launcher {

	public static void main(String[] args) {
		Launcher launcher = new Launcher();
		launcher.go();
	}

	private void go() {
		System.out.println("[CLIENT] Starting HTTP handler...");
		try {
			Thread dispatcher = new Thread(Dispatcher.instance());
			dispatcher.start();
			System.out.println("[CLIENT] HTTP handler started successfully!");
			
			// TODO: Write the code which is going to connect to the remote server to series-related stuff!
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
