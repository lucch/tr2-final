package tr2.server;

public class HelloWorld {

	public static void main(String[] args) {
		HelloWorld h = new HelloWorld();
		h.sayHello();

		Thread multicastListener = new Thread(new MulticastReceiver());
		multicastListener.start();

		MulticastSender multicastSender = new MulticastSender();
		multicastSender.speak(Messages.serverHelloMessage);
		multicastSender.speak(Messages.serverAliveMessage);

	}

	private void sayHello() {
		System.out.println("Hello TR2!");
	}

}
