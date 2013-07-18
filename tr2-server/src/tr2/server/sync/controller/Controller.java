package tr2.server.sync.controller;

import java.io.IOException;

import tr2.server.common.util.NetworkConstants;
import tr2.server.interval.data.Data;

public class Controller {
	private Data data;
	private P2PController p2p;
	
	public Controller() throws IOException {
		data = new Data();
		
		p2p = new P2PController(
				data, NetworkConstants.TCP_SERVER_TO_SERVER_PORT);
		p2p.start();

		new ClientServerController(
				data, NetworkConstants.REMOTE_SERIES_SERVER_PORT);
	}

}
