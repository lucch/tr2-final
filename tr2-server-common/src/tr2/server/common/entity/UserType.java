package tr2.server.common.entity;

public enum UserType {
	
	USER(0), ADMIN(1);
	
	private long code;
	
	private UserType(long code) {
		this.code = code;
	}
	
	public long getCode() {
		return code;
	}
	
	public void setCode(long code) {
		this.code = code;
	}
	
	public static UserType fromCode(long code) {
		for(UserType u : values()) {
			if(u.getCode() == code)
				return u;
		}
		return null;
	}

}
