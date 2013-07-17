package tr2.server.sync.controller;

import java.io.IOException;

import tr2.server.common.entity.Interval;
import tr2.server.common.series.protocol.Messages;
import tr2.server.common.tcp.ConnectionsManager;
import tr2.server.common.tcp.TCPController;
import tr2.server.common.util.JSONHelper;

public class ClientServerController implements TCPController {
	
//	private ClientData clientData;
	
	private ConnectionsManager client;
	
	private String label = "[CLIENT CONTROLLER]";

	public ClientServerController(int clientsPort) throws IOException {
		client = new ConnectionsManager(this, clientsPort);
//		clientData = new ClientData();
	}


	@Override
	public void notifyDisconnected(String address) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyMessageReceived(String message, String localAddress,
			String address) {
		// TODO Auto-generated method stub
		
		String response = null;
		// somente para testar, MUDAR AQUI
		if (message.equals(Messages.GET_INTERVAL)) {
			Interval i = new Interval();
			response = i.toJSON();
			try {
				client.sendToAllConnections(response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			String[] req;
			req = message.split(Messages.SEPARATOR);
			if(req[0].equals(Messages.INTERVAL_CALCULATED)) {
				Interval i = JSONHelper.fromJSON(req[1], Interval.class);
				try {
					client.sendToAllConnections("ACK");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(label + "Result: " + i.getResult());
			}
		}
	}

	@Override
	public void notifyConnected(String address) {
		// TODO Auto-generated method stub
		
	}

}
