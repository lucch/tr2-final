package tr2.server.http;

import java.util.HashMap;

import tr2.server.common.entity.*;

public class UserDB {

	private static HashMap<String, User> users = new HashMap<String, User>();

	private static UserDB userdb;

	public static HashMap<String, User> getUsers() {
		return users;
	}
	
	public static void setUsers(HashMap<String, User> users) {
		UserDB.users = users;
	}

	public static UserDB instance() {
		if (userdb == null) {
			userdb = new UserDB();
			User admin = new User();
			admin.setUserType(UserType.ADMIN);
			admin.setUsername("admin");
			User user = new User();
			user.setUsername("user");
			user.setUserType(UserType.USER);
			userdb.addUser(admin);
			userdb.addUser(user);
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
		User user = new User();
		for (String n : users.keySet()) {
			user = users.get(n);
			String nip = user.getUserIP();
			if (nip.equals(ip)) {
				System.out.print("\n achou \n");
				user.setUsername(name);
				updateUser(user);
				return;
			}
		}
		System.out.print("\n não achou \n");
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
