package tr2.client.http;

import java.io.IOException;

import tr2.server.common.entity.Interval;
import tr2.server.common.series.protocol.Messages;

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
			
			Interval i = getSeriesInterval();
			calculate(i);
			//System.out.println(i.getResult());
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void calculate(Interval i) {
		
	}

	private Interval getSeriesInterval() throws IOException {
		
		Proxy proxy = Proxy.instance();
		String s = proxy.request(Messages.GET_INTERVAL, RequestType.SERIES);
		
		System.out.println(s);
		
		return null;
	}

}
