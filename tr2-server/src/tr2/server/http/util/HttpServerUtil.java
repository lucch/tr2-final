package tr2.server.http.util;

import tr2.server.http.exception.BadRequestException;
import tr2.server.http.util.HttpHtmlTemplates;
import tr2.server.http.util.HttpHeaderTemplates;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class HttpServerUtil {
	
	public static String getHeader(String title) throws BadRequestException{
		return getTemplate(HttpHtmlTemplates.head,"title",title);

	}
	
	public static String getTemplateDecider(String msg, String type,String param, String value) throws BadRequestException {
		//String msg = getTemplate(tpl);
		String nmsg = null;
		if (msg.indexOf("{" + type + "}") >= 0) {
			nmsg = msg.replace("{" + type + "}", "");
			nmsg = nmsg.replace("{/" + type + "}", "");
			nmsg = replaceTemplateParam(nmsg,param,value);
		} else {
			nmsg = msg.substring(0,msg.indexOf("{" + type + "}"));
			nmsg += msg.substring(msg.indexOf("{/" + type + "}") + 3 + type.length(), msg.length());
		}
		return nmsg;
	}
	
	public static String getTemplateDecider(String msg, String type) throws BadRequestException {
		//String msg = getTemplate(tpl);
		String nmsg = null;
		if (msg.indexOf("{" + type + "}") >= 0) {
			nmsg = msg.replace("{" + type + "}", "");
			nmsg = nmsg.replace("{/" + type + "}", "");
		} else {
			type = "admin";
			nmsg = msg.substring(0,msg.indexOf("{" + type + "}"));
			nmsg += msg.substring(msg.indexOf("{/" + type + "}") + 3 + type.length(), msg.length());
		}
		return nmsg;
	}
	
	public static String getFooter() throws BadRequestException{
		return getTemplate(HttpHtmlTemplates.footer);
	}
	
	public static String getTemplate(String tpl,Map<String,String> params) throws BadRequestException {
		String msg = getTemplate(tpl);
		for(String param : params.keySet()) {
			msg = replaceTemplateParam(msg,param,params.get(param));
		}
		return msg;
	}
	
	public static String getTemplate(String tpl, String param, String value) throws BadRequestException {
		return replaceTemplateParam(getTemplate(tpl),param,value);
	}
	
	private static String replaceTemplateParam(String tmpl, String param, String value) {
		return tmpl.replace("{"+param+ "}", value);
	}
	
	public static String getHttpOK() throws BadRequestException {
		return getTemplate(HttpHeaderTemplates.ok) + "\n";
	}
	
	public static String getHttpBadRequest() throws BadRequestException {
		return getTemplate(HttpHeaderTemplates.badRequest) + "\n";
	}
	
	public static String getTemplate(String tpl) throws BadRequestException {
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
		} catch (IOException e) {
			 throw new BadRequestException();
		}
	}
}