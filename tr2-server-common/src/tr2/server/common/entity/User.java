package tr2.server.common.entity;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import tr2.server.common.util.JSONable;

public class User implements JSONable {

	private String userName;
	
	private String userIP;
	
	private UserType userType;

	public User() {
	}

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

	@SuppressWarnings("unchecked")
	@Override
	public String toJSON() {
		JSONObject obj = new JSONObject();
		obj.put("userName", this.userName);
		obj.put("userType", this.userType.getCode());
		return obj.toJSONString();
	}

	@Override
	public JSONable fromJSON(String json) {
		JSONParser parser = new JSONParser();
		JSONObject obj = null;
		User user = null;
		try {
			obj = (JSONObject) parser.parse(json);
			user = new User();
			user.setUsername((String) obj.get("userName"));
			UserType u = UserType.fromCode((Long) obj.get("userType"));
			user.setUserType(u);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return user;
	}

}
