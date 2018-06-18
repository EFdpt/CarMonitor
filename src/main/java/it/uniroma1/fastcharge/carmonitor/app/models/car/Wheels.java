package it.uniroma1.fastcharge.carmonitor.app.models.car;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class Wheels implements Serializable {
	private static final long serialVersionUID = -37627236686782662L;
	
	private volatile short lfWheelRpm;
	private volatile short rfWheelRpm;
	private volatile short lrWheelRpm;
	private volatile short rrWheelRpm;
	private final transient PropertyChangeSupport propertySupport;
	
	public Wheels() {
		this.propertySupport = new PropertyChangeSupport(this);
	}
	
	public static String lfWheelPropertyName() {
		return "lfWheelRpm";
	}
	
	public static String rfWheelPropertyName() {
		return "rfWheelRpm";
	}
	
	public static String lrWheelPropertyName() {
		return "lrWheelRpm";
	}
	
	public static String rrWheelPropertyName() {
		return "rrWheelRpm";
	}
	
	public short getLfWheelRpm() {
		return lfWheelRpm;
	}
	
	public void setLfWheelRpm(short lfWheelRpm) {
		short oldLfWheelRpm = this.lfWheelRpm;
		this.lfWheelRpm = lfWheelRpm;
		propertySupport.firePropertyChange("lfWheelRpm", oldLfWheelRpm, lfWheelRpm);
	}

	public short getRfWheelRpm() {
		return rfWheelRpm;
	}

	public void setRfWheelRpm(short rfWheelRpm) {
		short oldRfWheelRpm = this.rfWheelRpm;
		this.rfWheelRpm = rfWheelRpm;
		propertySupport.firePropertyChange("rfWheelRpm", oldRfWheelRpm, rfWheelRpm);
	}

	public short getLrWheelRpm() {
		return lrWheelRpm;
	}

	public void setLrWheelRpm(short lrWheelRpm) {
		short oldLrWheelRpm = this.lrWheelRpm;
		this.lrWheelRpm = lrWheelRpm;
		propertySupport.firePropertyChange("lrWheelRpm", oldLrWheelRpm, lrWheelRpm);
	}

	public short getRrWheelRpm() {
		return rrWheelRpm;
	}

	public void setRrWheelRpm(short rrWheelRpm) {
		short oldRrWheelRpm = this.rrWheelRpm;
		this.rrWheelRpm = rrWheelRpm;
		propertySupport.firePropertyChange("rrWheelRpm", oldRrWheelRpm, rrWheelRpm);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }
}
