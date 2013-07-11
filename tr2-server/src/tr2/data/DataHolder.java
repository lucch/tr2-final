/*
 * Controla a aplicação
 */

package tr2.data;

import java.util.ArrayList;

public class DataHolder {
	private ArrayList<ServerInfo> serversInfo;

	public DataHolder() {
		serversInfo = new ArrayList<ServerInfo>();
	}

	public ServerInfo addServerInfo(String address) {
		ServerInfo serverInfo = new ServerInfo(address);

		for (int i = 0; i < serversInfo.size(); i++) {
			if (serversInfo.get(i).equals(serverInfo)) {
				return null;
			}
		}

		serversInfo.add(serverInfo);
		System.out.println("Added address: " + serverInfo);
		
		return serverInfo;
	}

}
