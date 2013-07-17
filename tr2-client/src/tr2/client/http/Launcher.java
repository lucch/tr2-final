package tr2.client.http;

import java.io.IOException;

import tr2.client.series.CalculatorManager;

public class Launcher {

	public static void main(String[] args) throws IOException {
		Launcher launcher = new Launcher();
		launcher.go();
	}

	private void go() {
//		System.out.println("[CLIENT] Starting HTTP handler...");
//		try {
//			Thread dispatcher = new Thread(Dispatcher.instance());
//			dispatcher.start();
//			System.out.println("[CLIENT] HTTP handler started successfully!");

			// TODO: Write the code which is going to connect to the remote
			// server to series-related stuff!
			System.out.println("[CLIENT] Starting calculator manager...");
			Thread calculatorManager = new Thread(new CalculatorManager());
			calculatorManager.start();
			System.out.println("[CLIENT] Calculator manager started successfully!");

//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

}
