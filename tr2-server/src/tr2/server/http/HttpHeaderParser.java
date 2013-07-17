package tr2.server.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import tr2.server.http.exception.BadRequestException;

public class HttpHeaderParser {
	private ArrayList<String> pages = new ArrayList<String>();
	private HashMap<String, String> data = new HashMap<String, String>();
	
	
	public HttpHeaderParser(InputStream stream) throws BadRequestException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		
		try {
			String linha = reader.readLine();
			String[] headerStr = linha.split(" ");
			if (!headerStr[0].equals("GET") || headerStr.length <= 2) {
				throw new BadRequestException();
			}
			
			if (headerStr[1].equals("/")) {
				pages.add("index");
				return;
			}
			
			linha = headerStr[1].substring(1, headerStr[1].length());
			
			if(linha.indexOf("?") >= 0){
				String[] params = linha.split("\\?");
				linha = params[0];
				params = params[1].split("&");
				
				for(int i = 0;i<params.length;i++) {
					String[] new_params = params[i].split("=");
					if (new_params.length < 2)
						throw new BadRequestException();
					
					data.put(new_params[0], new_params[1]);
				}
			}
			
			String[] vecPages = linha.split("/");
			
			
			for(String page : vecPages)
				pages.add(page);
				
			
			if (headerStr.length > 1) {

			}
		} catch (IOException ioe) {
			throw new BadRequestException();
		}

	}
	
	public HashMap<String,String> getData() {
		return data;
	}

	public ArrayList<String> getPages() {
		return pages;
	}
}
