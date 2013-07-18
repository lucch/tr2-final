package tr2.server.http;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import tr2.server.http.exception.*;
import tr2.server.http.util.*;

import java.util.ArrayList;
import java.util.HashMap;

//import tr2.server.exception.BadRequestException;


public class HttpServerInstance implements Runnable {
	private Socket socket;

	public HttpServerInstance(final Socket socket) {
		this.socket = socket;
	}

	private void selectPage() {
		try {
			HttpHeaderParser header = new HttpHeaderParser(socket.getInputStream());
			HashMap <String,String> data = header.getData();
			ArrayList<String> pages = header.getPages();
			String msg = null;
			
			try {
				if (pages.get(0).equals("index")) {
					msg = HttpRequestParser.index();
				} else if (pages.get(0).equals("logout")) {
					msg = HttpRequestParser.logout();
				} else if (pages.get(0).equals("login")) {
					if (!data.containsKey("nome")) {
						throw new BadRequestException();
					}
					msg = HttpRequestParser.login(data.get("nome"));
					
					
				/* ADMIN */
				} else if (pages.get(0).equals("admin")) {
					if(pages.size() == 1) {
						msg = HttpRequestParser.admin(data.get("name"));
					} else if (pages.get(1).equals("add_user")) {
						if (data.containsKey("name") && data.containsKey("type")) {
							msg = HttpRequestParser.addUser(data.get("name"), data.get("type"),data.get("myname"));
						} else {
							msg = HttpRequestParser.addUser(data.get("myname"));
						}
					} else if (pages.get(1).equals("edit_user_admin")) {
						if (data.containsKey("name") && data.containsKey("type")) {
							msg = HttpRequestParser.editUserAdmin(data.get("name"),data.get("type"),data.get("myname"));
						} else {
							throw new BadRequestException();
						}
					} else if (pages.get(1).equals("update_user")) {
						if (data.containsKey("name") && data.containsKey("type") && data.containsKey("oldname")) {
							msg = HttpRequestParser.updateUser(data.get("name"),data.get("type"),data.get("oldname"),data.get("myname"));
						} else {
							throw new BadRequestException();
						}
					} else if (pages.get(1).equals("remove_user")) {
						msg = HttpRequestParser.removeUser(data.get("name"),data.get("myname"));
					} else if (pages.get(1).equals("edit_users")) {
						msg = HttpRequestParser.editUsers(data.get("name"));
					} else if (pages.get(1).equals("intervals")) {
						msg = HttpRequestParser.intervals("admin",data.get("name"));
					} else if (pages.get(1).equals("remove_seq")) {
						//TODO: remove sequence
					} else if (pages.get(1).equals("servers")) {
						msg = HttpRequestParser.servers("admin",data.get("name"));
					} else if (pages.get(1).equals("result")) {
						msg = HttpRequestParser.result("admin",data.get("name"));
					}
					
					
				/* USER */
				} else if (pages.get(0).equals("user")) {
					if(pages.size() == 1) {
						msg = HttpRequestParser.user(data.get("name"));
					} else if (pages.get(1).equals("edit_user")) {
						if (data.containsKey("newname") && data.containsKey("oldname")) {
							msg = HttpRequestParser.editUser(data.get("newname"),data.get("oldname"));
						} else if (data.containsKey("name")){
							msg = HttpRequestParser.editUser(data.get("name"));
						} else {
							throw new BadRequestException();
						}
					} else if (pages.get(1).equals("intervals")) {
						msg = HttpRequestParser.intervals("user",data.get("name"));
					} else if (pages.get(1).equals("servers")) {
						msg = HttpRequestParser.servers("user",data.get("name"));
					} else if (pages.get(1).equals("result")) {
						msg = HttpRequestParser.result("user",data.get("name"));
					}
				}
			} catch (BadRequestException bre) {
				msg = HttpServerUtil.getHttpBadRequest();
			}
			
			if (msg == null){
				//msg = HttpServerUtil.getHttpBadRequest();
				socket.close();
				return;
			}
			
			msg += "\n";
			//msg += "\n" + HttpRequestParser.EOF + "\n";
			
			System.out.print(msg + "\n");
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			writer.write(msg);
			writer.flush();
			socket.close();
			//writer.close();
		} catch (BadRequestException e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}
	
	@Override
	public void run() {
//		while(true) {
//			selectPage();
//		}
		try {
			selectPage();
		} catch (Exception e) {
			try {
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				e.printStackTrace();
			}
		}
	}
}
