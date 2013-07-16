package tr2.server.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import tr2.server.http.exception.BadRequestException;

public class HttpHeaderParser {
	private String page;
	private HashMap<String, String> data = new HashMap<String, String>();
	
	
	public HttpHeaderParser(InputStream stream) throws BadRequestException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		
		try {
			String linha = reader.readLine();
			String method = linha.substring(0,3);
			if (!method.equals("GET")) {
				throw new BadRequestException();
			}
			
			if (linha.length() == 14) {
				this.page = "index";
				return ;
			}
			
			linha = linha.substring(5, linha.length()-9);
			Integer index = linha.indexOf("?");
			
			if (index >= 0) {
				this.page = linha.substring(0,index);
			} else {
				//Has no parameters
				this.page = linha;
				return ;
			}
			
			linha = linha.substring(index+1,linha.length());
			String[] params = linha.split("&");
			
			for(int i = 0;i<params.length;i++) {
				String[] new_params = params[i].split("=");
				data.put(new_params[0], new_params[1]);
			}
		} catch (IOException ioe) {
			System.out.println(ioe);
		}

	}
	
	public HashMap<String,String> getData() {
		return data;
	}
	
	public String getPage() {
		return page;
	}
}
