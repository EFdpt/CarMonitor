package it.uniroma1.fastcharge.carmonitor.app.models.activities.atomic;

import it.uniroma1.fastcharge.carmonitor.app.models.activities.framework.Task;
import it.uniroma1.fastcharge.carmonitor.app.models.radio.ISerialRadio;
import it.uniroma1.fastcharge.carmonitor.app.models.session.Session;
import it.uniroma1.fastcharge.carmonitor.config.ApplicationPreferences;

public class RadioConnect implements Task {
	
	private Session currentSession;
	private volatile boolean executed = false;
	
	public RadioConnect(ISerialRadio radio) {
		this.currentSession = Session.getInstance();
		this.currentSession.setRadio(radio);
	}

	@Override
	public void execute() {
		if (executed)
			return;
		executed = true;
		
		currentSession.getRadio().setBaudRate(ApplicationPreferences.getConfiguration().getBaudRate());
		currentSession.getRadio().open();		
	}
	
	public synchronized Session getCurrentSession() {
		return currentSession;
	}
}
