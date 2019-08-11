package it.uniroma1.fastcharge.carmonitor.app.models.activities.atomic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import it.uniroma1.fastcharge.carmonitor.app.models.activities.framework.Task;
import it.uniroma1.fastcharge.carmonitor.app.models.session.Session;
import it.uniroma1.fastcharge.carmonitor.config.ApplicationPreferences;

public class RadioConnectTask implements Task {
	
	private volatile boolean executed = false;
	private volatile boolean connected = false;
	
	public RadioConnectTask() {}

	@Override
	public void execute() {
		if (executed)
			return;
		executed = true;
		
		if (Session.getDefaultInstance().getRadio() == null || Session.getDefaultInstance().getRadio().isOpen())
			return;
 
		Session.getDefaultInstance().getRadio().setBaudRate(ApplicationPreferences.getConfiguration().getBaudRate());
		
		try {
			String fileName = ApplicationPreferences.getConfiguration().getLogDir() + "/" + new SimpleDateFormat("yyyy-MM-dd HH_mm_SS").format(new Date()) + ".ser";
			File f = new File(fileName);
			f.getParentFile().mkdirs();
			f.createNewFile();
	    	FileOutputStream fileOut = new FileOutputStream(f, true);
	    	ObjectOutputStream out = new ObjectOutputStream(fileOut);
	    	Session.getDefaultInstance().setOutputStream(out);
	    	Session.getDefaultInstance().setFileOutputStream(fileOut);
	    } catch (IOException i) {
	    	throw new RuntimeException(i);
	    }
		
		connected = Session.getDefaultInstance().getRadio().open();
	}
	
	@Override
	public boolean isExecuted() {
		return executed;
	}
	
	public boolean isConnected() {
		return connected;
	}
}
