package tr2.client.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SimpleHttpParser {

	public String parse(InputStream inputStream) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				inputStream));
		StringBuilder result = new StringBuilder();

		// TODO: This implementation is inefficient!!! Optimize!		
		int c, end = 0;
		while (true) {
			c = reader.read();
			result.append((char) c);
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
		
		// Check if it is a GET.

		return result.toString();
	}

}
