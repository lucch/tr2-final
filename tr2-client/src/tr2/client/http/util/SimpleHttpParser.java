package tr2.client.http.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import tr2.client.http.exception.BadRequestException;

public class SimpleHttpParser {

	public String parse(InputStream inputStream) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				inputStream));
		StringBuilder result = new StringBuilder();

		// TODO: This implementation is inefficient!!! Optimize!		
		char c, end = 0;
		while (true) {
			c = (char) reader.read();
			result.append(c);
			if (c == '\r')
				continue;
			if (c == '\n') {
				if (end == 0) {
					end = 1;
				} else {
					break;
				}
			} else {
				end = 0;
			}
		}

		// We're only accepting GET requests by now.
		String method = result.substring(0, 3);
		if(!method.equals("GET"))
			throw new BadRequestException();
				
		return result.toString();
	}

}
