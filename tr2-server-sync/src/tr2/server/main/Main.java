package tr2.server.main;

import java.io.IOException;
import java.net.UnknownHostException;

import tr2.server.sync.controller.Controller;

public class Main {

	public static void main(String[] args) {

		try {
			
			new Controller();
			

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
