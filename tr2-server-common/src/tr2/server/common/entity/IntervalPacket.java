package tr2.server.common.entity;

import java.util.List;

public class IntervalPacket {
	
	private List<Interval> intervals;
	
	/* Server which is assigned the intervals */
	private String ownerIP;
		
	public IntervalPacket() {}
	
	public IntervalPacket(List<Interval> intervals, String ownerIP) {
		this.setIntervals(intervals);
		this.setOwnerIP(ownerIP);
	}

	public List<Interval> getIntervals() {
		return intervals;
	}

	public void setIntervals(List<Interval> intervals) {
		this.intervals = intervals;
	}

	public String getOwnerIP() {
		return ownerIP;
	}

	public void setOwnerIP(String ownerIP) {
		this.ownerIP = ownerIP;
	}
	
}
