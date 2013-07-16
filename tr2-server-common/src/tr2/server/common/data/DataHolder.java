/*
 * Controla a aplicação
 */

package tr2.server.common.data;

import java.util.ArrayList;

public class DataHolder {
	private ArrayList<ServerInfo> serversInfo;

	public DataHolder() {
		serversInfo = new ArrayList<ServerInfo>();
	}

	public boolean addServerInfo(String address) {
		int serverIndex = findServerInfo(address);

		if (serverIndex == serversInfo.size()) {
			// if server isn't found
			ServerInfo serverInfo = new ServerInfo(address);
			serversInfo.add(new ServerInfo(address));
			System.out.println("Added address: " + serverInfo);
			// returns server added right now
			return true;
		}

		printServersInfo();
		
		// if server was already here, returns null 
		return false;
	}

	public int findServerInfo(String address) {
		ServerInfo serverInfo = new ServerInfo(address);

		int i;
		for (i = 0; i < serversInfo.size(); i++) {
			if (serversInfo.get(i).equals(serverInfo)) {
				break;
			}
		}

		return i;
	}

	public void removeServerInfo(String address) {
		int serverIndex = findServerInfo(address);

		if (serverIndex < serversInfo.size())
			serversInfo.remove(serverIndex);
		
		printServersInfo();
	}

	public void printServersInfo() {
		System.out.println("@ Servers Info:");
		for (int i = 0; i < serversInfo.size(); i++) {
			System.out.println(serversInfo.get(i));
		}
	}
	
	public ArrayList<ServerInfo> getServersInfo() {
		return serversInfo;
	}
	
	public String serversInfoToString() {
		String str = "";
		for (int i = 0; i < serversInfo.size(); i++) {
			str += serversInfo.get(i).getAddress();
			if (i < serversInfo.size() - 1)
				str += "/";
		}
		
		return str;
	}
}
