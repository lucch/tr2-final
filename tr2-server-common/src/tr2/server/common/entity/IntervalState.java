package tr2.server.common.entity;

public enum IntervalState {
	
	AVAILABLE(0), FINISHED(1), RUNNING(2);
	
	private long code;
	
	private IntervalState(long code) {
		this.code = code;
	}
	
	public long getCode() {
		return code;
	}
	
	public void setCode(long code) {
		this.code = code;
	}
	
	public static IntervalState fromCode(long code) {
		for(IntervalState e : values()) {
			if(e.getCode() == code)
				return e;
		}
		return null;
	}
}
