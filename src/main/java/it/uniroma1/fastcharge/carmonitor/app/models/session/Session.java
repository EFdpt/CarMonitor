package it.uniroma1.fastcharge.carmonitor.app.models.session;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import it.uniroma1.fastcharge.carmonitor.app.models.car.Car;
import it.uniroma1.fastcharge.carmonitor.app.models.radio.SerialRadio;

public class Session {
	private volatile Car car;
	private volatile SerialRadio radio;
	private volatile ObjectOutputStream	out;
	private volatile FileOutputStream	fileOut;
	
	// current session singleton (volatile for synchronized object reference)
	private volatile static Session defaultSession = new Session();
	
	private Session() {
	}
	
	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public SerialRadio getRadio() {
		return radio;
	}

	public void setRadio(SerialRadio radio) {
		this.radio = radio;
	}
	
	public ObjectOutputStream getOutputStream() {
		return this.out;
	}
	
	public void setOutputStream(ObjectOutputStream out) {
		this.out = out;
	}
	
	public FileOutputStream getFileOutputStream() {
		return this.fileOut;
	}
	
	public void setFileOutputStream(FileOutputStream fileOut) {
		this.fileOut = fileOut;
	}
	
	// return a new Session instance
	public static Session getInstance() {
		defaultSession = new Session();
		return defaultSession;
	}
	
	// return current Session instance
	public static Session getDefaultInstance() {
		return defaultSession;
	}
}
