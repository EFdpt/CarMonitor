package it.uniroma1.fastcharge.carmonitor.app.models.activities.atomic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import it.uniroma1.fastcharge.carmonitor.app.models.activities.framework.Task;
import it.uniroma1.fastcharge.carmonitor.app.models.car.Car;

public class ExportCsvTask implements Task {
	
	private volatile boolean executed = false;
	
	private FileInputStream fileIn;
	private ObjectInputStream inputStream;
	
	private FileWriter		fileWriter;
	private BufferedWriter 	writer;
	private CSVPrinter		printer;
	
	public ExportCsvTask(File logFile, File csvFile) {
		try {
			this.fileWriter = new FileWriter(csvFile);
			this.writer = new BufferedWriter(fileWriter);
			printer = new CSVPrinter(writer, CSVFormat.DEFAULT.withDelimiter(';')
			        .withHeader("TPS1", "TPS2", "BRAKE", "APPS_PLAUSIBILITY", "BRAKE_PLAUSIBILITY",
			        			"LF_WHEEL", "RF_WHEEL", "LR_WHEEL", "RR_WHEEL",
			        			"LF_SUSP", "RF_SUSP", "LR_SUSP", "RR_SUSP",
			        			"ACC_X", "ACC_Z"));
			this.fileIn = new FileInputStream(logFile);
			inputStream = new ObjectInputStream(fileIn);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void execute() {
		if (executed)
			return;
		executed = true;
		
		try {
			Car car;
			while ((car = (Car) inputStream.readObject()) != null) {
				System.out.println(car.getPedals().getTps1());
				printer.printRecord(
					car.getPedals().getTps1(), car.getPedals().getTps2(), car.getPedals().getBrake(),
					car.getPedals().getAppsPlausibility(), car.getPedals().getBrakePlausibility(),
					car.getWheels().getLfWheelRpm(), car.getWheels().getRfWheelRpm(), car.getWheels().getLrWheelRpm(), car.getWheels().getRrWheelRpm(),
					car.getSuspensions().getLfSuspension(), car.getSuspensions().getRfSuspension(), car.getSuspensions().getLrSuspension(), car.getSuspensions().getRrSuspension(),
					car.getAccelerometers().getAccelerometerX(), car.getAccelerometers().getAccelerometerZ()
				);
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		} finally {
			// close all
			try {
				inputStream.close();
				printer.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}

	@Override
	public boolean isExecuted() {
		return executed;
	}

}
