package tr2.server.sync.controller;

import java.io.IOException;

public class Timer implements Runnable {
	
	private long millis;
	
	private TimerController controller;
	
	private int type;
	
	public Timer(TimerController controller, long millis, int type) {
		this.type = type;
		this.controller = controller;
		this.millis = millis;
		
		Thread timer = new Thread(this);
		timer.start();
	}
	
	public void run() {
		try {
			Thread.sleep(millis);
			
			controller.notifyTimeIsOver(type);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
