package tr2.server.common.series.protocol;

public class SeriesRequest {

	public String prepare(String command, String payload) {

		if (!isValid(command))
			return null;

		StringBuilder request = new StringBuilder();

		request.append(command);
//		request.append("\n");
		if ((payload != null) && (!payload.equals(""))) {
			request.append(Messages.SEPARATOR);
			request.append(payload);
		}
		request.append(Messages.END);
//		request.append("\n");

		return request.toString();
	}

	// TODO: Validate the command.
	private boolean isValid(String command) {
		return true;
	}

}
