package tr2.server.common.entity;

/* 1 a 10000 */
public class Interval {
	
	/*
	0 -> [0..9999]
	1 -> [10000..19999]
	2 -> [20000..29999]
	...
	I -> [(I*10000)..(I*10000+9999)]
	*/
	private long index;
	
	private double result;
	
	private String clientIP;
	
	private IntervalState intervalState;
	
	public Interval() {
		
	}

	public double getResult() {
		return result;
	}

	public void setResult(double result) {
		this.result = result;
	}

	public String getClientIP() {
		return clientIP;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}

	public IntervalState getIntervalState() {
		return intervalState;
	}

	public void setIntervalState(IntervalState intervalState) {
		this.intervalState = intervalState;
	}
	
	public long getFirstDenominator() {
		return index * 10000l;
	}
	
	public long getLastDenominator() {
		return index * 10000l + 9999l;
	}

}
