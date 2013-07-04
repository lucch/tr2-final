package tr2.server;

public class Messages {
	public final static String serverHelloMessage = "WHAZZUP, SERVERS";
	public final static String serverAliveMessage = "I AM ALIVE, BABY";
	public final static String messageNotRecognized = "SPEAK ENGLISH, FUCKER";
	
	public final static String serverHelloResponse = "WELCOME, BROTHER";
	public final static String serverAliveResponse = "I'M GLAD YOU ARE ALIVE";

	public static String getServerHelloMessage() {
		return serverHelloMessage;
	}

	public static String getServerAliveMessage() {
		return serverAliveMessage;
	}

	public static String parser(String message) {
		if (message.equals(serverHelloMessage)) {
			return serverHelloResponse;
		} else if (message.equals(serverAliveMessage)) {
			return serverAliveResponse;
		} else {
			return messageNotRecognized;
		}
	}
}
