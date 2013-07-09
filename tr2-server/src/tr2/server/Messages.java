package tr2.server;

public class Messages {
	public final static String Hello = "HELLO";
	public final static String Alive = "Alive.";
	public final static String NotRecognized = ".";
	public final static String HelloResponse = "HELLOACK";
	
	public final static String NoOp = "NO OPERATION";
	
	public static String getServerHelloMessage() {
		return Hello;
	}

	public static String getServerAliveMessage() {
		return Alive;
	}

	public static String parser(String message) {
		if (message.equals(Hello)) {
			// Envia mensagem para que o servidor note que você está conectado
			// Coloca ele na sua lista de conexões
			return HelloResponse;
		} else if (message.equals(Alive)) {
			// Anota que o servidor que mandou alive está vivo
			return NoOp;
		} else {
			return NoOp;
		}
	}
}
