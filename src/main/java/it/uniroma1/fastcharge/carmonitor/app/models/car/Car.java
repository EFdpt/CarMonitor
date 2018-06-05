package it.uniroma1.fastcharge.carmonitor.app.models.car;

public class Car {
	
	private Pedals pedals;
	private Wheels wheels;
	
	public Car() { 
		this.pedals = new Pedals();
		this.wheels = new Wheels();
	}
	
	public Pedals getPedals() {
		return this.pedals;
	}
	
	public Wheels getWheels() {
		return this.wheels;
	}

}
