package tr2.server;

public class HelloWorld {

	public static void main(String[] args) {
		HelloWorld h = new HelloWorld();
		h.sayHello();

		Thread multicastListener = new Thread(new MulticastReceiver());
		multicastListener.start();

		MulticastSender multicastSender = new MulticastSender();
		multicastSender.speak("alo amor1");
		multicastSender.speak("alo amor2");
		multicastSender.speak("alo amor3");
		multicastSender.speak("alo amor4");
		multicastSender.speak("alo amor5");
	}

	private void sayHello() {
		System.out.println("Hello TR2!");
	}

}
