package tr2.server.interval.data;

import java.util.ArrayList;

import tr2.server.common.entity.Interval;
import tr2.server.common.series.protocol.Messages;

public class Data {
	private static ArrayList<Interval> intervals;

	private static ArrayList<Interval> runningIntervals;

	private static ArrayList<Interval> pendingIntervals;

	private long intervalIndex;
	
	private final String label = "[INTERVAL DATA]";

	public static final int TYPE_INTERVALS = 0;
	public static final int TYPE_PENDING_INTERVALS = 1;
	
	public Data() {
		intervalIndex = 0;
		intervals = new ArrayList<Interval>();
		runningIntervals = new ArrayList<Interval>();
		pendingIntervals = new ArrayList<Interval>();

	}
	
	public static ArrayList<Interval> getCalculatedIntervals() {
		return intervals;
	}
	
	public static ArrayList<Interval> getRunningIntervals() {
		return runningIntervals;
	}
	
	public static ArrayList<Interval> getPendingIntervals() {
		return pendingIntervals;
	}
	
	public Interval getInterval(String address) {
		Interval i = null;

		if (pendingIntervals.size() == 0) {
			// if there are no pending intervals
			i = new Interval();

			i.setIndex(intervalIndex);

			intervalIndex++;
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
			System.out.println(label + " Interval " + interval.getIndex()
					+ " is pending");
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
			intervals.add(calculated);
			System.out.println(label + " Interval " + calculated.getIndex()
					+ " received with result " + calculated.getResult());
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

	public String intervalsToString(int type) {
		ArrayList<Interval> intervals = null;
		
		String str = "";
		if (type == TYPE_INTERVALS) {
			intervals = Data.intervals;
			str += intervalIndex + Messages.SEPARATOR;
		} else if (type == TYPE_PENDING_INTERVALS) {
			intervals = Data.pendingIntervals;
		}
		
		for (int i = 0; i < intervals.size(); i++) {
			// append all intervals
			Interval interval = intervals.get(i);
			str += interval.getIndex() 
					+ Messages.SUBSEPARATOR
					+ interval.getResult() 
					+ Messages.SUBSEPARATOR
					+ interval.getClientIP() 
					+ Messages.SEPARATOR;
		}
		
		return str;
	}

	public void stringToIntervals(String string, int type) {
					
		ArrayList<Interval> newIntervals = new ArrayList<Interval>();
		
		String[] strIntervals;
		
		strIntervals = string.split(Messages.SEPARATOR);
		
		if (type == Data.TYPE_INTERVALS) {
			long indexReceived = Long.parseLong(strIntervals[0]);
		
			if (indexReceived == 0) return;
		
			this.intervalIndex = indexReceived;
		}
		
		for (int i = 1; i < strIntervals.length; i++) {
			String[] attributes = strIntervals[i].split(Messages.SUBSEPARATOR);
			
			Interval interval = new Interval();
			interval.setIndex(Long.parseLong(attributes[0]));
			interval.setResult(Double.parseDouble(attributes[1]));
			interval.setClientIP(attributes[2]);
			
			newIntervals.add(interval);
		}
				
		if (type == TYPE_INTERVALS) {
			System.out.println(label + "Old intervals list size is " + intervals.size());
			Data.intervals = newIntervals;
			System.out.println(label + "New intervals list size is " + newIntervals.size());
		} else if (type == TYPE_PENDING_INTERVALS) {
			System.out.println(label + "Old pending intervals list size is " + pendingIntervals.size());
			Data.pendingIntervals = newIntervals;
			System.out.println(label + "New pending intervals list size is " + newIntervals.size());
		}
		
	}
	
}
