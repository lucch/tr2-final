package tr2.client;

import java.io.IOException;

public class Launcher {

	public static void main(String[] args) {
		Launcher launcher = new Launcher();
		launcher.go();
	}

	private void go() {
		System.out.println("Starting server...");
		try {
			Thread dispatcher = new Thread(Dispatcher.instance());
			dispatcher.start();
			System.out.println("Server started successfully!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
