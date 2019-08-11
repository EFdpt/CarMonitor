package it.uniroma1.fastcharge.carmonitor.app.models.activities.atomic;

import it.uniroma1.fastcharge.carmonitor.app.models.activities.framework.Task;
import it.uniroma1.fastcharge.carmonitor.app.models.radio.SerialRadio;
import it.uniroma1.fastcharge.carmonitor.app.models.session.Session;

public class SetRadioTask implements Task {
	
	private volatile boolean 		executed = false;
	private volatile SerialRadio	radio;
	
	public SetRadioTask(SerialRadio radio) {
		this.radio = radio;
	}

	@Override
	public void execute() {
		if (executed)
			return;
		executed = true;
		
		SerialRadio sessRadio = Session.getDefaultInstance().getRadio();
		
		if (sessRadio != null && sessRadio.isOpen()) // current radio opened
			return;
		else if (sessRadio == null) { // no radio already setted
			Session.getDefaultInstance().setRadio(radio);
		} else if (!sessRadio.isOpen()) // radio already setted but closed -> create new session and set radio
			Session.getInstance().setRadio(this.radio);
	}

	@Override
	public boolean isExecuted() {
		return executed;
	}

}
