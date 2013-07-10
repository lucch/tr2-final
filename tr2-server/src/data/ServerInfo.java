/*
 * Armazena informa›es sobre um servidor
 */

package data;

public class ServerInfo {
	private String address;
	private int port;
	
	public ServerInfo(String address) {
		this.address = address;
	}
	
	public String getAddress() {
		return address;
	}
}
