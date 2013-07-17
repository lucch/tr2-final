package tr2.server.common.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JSONHelper {

	@SuppressWarnings("unchecked")
	public static <T extends JSONable> T fromJSON(String json, Class<T> name) {
		try {
			for (Method m : name.getMethods()) {
				if (m.getName().equals("fromJSON"))
					return (T) m.invoke(name.newInstance(), json);
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return null;
	}
	
}
