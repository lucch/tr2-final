package tr2.server;

public class Messages {
	public final static String Hello = "WHAZZUP, SERVERS";
	public final static String Alive = "I AM ALIVE, BABY";
	public final static String NotRecognized = "SPEAK ENGLISH, FUCKER";
	
	public final static String HelloResponse = "WELCOME, BROTHER";
	public final static String AliveResponse = "I'M GLAD YOU ARE ALIVE";

	public final static String NoOperation = "NO OPERATION";
	
	public static String getServerHelloMessage() {
		return Hello;
	}

	public static String getServerAliveMessage() {
		return Alive;
	}

	public static String parser(String message) {
		if (message.equals(Hello)) {
			return HelloResponse;
		} else if (message.equals(Alive)) {
			return AliveResponse;
		} else if (message.equals(HelloResponse)) {
			return NoOperation;
		} else if (message.equals(AliveResponse)) {
			return NoOperation;
		} else {
			return NotRecognized;
		}
	}
}
