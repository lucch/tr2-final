package tr2.server.common.entity;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import tr2.server.common.util.JSONable;



/**
 * This class represents a subinterval of the infinite harmonic series.
 * 
 * The intervals are sequence of 10.000 numbers indexed according to its
 * position:
 * 
 * INDEX | INTERVAL 0 -> [0..9999] 1 -> [10000..19999] 2 -> [20000..29999] ... I
 * -> [(I*10000)..(I*10000+9999)]
 * 
 * This object is used to interchange data through the network.
 * 
 * @author alexandrelucchesi
 * 
 */

public class Interval implements JSONable {

	private long index;

	private double result;

	private String clientIP;

	private IntervalState intervalState;

	public Interval() {

	}

	public long getIndex() {
		return index;
	}

	public void setIndex(long index) {
		this.index = index;
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
	
	@SuppressWarnings("unchecked")
	public String toJSON() {
		JSONObject obj = new JSONObject();
		obj.put("index", this.index);
		obj.put("result", this.result);
		obj.put("clientIP", this.clientIP);
		obj.put("intervalState", this.intervalState == null ? 0 : this.intervalState.getCode());
		return obj.toJSONString();
	}
	
	public JSONable fromJSON(String json) {
		JSONParser parser = new JSONParser();
		JSONObject obj = null;
		Interval interval = null;
		try {
			obj = (JSONObject) parser.parse(json);
			interval = new Interval();
			interval.setIndex((Long) obj.get("index"));
			interval.setResult((Double) obj.get("result"));
			interval.setClientIP((String) obj.get("clientIP"));
			long code = (Long) obj.get("intervalState");
			interval.setIntervalState(IntervalState.fromCode(code));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return interval;
	}

}
