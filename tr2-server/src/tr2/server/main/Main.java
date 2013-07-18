package tr2.server.main;

import java.io.IOException;
import java.net.UnknownHostException;

import tr2.server.http.HttpServerDispatcher;
import tr2.server.http.UserDB;
import tr2.server.sync.controller.Controller;

public class Main {

	public static void main(String[] args) {
		try {
			System.out.println("Starting server...");
			UserDB.instance();
			new Controller();

			Thread dispatcher = new Thread(HttpServerDispatcher.instance());
			dispatcher.start();
			System.out.println("Server started successfully!");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
