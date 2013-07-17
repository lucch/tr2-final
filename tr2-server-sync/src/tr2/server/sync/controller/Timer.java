package tr2.server.sync.controller;

import java.io.IOException;

public class Timer implements Runnable {

	private long millis;

	private TimerController controller;

	private int type;

	private boolean loop;

	public Timer(TimerController controller, long millis, boolean loop, int type) {
		this.controller = controller;
		this.millis = millis;
		this.loop = loop;
		this.type = type;

		Thread timer = new Thread(this);
		timer.start();
	}

	public void run() {
		do {
			try {
				Thread.sleep(millis);

				controller.notifyTimeIsOver(type);

			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} while (loop);

	}
}
