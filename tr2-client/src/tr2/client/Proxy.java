package tr2.client;

public class Proxy {
	
	private static Proxy proxy;

	public static Proxy instance() {
		if (proxy == null) {
			proxy = new Proxy();
		}
		return proxy;	
	}

	/**
	 * Make a request to a remote server.
	 * 
	 * @param request The request to be processed by the remote server.
	 * @return Response from the remote server.
	 */
	public String request(String request) {
		// TODO Auto-generated method stub
		return request;
	}
	
}
