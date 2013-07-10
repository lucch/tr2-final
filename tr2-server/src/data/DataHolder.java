/*
 * Controla a aplicação
 */

package data;

import java.util.LinkedList;

public class DataHolder {
	private LinkedList<ServerInfo> serversInfo;

	public DataHolder() {
		serversInfo = new LinkedList<ServerInfo>();
	}
	
	public void addServerInfo(String address) {
		ServerInfo serverInfo = new ServerInfo(address);
		if (serversInfo.isEmpty()) {
			serversInfo.add(serverInfo);
		} else {
			serversInfo.getLast();
			ServerInfo last = serversInfo.getLast();

			int index = 0;
			ServerInfo current;

			do {
				current = serversInfo.get(index);
				// verifies if server IP is present
				if (current.getAddress().equals(serverInfo.getAddress()))
					return;

				index++;
			} while (!current.equals(last));

			serversInfo.add(serverInfo);
		}
	}

}
