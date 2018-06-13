package it.uniroma1.fastcharge.carmonitor.app.models.activities.atomic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import it.uniroma1.fastcharge.carmonitor.app.models.activities.framework.Task;
import it.uniroma1.fastcharge.carmonitor.app.models.radio.SerialRadio;
import it.uniroma1.fastcharge.carmonitor.app.models.session.Session;
import it.uniroma1.fastcharge.carmonitor.config.ApplicationPreferences;

public class RadioConnectTask implements Task {
	
	private volatile boolean executed = false;
	
	public RadioConnectTask() {}

	@Override
	public void execute() {
		if (executed)
			return;
		executed = true;
		
		Session.getDefaultInstance().getRadio().setBaudRate(ApplicationPreferences.getConfiguration().getBaudRate());
		
		try {
			String fileName = ApplicationPreferences.getConfiguration().getLogDir() + new SimpleDateFormat("'/'yyyy-MM-dd HH_mm_SS'.ser'").format(new Date());
			System.out.println(fileName);
			File f = new File(fileName);
			f.getParentFile().mkdirs();
			f.createNewFile();
	    	FileOutputStream fileOut = new FileOutputStream(f, false);
	    	ObjectOutputStream out = new ObjectOutputStream(fileOut);
	    	Session.getDefaultInstance().setOutputStream(out);
	    	Session.getDefaultInstance().setFileOutputStream(fileOut);
	    } catch (IOException i) {
	    	i.printStackTrace();
	    }
		
		Session.getDefaultInstance().getRadio().open();
	}
}
