package it.uniroma1.fastcharge.carmonitor.app.models.car;

public class Car {
	
	private Pedals pedals;
	private Wheels wheels;
	
	// singleton instance
	private final static Car car = new Car();
	
	private Car() { 
		this.pedals = new Pedals();
		this.wheels = new Wheels();
	}
	
	public static Car getInstance() {
		return car;
	}
	
	public Pedals getPedals() {
		return this.pedals;
	}
	
	public Wheels getWheels() {
		return this.wheels;
	}

}
