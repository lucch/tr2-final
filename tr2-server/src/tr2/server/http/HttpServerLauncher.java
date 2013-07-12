package tr2.server.http;

import java.io.IOException;

import tr2.controller.Controller;

public class HttpServerLauncher {

	public static void main(String[] args) {
		HttpServerLauncher launcher = new HttpServerLauncher();
		launcher.go();
	}

	private void go() {
		System.out.println("Starting server...");
		try {
			Thread dispatcher = new Thread(HttpServerDispatcher.instance());
			dispatcher.start();
			System.out.println("Server started successfully!");
			
			// Starts the "Multicast", notifying interested listeners of this 
			// HTTP Server's existence.
			new Controller();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}