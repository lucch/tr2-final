package tr2.server.common.util;

public interface JSONable {

	public String toJSON();
	
	public JSONable fromJSON(String json);
}
