package tr2.client;

import java.net.Socket;

public interface Listener {
	
	public void notify(Socket socket, String response);

}
