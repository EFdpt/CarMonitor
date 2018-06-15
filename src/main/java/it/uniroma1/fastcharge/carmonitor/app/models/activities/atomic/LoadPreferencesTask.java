package it.uniroma1.fastcharge.carmonitor.app.models.activities.atomic;

import java.io.IOException;

import it.uniroma1.fastcharge.carmonitor.app.models.activities.framework.Task;
import it.uniroma1.fastcharge.carmonitor.app.views.i18n.I18N;
import it.uniroma1.fastcharge.carmonitor.config.ApplicationPreferences;

public class LoadPreferencesTask implements Task {
	
	private volatile boolean executed;
	
	public LoadPreferencesTask() {
		this.executed = executed;
	}

	@Override
	public void execute() {
		if (executed)
			return;
		executed = true;
		
		try {
			ApplicationPreferences.loadPreferences();
			I18N.setLocale(ApplicationPreferences.getConfiguration().getLocale());
		} catch (IOException e) {
		}
	}
	
	public boolean isExecuted() {
		return executed;
	}

}
