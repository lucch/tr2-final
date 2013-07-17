package tr2.server.interval.data;

import java.util.ArrayList;

import tr2.server.common.entity.Interval;

public class Data {
	private ArrayList<Interval> intervals;

	private ArrayList<Interval> runningIntervals;

	private ArrayList<Interval> pendingIntervals;

	private long index;

	private final String label = "[INTERVAL DATA]";

	public Data() {
		index = 0;
		intervals = new ArrayList<Interval>();
		runningIntervals = new ArrayList<Interval>();
		pendingIntervals = new ArrayList<Interval>();

	}

	public Interval getInterval(String address) {
		Interval i = null;

		if (pendingIntervals.size() == 0) {
			// if there are no pending intervals
			i = new Interval();

			i.setIndex(index);

			index++;
		} else {
			i = pendingIntervals.get(0); // gets the first pending interval
			pendingIntervals.remove(0);
		}

		i.setClientIP(address);

		runningIntervals.add(i);

		return i;
	}

	// if the connection to the client is down,
	// we need to see if he was calculating an interval and make it avaiable
	public void removeClientDropInterval(String address) {
		Interval interval = searchRunningIntervalByAddress(address);

		if (interval == null) {
			// there was no interval being calculated by the client
		} else {
			runningIntervals.remove(interval);
			pendingIntervals.add(interval);
			System.out.println(label + " Interval is pending");
		}
	}

	public void addCalculatedInterval(Interval calculated) {
		Interval interval = searchRunningIntervalByAddress(calculated
				.getClientIP());

		if (interval == null) {
			// there is no running interval being calculated
			// this is the case when the client drops and the interval
			// is designated to be calculated by another client
		} else {
			runningIntervals.remove(interval);
			intervals.add(interval);
			System.out.println(label + " Interval " + interval.getIndex()
					+ " received with result " + interval.getResult());
		}
	}

	private Interval searchRunningIntervalByAddress(String address) {
		Interval interval = null;

		for (int i = 0; i < runningIntervals.size(); i++) {
			Interval intervalAux = runningIntervals.get(i);
			if (intervalAux.getClientIP().equals(address)) {
				interval = intervalAux;
				break;
			}
		}

		return interval;
	}
}
