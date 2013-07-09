package tr2.server;

public class PeriodicMulticastMessage implements Runnable {
	private Multicast channel;
	private String message;
	private long millis;

	public PeriodicMulticastMessage(Multicast channel, String message,
			long millis) {
		this.channel = channel;
		this.millis = millis;
		this.message = message;
	}

	public void run() {
		while (true) {
			channel.speak(message);
			try {
				Thread.sleep(millis);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
