package it.uniroma1.fastcharge.carmonitor.app.models.activities.atomic;

import java.io.IOException;

import it.uniroma1.fastcharge.carmonitor.app.models.activities.framework.Task;
import it.uniroma1.fastcharge.carmonitor.app.models.session.Session;

public class RadioDisconnectTask implements Task {

	private volatile boolean executed = false;
	private volatile boolean isDisconnected = false;
	
	public RadioDisconnectTask() {}
	
	@Override
	public void execute() {
		if (executed)
			return;
		
		if (Session.getDefaultInstance().getRadio() == null || !Session.getDefaultInstance().getRadio().isOpen())
			return;
		
		isDisconnected = Session.getDefaultInstance().getRadio().close();
		try {
			Session.getDefaultInstance().getOutputStream().close();
			Session.getDefaultInstance().getFileOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean isExecuted() {
		return executed;
	}
	
	public boolean isDisconnected() {
		return isDisconnected;
	}
}
