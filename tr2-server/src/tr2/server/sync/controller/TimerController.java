package tr2.server.sync.controller;

import java.io.IOException;

public interface TimerController {
	public void notifyTimeIsOver(int type) throws IOException;
}
