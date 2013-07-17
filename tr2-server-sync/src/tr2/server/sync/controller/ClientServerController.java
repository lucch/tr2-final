package tr2.server.sync.controller;

import java.io.IOException;

import tr2.server.common.entity.Interval;
import tr2.server.common.series.protocol.Messages;
import tr2.server.common.tcp.ConnectionsManager;
import tr2.server.common.tcp.TCPController;
import tr2.server.common.util.JSONHelper;
import tr2.server.interval.data.Data;

public class ClientServerController implements TCPController {
	
	private Data data;
	
	private ConnectionsManager client;
	
	private String label = "[CLIENT CONTROLLER]";

	public ClientServerController(int clientsPort) throws IOException {
		client = new ConnectionsManager(this, clientsPort);
		data = new Data();
	}


	@Override
	public void notifyDisconnected(String address) {
		// TODO Auto-generated method stub
		data.removeClientDropInterval(address);
	}

	@Override
	public void notifyMessageReceived(String message, String localAddress,
			String address) {
		
		String response = null;

		if (message.equals(Messages.GET_INTERVAL)) {
			Interval i = data.getInterval(address);
			response = i.toJSON();
			try {
				client.sendTo(response, address);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			String[] req;
			req = message.split(Messages.SEPARATOR);
			if (req[0].equals(Messages.INTERVAL_CALCULATED)) {
				Interval i = JSONHelper.fromJSON(req[1], Interval.class);
				data.addCalculatedInterval(i);
				try {
					response = Messages.ACK;
					client.sendTo(response, address);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void notifyConnected(String address) {
		// TODO Auto-generated method stub
		
	}

}
