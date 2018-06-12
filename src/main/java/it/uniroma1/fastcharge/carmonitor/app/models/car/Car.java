package it.uniroma1.fastcharge.carmonitor.app.models.car;

import java.io.Serializable;

public class Car implements Serializable {
	
	private static final long serialVersionUID = 1816168786704121837L;
	
	private Pedals 			pedals;
	private Wheels 			wheels;
	private Accelerometers 	accelerometers;
	private Suspensions 	suspensions;
	
	public Car() { 
		this.pedals = new Pedals();
		this.wheels = new Wheels();
		this.accelerometers = new Accelerometers();
		this.suspensions = new Suspensions();
	}
	
	public Pedals getPedals() {
		return this.pedals;
	}
	
	public Wheels getWheels() {
		return this.wheels;
	}
	
	public Accelerometers getAccelerometers() {
		return this.accelerometers;
	}
	
	public Suspensions getSuspensions() {
		return this.suspensions;
	}
}
