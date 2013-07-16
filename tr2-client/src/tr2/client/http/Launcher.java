package tr2.client.http;

import java.io.IOException;

import tr2.server.common.entity.Interval;
import tr2.server.common.entity.IntervalState;
import tr2.server.common.series.protocol.Messages;
import tr2.server.common.util.JSONHelper;

public class Launcher {

	public static void main(String[] args) throws IOException {
		Launcher launcher = new Launcher();
		//launcher.go();
		
		Interval i = launcher.getSeriesInterval();
		//launcher.calculate(i);
		//System.out.println("Result is: " + i.getResult());
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
	
	private void calculate(Interval interval) {
		if (interval == null)
			return;
		
		double sum = 0;
		for(long i = interval.getFirstDenominator(); i<interval.getLastDenominator(); i++) {
			sum += 1.0d/i;
		}
		interval.setResult(sum);
	}

	private Interval getSeriesInterval() throws IOException {
		
		//Proxy proxy = Proxy.instance();
		//String s = proxy.request(Messages.GET_INTERVAL, RequestType.SERIES);
		
		//System.out.println(s);
		
		Interval i = new Interval();
		i.setIntervalState(IntervalState.RUNNING);
		
		Interval j = JSONHelper.fromJSON(i.toJSON(), Interval.class);
		System.out.println(j.getIntervalState());
		
		
		// TODO: Parse JSON String to Interval object.
		
		return null;
	}

}
