package tr2.server.http.util;

import tr2.server.http.util.HttpHtmlTemplates;
import tr2.server.http.util.HttpHeaderTemplates;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HttpServerUtil {
	
	public static String getHeader(String title){
		return replaceTemplateParam(getTemplate(HttpHtmlTemplates.head),"title",title);

	}
	
	public static String getFooter(){
		return getTemplate(HttpHtmlTemplates.footer);
	}
	
	public static String getTemplate(String tpl) {
		try {
	        StringBuffer fileData = new StringBuffer();
	        BufferedReader reader = new BufferedReader(new FileReader(tpl));
	        char[] buf = new char[1024];
	        int numRead=0;
	        while((numRead=reader.read(buf)) != -1){
	        	String readData = String.valueOf(buf, 0, numRead);
	            fileData.append(readData);
	        }
	        reader.close();
	        return fileData.toString();
		}catch (IOException e) {
			return "Arquivo não encontrado: " + tpl;
		}
	}
	
	public static String replaceTemplateParam(String tmpl, String param, String value) {
		return tmpl.replace("{"+param+ "}", value);
	}
	
	public static String getHttpOK() {
		return getTemplate(HttpHeaderTemplates.ok) + "\n";
	}
}