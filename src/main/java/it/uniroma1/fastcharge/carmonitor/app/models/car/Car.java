package it.uniroma1.fastcharge.carmonitor.app.models.car;

public class Car {
	
	private Pedals 			pedals;
	private Wheels 			wheels;
	private Accelerometers 	accelerometers;
	private Suspensions 	suspensions;
	
	private Car() { 
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
