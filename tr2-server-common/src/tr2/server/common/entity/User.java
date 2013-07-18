package tr2.server.common.entity;

public class User {
	
	private String userName;
	private String userIP;
	private UserType userType;
	
	public User() {}
	
	public User(String username, UserType type) {
		this.userName = username;
		this.setUserType(type);
	}

	public String getUsername() {
		return userName;
	}

	public void setUsername(String username) {
		this.userName = username;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	
	public String getUserIP() {
		return userIP;
	}
	
	public void setUserIP(String ip) {
		this.userIP = ip;
	}

}
