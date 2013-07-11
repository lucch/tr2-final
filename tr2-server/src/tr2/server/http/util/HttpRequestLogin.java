package tr2.server.http.util;

import tr2.server.http.util.HttpServerUtil;
import tr2.server.http.util.HttpHtmlTemplates;

public class HttpRequestLogin implements HttpRequestParser {
	private String msg = null;

	public HttpRequestLogin(String name) {
		if (validate(name)) {

		} else {

		}
	}

	public HttpRequestLogin() {
		msg = HttpServerUtil.getHttpOK();
		msg += HttpServerUtil.getHeader("Login");
		msg += HttpServerUtil.getTemplate(HttpHtmlTemplates.login);
		msg += HttpServerUtil.getFooter();
	}

	public String getMessage() {
		return msg;
	}

	private Boolean validate(String name) {
		// TODO check name exists
		return true;
	}
}
