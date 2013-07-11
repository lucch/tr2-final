package tr2.server.http;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import tr2.server.http.util.*;
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
			String page = header.getPage();
			String msg = null;
			
			if (page.equals("index")) {
				HttpRequestParser loginRequest;
				loginRequest = new HttpRequestLogin();
				msg = loginRequest.getMessage();
			} else if (page.equals("add_user")) {
				
			} else if (page.equals("remove_user")) {
				
			} else if (page.equals("user_view")) {
			} else if (page.equals("remove_data")) {
				
			} else if (page.equals("error")) {
			} else if (page.equals("login")) {
				HttpRequestParser loginRequest;
				if (data.containsKey("nome")) {
					loginRequest = new HttpRequestLogin(data.get("nome"));
				} else {
					loginRequest = new HttpRequestLogin();
				}
				msg = loginRequest.getMessage();
			}
			
			msg += "\nEOF\n"; // Marks EOF. TODO: Extract to the messages file.
			
			System.out.print(msg);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			writer.write(msg);
			writer.flush();
			//writer.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	
		
	}
	
	@Override
	public void run() {
		selectPage();
//		try {
//			selectPage();
//		} catch (Exception e) {
//			try {
//				socket.close();
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			} finally {
//				e.printStackTrace();
//			}
//		}
	}
}
