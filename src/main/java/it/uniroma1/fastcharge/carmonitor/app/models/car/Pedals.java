package it.uniroma1.fastcharge.carmonitor.app.models.car;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;

public class Pedals implements Serializable {
	private static final long serialVersionUID = -1610552689784668165L;
	
	private volatile byte tps1;
	private volatile byte tps1Min;
	private volatile byte tps1Max;
	
	private volatile byte tps2;
	private volatile byte tps2Min;
	private volatile byte tps2Max;
	
	private volatile byte brake;
	private volatile byte brakeMin;
	private volatile byte brakeMax;
	
	private volatile boolean appsPlausibility;
	private volatile boolean brakePlausibility;
	
	private final transient PropertyChangeSupport propertySupport;
	
	public Pedals() {
		this.propertySupport = new PropertyChangeSupport(this);
	}
	
	public static String tps1PropertyName() {
		return "tps1";
	}
	
	public static String tps2PropertyName() {
		return "tps2";
	}
	
	public static String brakePropertyName() {
		return "brake";
	}
	
	public static String tps1MaxPropertyName() {
		return "tps1Max";
	}
	
	public static String tps1MinPropertyName() {
		return "tps1Min";
	}
	
	public static String tps2MaxPropertyName() {
		return "tps2Max";
	}
	
	public static String tps2MinPropertyName() {
		return "tps2Min";
	}
	
	public static String brakeMaxPropertyName() {
		return "brakeMax";
	}
	
	public static String brakeMinPropertyName() {
		return "brakeMin";
	}
	
	public static String appsPlausibilityPropertyName() {
		return "appsPlausibility";
	}
	
	public static String brakePlausibilityPropertyName() {
		return "brakePlausibility";
	}

	public byte getTps1() {
		return tps1;
	}

	public void setTps1(byte tps1) {
		byte oldTps1 = this.tps1;
		this.tps1 = tps1;
		propertySupport.firePropertyChange("tps1", oldTps1, tps1);
		
		if (tps1 > tps1Max) {
			byte oldTps1Max = this.tps1Max;
			this.tps1Max = tps1;
			propertySupport.firePropertyChange("tps1Max", oldTps1Max, tps1);
		} else if (tps1 < tps1Min) {
			byte oldTps1Min = this.tps1Min;
			this.tps1Min = tps1;
			propertySupport.firePropertyChange("tps1Min", oldTps1Min, tps1);
		}
	}

	public byte getTps1Min() {
		return tps1Min;
	}

	public byte getTps1Max() {
		return tps1Max;
	}

	public byte getTps2() {
		return tps2;
	}

	public void setTps2(byte tps2) {
		byte oldTps2 = this.tps2;
		this.tps2 = tps2;
		propertySupport.firePropertyChange("tps2", oldTps2, tps2);
		
		if (tps2 > tps2Max) {
			byte oldTps2Max = this.tps2Max;
			this.tps2Max = tps2;
			propertySupport.firePropertyChange("tps2Max", oldTps2Max, tps2);
		} else if (tps2 < tps2Min) {
			byte oldTps2Min = this.tps2Min;
			this.tps2Min = tps2;
			propertySupport.firePropertyChange("tps2Min", oldTps2Min, tps2);
		}
	}

	public byte getTps2Min() {
		return tps2Min;
	}

	public byte getTps2Max() {
		return tps2Max;
	}

	public byte getBrake() {
		return brake;
	}

	public void setBrake(byte brake) {
		byte oldBrake = this.brake;
		this.brake = brake;
		propertySupport.firePropertyChange("brake", oldBrake, brake);
		
		if (brake > brakeMax) {
			byte oldBrakeMax = this.brakeMax;
			this.brakeMax = brake;
			propertySupport.firePropertyChange("brakeMax", oldBrakeMax, brake);
		} else if (tps2 < brakeMin) {
			byte oldBrakeMin = this.brakeMin;
			this.brakeMin = brake;
			propertySupport.firePropertyChange("brakeMin", oldBrakeMin, brake);
		}
	}

	public byte getBrakeMin() {
		return brakeMin;
	}

	public byte getBrakeMax() {
		return brakeMax;
	}

	public boolean getAppsPlausibility() {
		return appsPlausibility;
	}

	public void setAppsPlausibility(boolean appsPlausibility) {
		boolean oldAppsPlausibility = this.appsPlausibility;
		this.appsPlausibility = appsPlausibility;
		propertySupport.firePropertyChange("appsPlausibility", oldAppsPlausibility, appsPlausibility);
	}

	public boolean getBrakePlausibility() {
		return brakePlausibility;
	}

	public void setBrakePlausibility(boolean brakePlausibility) {
		boolean oldBrakePlausibility = this.brakePlausibility;
		this.brakePlausibility = brakePlausibility;
		propertySupport.firePropertyChange("brakePlausibility", oldBrakePlausibility, brakePlausibility);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		
	}
	
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		
	}
	
	private void readObjectNoData() throws ObjectStreamException {
		throw new InvalidObjectException("Stream data required");
	}

}
