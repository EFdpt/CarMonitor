package it.uniroma1.fastcharge.carmonitor.app.models.session;

import it.uniroma1.fastcharge.carmonitor.app.models.car.Car;
import it.uniroma1.fastcharge.carmonitor.app.models.radio.SerialRadio;

public class Session {
	private Car car;
	private SerialRadio radio;
	
	// current session singleton (volatile for synchronized object reference)
	private volatile static Session defaultSession = new Session();
	
	private Session() {}
	
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
