/*
 * Armazena informações sobre um servidor
 */

package tr2.server.common.data;

public class ServerInfo {
	private String address;
	private boolean active;

	public ServerInfo(String address) {
		this.address = address;
	}

	public boolean isActive() {
		return active;
	}
	
	public String getAddress() {
		return address;
	}
	
	@Override
	public boolean equals(Object obj) {
		ServerInfo serverInfo = (ServerInfo)obj;
		return serverInfo.address.equals(this.address);
	}
	
	@Override
	public String toString() {
		return address;
	}
}
