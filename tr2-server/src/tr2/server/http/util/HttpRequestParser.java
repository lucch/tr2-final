package tr2.server.http.util;

import java.util.HashMap;

import tr2.server.common.entity.User;
import tr2.server.common.entity.UserType;
import tr2.server.common.util.NetworkConstants;
import tr2.server.http.UserDB;
import tr2.server.http.exception.BadRequestException;

public class HttpRequestParser {
	public static final String EOF = "EOF";

	public static String editUsers() throws BadRequestException {
		String msg = null;

		msg = HttpServerUtil.getHttpOK();
		msg += HttpServerUtil.getHeader("Lista de usuários");
		msg += HttpServerUtil.getTemplate(HttpHtmlTemplates.admin_menu);
		msg += HttpServerUtil.getTemplate(HttpHtmlTemplates.edit_users);
		
		UserDB userDB = UserDB.instance();
		HashMap<String,User> users = userDB.getUsers();
		
		for (String name : users.keySet()) {
			User user = users.get(name);
			HashMap<String,String> user_map = new HashMap<String,String>();
			user_map.put("name", user.getUsername());
			user_map.put("type", user.getUserType().toString());
			msg += HttpServerUtil.getTemplate(HttpHtmlTemplates.edit_users_row,user_map);
		}
		
		msg += HttpServerUtil.getTemplate(HttpHtmlTemplates.edit_users_footer);
		msg += HttpServerUtil.getFooter();
		return msg;
	}

	public static String index() throws BadRequestException {
		String msg = null;
		msg = HttpServerUtil.getHttpOK();
		msg += HttpServerUtil.getHeader("Login");
		msg += HttpServerUtil.getTemplate(HttpHtmlTemplates.login);
		msg += HttpServerUtil.getFooter();
		return msg;
	}
	
	public static String login(String name, String ip) throws BadRequestException {
		UserDB userDB = UserDB.instance();
		if (userDB.isUser(name)) {
			User user = userDB.getUser(name);
			
			user.setUserIP(ip);
			userDB.updateUser(user);
			System.out.print(user.getUserIP());
			HashMap<String,String> data = new HashMap<String,String>();
			data.put("type", user.getUserType().toString().toLowerCase() + "/");
			data.put("port",String.valueOf(NetworkConstants.LOCAL_CLIENT_PORT));
			return HttpServerUtil.getTemplate(HttpHeaderTemplates.redirect,data);
		} else {
			HashMap<String,String> data = new HashMap<String,String>();
			data.put("type", "");
			data.put("port",String.valueOf(NetworkConstants.LOCAL_CLIENT_PORT));
			return HttpServerUtil.getTemplate(HttpHeaderTemplates.redirect,data);
		}
	}

	public static String admin() throws BadRequestException {
		String msg = null;
		msg = HttpServerUtil.getHttpOK();
		msg += HttpServerUtil.getHeader("Administração");
		msg += HttpServerUtil.getTemplate(HttpHtmlTemplates.admin_menu);
		msg += getIntervalBody("admin");
		msg += HttpServerUtil.getFooter();
		return msg;
	}

	private static String getIntervalBody(String type)
			throws BadRequestException {
		String msg = null;

		msg = HttpServerUtil.getTemplateDecider(
				HttpServerUtil.getTemplate(HttpHtmlTemplates.intervals), type);

		/*
		 * TODO: get intervals data for (Interval in : intervals) {
		 * HashMap<String,String> data = new HashMap<String,String>();
		 * data.put("interval", in.getFirstDenominator() + " - " +
		 * in.getLastDenominator()); data.put("state",
		 * in.getIntervalState().toString()); data.put("client",
		 * in.getClientIP()); if (in.getIntervalState() ==
		 * IntervalState.FINISHED) { data.put("result",
		 * String.valueOf(in.getResult())); } else { data.put("result", "-"); }
		 * 
		 * 
		 * Integer index; if (in.getFirstDenominator() > 0) { index =
		 * (int)in.getFirstDenominator()/10000; } else { index = 0; }
		 * 
		 * if (type == "admin") msg +=
		 * HttpServerUtil.getTemplateDecider(HttpServerUtil
		 * .getTemplate(HttpHtmlTemplates
		 * .intervals_row,data),type,"index",index.toString()); else msg +=
		 * HttpServerUtil
		 * .getTemplateDecider(HttpServerUtil.getTemplate(HttpHtmlTemplates
		 * .intervals_row,data),type); }
		 */
		msg += HttpServerUtil.getTemplate(HttpHtmlTemplates.intervals_footer);

		return msg;
	}

	public static String logout() throws BadRequestException {
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("type", "");
		data.put("port", String.valueOf(NetworkConstants.LOCAL_CLIENT_PORT));
		return HttpServerUtil.getTemplate(HttpHeaderTemplates.redirect, data);
	}

	public static String addUser() throws BadRequestException {
		String msg = HttpServerUtil.getHttpOK();
		msg += HttpServerUtil.getHeader("Adicionar usuário");
		msg += HttpServerUtil.getTemplate(HttpHtmlTemplates.admin_menu);
		msg += HttpServerUtil.getTemplate(HttpHtmlTemplates.add_user);
		msg += HttpServerUtil.getFooter();
		return msg;
	}
	
	public static String addUser(String name, String type) throws BadRequestException  {
		UserDB userDB = UserDB.instance();
		User user = new User();
		
		user.setUsername(name);
		user.setUserType(UserType.valueOf(type));
		userDB.addUser(user);
		
		HashMap<String,String> data = new HashMap<String,String>();
		data.put("type", "admin/edit_users");
		data.put("port",String.valueOf(NetworkConstants.LOCAL_CLIENT_PORT));
		return HttpServerUtil.getTemplate(HttpHeaderTemplates.redirect,data);
	}

	public static String editUserAdmin(String name, String type)
			throws BadRequestException {
		String msg = null;
		msg = HttpServerUtil.getHttpOK();
		msg += HttpServerUtil.getHeader("Editar usuário");
		msg += HttpServerUtil.getTemplate(HttpHtmlTemplates.admin_menu);
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("name", name);
		data.put("type", type);
		msg += HttpServerUtil.getTemplate(HttpHtmlTemplates.edit_user_admin,
				data);

		msg += HttpServerUtil.getFooter();
		return msg;
	}
	
	public static String updateUser(String name, String type, String oldname) throws BadRequestException {
		UserDB userDB = UserDB.instance();
		User user = new User();
		
		if(userDB.isUser(oldname)) {
			user = userDB.getUser(oldname);
			user.setUsername(name);
			user.setUserType(UserType.valueOf(type));
		}
		
		HashMap<String,String> data = new HashMap<String,String>();
		data.put("type", "admin/edit_users");
		data.put("port",String.valueOf(NetworkConstants.LOCAL_CLIENT_PORT));
		return HttpServerUtil.getTemplate(HttpHeaderTemplates.redirect,data);
	}
	
	public static String removeUser(String name) throws BadRequestException {
		UserDB userDB = UserDB.instance();

		if(userDB.isUser(name)) {
			userDB.removeUser(name);
		}
		
		HashMap<String,String> data = new HashMap<String,String>();
		data.put("type", "admin/edit_users");
		data.put("port",String.valueOf(NetworkConstants.LOCAL_CLIENT_PORT));
		return HttpServerUtil.getTemplate(HttpHeaderTemplates.redirect,data);
	}
	
	public static String editUser() throws BadRequestException {
		String msg = null;
		msg = HttpServerUtil.getHttpOK();
		msg += HttpServerUtil.getHeader("Editar conta");
		msg += HttpServerUtil.getTemplate(HttpHtmlTemplates.user_menu);
		msg += HttpServerUtil.getTemplate(HttpHtmlTemplates.edit_user);
		msg += HttpServerUtil.getFooter();
		return msg;
	}
	
	public static String editUser(String ip, String name) throws BadRequestException  {
		UserDB userDB = UserDB.instance();
		userDB.updateNameByIp(ip, name);
		User user = userDB.getUser(name);
		System.out.print(user.getUserIP());

		HashMap<String,String> data = new HashMap<String,String>();
		data.put("type", "user/");
		data.put("port",String.valueOf(NetworkConstants.LOCAL_CLIENT_PORT));
		return HttpServerUtil.getTemplate(HttpHeaderTemplates.redirect,data);
	}

	public static String intervals(String type) throws BadRequestException {
		String msg = null;
		msg = HttpServerUtil.getHttpOK();
		msg += HttpServerUtil.getHeader("Intervalos calculados");

		if (type == "admin")
			msg += HttpServerUtil.getTemplate(HttpHtmlTemplates.admin_menu);
		else
			msg += HttpServerUtil.getTemplate(HttpHtmlTemplates.user_menu);
		msg += getIntervalBody(type);
		msg += HttpServerUtil.getFooter();
		return msg;
	}

	public static String servers(String type) throws BadRequestException {
		String msg = null;
		msg = HttpServerUtil.getHttpOK();

		if (type == "user")
			msg += HttpServerUtil.getTemplate(HttpHtmlTemplates.user_menu);
		else
			msg += HttpServerUtil.getTemplate(HttpHtmlTemplates.admin_menu);

		msg += HttpServerUtil.getHeader("Servidores");
		msg += HttpServerUtil.getTemplate(HttpHtmlTemplates.servers);

		/*
		 * TODO: get servers for(Server server : servers) {
		 * HashMap<String,String> data = new HashMap<String,String>();
		 * data.put("server", server.getIndex().toString()); data.put("state",
		 * server.getState().toString()); data.put("time",
		 * server.getTime().toString()); msg +=
		 * HttpServerUtil.getTemplate(HttpHtmlTemplates.servers_row,data); }
		 */

		msg += HttpServerUtil.getTemplate(HttpHtmlTemplates.servers_footer);
		msg += HttpServerUtil.getFooter();
		return msg;
	}

	public static String result(String type) throws BadRequestException {
		String msg = null;
		msg = HttpServerUtil.getHttpOK();

		if (type == "user")
			msg += HttpServerUtil.getTemplate(HttpHtmlTemplates.user_menu);
		else
			msg += HttpServerUtil.getTemplate(HttpHtmlTemplates.admin_menu);

		msg += HttpServerUtil.getHeader("Resultado");
		// TODO: get total result
		// Double res = getResultado();
		// msg +=
		// HttpServerUtil.getTemplate(HttpHtmlTemplates.result,res.toString());
		msg += HttpServerUtil.getFooter();
		return msg;
	}

	public static String user() throws BadRequestException {
		String msg = null;
		msg = HttpServerUtil.getHttpOK();
		msg += HttpServerUtil.getHeader("Usuário");
		msg += HttpServerUtil.getTemplate(HttpHtmlTemplates.user_menu);
		msg += getIntervalBody("user");
		msg += HttpServerUtil.getFooter();
		return msg;
	}
}