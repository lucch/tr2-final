package tr2.client.http;

import java.io.IOException;

import tr2.server.common.entity.Interval;
import tr2.server.common.series.protocol.Messages;
import tr2.server.common.util.JSONHelper;

public class Launcher {

	public static void main(String[] args) throws IOException {
		Launcher launcher = new Launcher();
		launcher.go();
	}

	private void go() {
		System.out.println("[CLIENT] Starting HTTP handler...");
		try {
			Thread dispatcher = new Thread(Dispatcher.instance());
			dispatcher.start();
			System.out.println("[CLIENT] HTTP handler started successfully!");

			// TODO: Write the code which is going to connect to the remote
			// server to series-related stuff!
			Interval interval = getSeriesInterval();
			calculate(interval);
			System.out.println(interval);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void calculate(Interval interval) {
		if (interval == null)
			return;

		double sum = 0;
		for (long i = interval.getFirstDenominator(); i < interval
				.getLastDenominator(); i++) {
			sum += 1.0d / i;
		}
		interval.setResult(sum);
	}

	private Interval getSeriesInterval() throws IOException {
		Proxy proxy = Proxy.instance();
		String response = proxy.request(Messages.GET_INTERVAL,
				RequestType.SERIES);

		/* Parses JSON String to Interval object. */
		return JSONHelper.fromJSON(response, Interval.class);
	}

}
