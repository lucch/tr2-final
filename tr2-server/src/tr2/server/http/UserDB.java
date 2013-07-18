package tr2.server.http;

import java.util.HashMap;

import tr2.server.common.entity.*;

public class UserDB {

	private static volatile HashMap<String, User> users;

	private static UserDB userdb;
	
	static {
		users = new HashMap<String, User>();
		
		User admin = new User();
		admin.setUserType(UserType.ADMIN);
		admin.setUsername("admin");
		
		User user = new User();
		user.setUsername("user");
		user.setUserType(UserType.USER);
		
		users.put(admin.getUsername(), admin);
		users.put(user.getUsername(), user);
	}

	public static HashMap<String, User> getUsers() {
		return users;
	}
	
	public static void setUsers(HashMap<String, User> users) {
		UserDB.users = users;
		System.out.println("!!!!SETTING USER!!!!");
	}
	
	private UserDB() {}

	public static UserDB instance() {
		if (userdb == null) {
			userdb = new UserDB();
		}
		return userdb;
	}

	public boolean isUser(String name) {
		return users.containsKey(name);
	}

	public User getUser(String name) {
		return users.get(name);
	}

	public void updateUser(User user) {
		users.put(user.getUsername(), user);
	}

	public void updateNameByIp(String ip, String name) {
		for (String n : users.keySet()) {
			User user = users.get(n);
			String nip = user.getUserIP();
			if (nip == null) continue;
			if (nip.equals(ip)) {
				user.setUsername(name);
				updateUser(user);
				return;
			}
		}
	}

	public void addUser(User user) {
		users.put(user.getUsername(), user);
	}

	public void removeUser(String name) {
		if (users.containsKey(name)) {
			users.remove(name);
		}
	}

}
