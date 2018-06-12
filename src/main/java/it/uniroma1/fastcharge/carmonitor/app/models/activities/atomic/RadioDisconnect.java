package it.uniroma1.fastcharge.carmonitor.app.models.activities.atomic;

import java.io.IOException;

import it.uniroma1.fastcharge.carmonitor.app.models.activities.framework.Task;
import it.uniroma1.fastcharge.carmonitor.app.models.session.Session;

public class RadioDisconnect implements Task {

	private volatile boolean executed = false;
	private volatile Session currentSession;
	
	public RadioDisconnect() {
		currentSession = Session.getDefaultInstance();
	}
	
	@Override
	public void execute() {
		if (executed)
			return;
		
		try {
			currentSession.getOutputStream().close();
			currentSession.getFileOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized Session getCurrentSession() {
		return currentSession;
	}
}
