/*
 * Controla a aplicação
 */

package tr2.server.common.data;

import java.util.ArrayList;
import java.util.Date;

public class ServerData {
	
	private ArrayList<ServerInfo> serversInfo;
	
	private boolean active;
	
	private int activeIndex;
	
	private Date startTime;

	public ServerData() {
		serversInfo = new ArrayList<ServerInfo>();		
		activeIndex = -1;
		startTime = new Date();
	}
	
	public long getTime() {
		return startTime.getTime();
	}
	
	public int getActiveIndex() {
		return activeIndex;
	}
	
	public void setActive() {
		active = true;
	}
	
	public void setPassive() {
		active = false;
	}

	public boolean isActive() {
		return active;
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

	public void setServerActive(String address) {
		int serverIndex = findServerInfo(address);

		activeIndex = serverIndex;
	}
	
	// returns false if the manager was lost
	public boolean removeServerInfo(String address) {
		int serverIndex = findServerInfo(address);

		if (serverIndex < serversInfo.size())
			serversInfo.remove(serverIndex);
		
		printServersInfo();

		if (activeIndex == serverIndex) {
			// TODO manager has dropped
			activeIndex = -1;
			return false;
		}
		
		return true;
		
	}

	public void printServersInfo() {
		System.out.println("[DATA] Servers Info:");
		for (int i = 0; i < serversInfo.size(); i++) {
			System.out.println("[DATA] " + serversInfo.get(i));
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
