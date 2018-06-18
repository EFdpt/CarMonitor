package it.uniroma1.fastcharge.carmonitor.app.models.car;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class Accelerometers implements Serializable {
	private static final long serialVersionUID = -4794710467001730614L;
	
	private volatile byte accelerometerX;
	private volatile byte accelerometerY;
	private volatile byte accelerometerZ;
	private final transient PropertyChangeSupport propertySupport ;
	
	public Accelerometers() {
		this.propertySupport = new PropertyChangeSupport(this);
	}
	
	public static String accelerometerXPropertyName() {
		return "accelerometerX";
	}
	
	public static String accelerometerYPropertyName() {
		return "accelerometerY";
	}
	
	public static String accelerometerZPropertyName() {
		return "accelerometerZ";
	}
	
	public byte getAccelerometerX() {
		return accelerometerX;
	}

	public void setAccelerometerX(byte accelerometerX) {
		byte oldAccX = this.accelerometerX;
		this.accelerometerX = accelerometerX;
		propertySupport.firePropertyChange("accelerometerX", oldAccX, accelerometerX);
	}

	public byte getAccelerometerY() {
		return accelerometerY;
	}

	public void setAccelerometerY(byte accelerometerY) {
		byte oldAccY = this.accelerometerY;
		this.accelerometerY = accelerometerY;
		propertySupport.firePropertyChange("accelerometerY", oldAccY, accelerometerY);
	}

	public byte getAccelerometerZ() {
		return accelerometerZ;
	}

	public void setAccelerometerZ(byte accelerometerZ) {
		byte oldAccZ = this.accelerometerZ;
		this.accelerometerZ = accelerometerZ;
		propertySupport.firePropertyChange("accelerometerZ", oldAccZ, accelerometerZ);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }
}
