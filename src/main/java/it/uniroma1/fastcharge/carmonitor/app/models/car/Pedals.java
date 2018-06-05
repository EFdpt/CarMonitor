package it.uniroma1.fastcharge.carmonitor.app.models.car;

public class Pedals {
	private volatile byte tps1;
	private volatile byte tps1_min;
	private volatile byte tps1_max;
	
	private volatile byte tps2;
	private volatile byte tps2_min;
	private volatile byte tps2_max;
	
	private volatile byte brake;
	private volatile byte brake_min;
	private volatile byte brake_max;
	
	public Pedals() {}

	public byte getTps1() {
		return tps1;
	}

	public void setTps1(byte tps1) {
		this.tps1 = tps1;
		
		if (tps1 > tps1_max)
			tps1_max = tps1;
		else if (tps1 < tps1_min)
			tps1_min = tps1;
	}

	public byte getTps1Min() {
		return tps1_min;
	}

	public byte getTps1Max() {
		return tps1_max;
	}

	public byte getTps2() {
		return tps2;
	}

	public void setTps2(byte tps2) {
		this.tps2 = tps2;
		
		if (tps2 > tps2_max)
			tps2_max = tps2;
		else if (tps2 < tps2_min)
			tps2_min = tps2;
	}

	public byte getTps2Min() {
		return tps2_min;
	}

	public byte getTps2Max() {
		return tps2_max;
	}

	public byte getBrake() {
		return brake;
	}

	public void setBrake(byte brake) {
		this.brake = brake;
		
		if (brake > brake_max)
			brake_max = brake;
		else if (brake < brake_min)
			brake_min = brake;
	}

	public byte getBrakeMin() {
		return brake_min;
	}

	public byte getBrakeMax() {
		return brake_max;
	}
}
