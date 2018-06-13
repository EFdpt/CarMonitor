package it.uniroma1.fastcharge.carmonitor.app.models.activities;

import java.io.IOException;

import it.uniroma1.fastcharge.carmonitor.app.views.i18n.I18N;
import it.uniroma1.fastcharge.carmonitor.config.ApplicationPreferences;

public class MainActivity implements Runnable {
	
	private volatile boolean executed = false;
	
	public MainActivity() {}

	@Override
	public void run() {
		if (executed)
			return;
		executed = true;
		
		try {
			ApplicationPreferences.loadPreferences("options.json");
		} catch (IOException e) {
			// configurazione precedente non presente
			e.printStackTrace();
		}
		
		I18N.setLocale(ApplicationPreferences.getConfiguration().getLocale());
	}
	
	public boolean isExecuted() {
		return executed;
	}

}
