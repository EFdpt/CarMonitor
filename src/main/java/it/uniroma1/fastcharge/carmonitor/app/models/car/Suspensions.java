package it.uniroma1.fastcharge.carmonitor.app.models.car;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class Suspensions implements Serializable {
	private static final long serialVersionUID = 6294604389693562541L;
	
	private volatile byte lfSuspension;
	private volatile byte rfSuspension;
	private volatile byte lrSuspension;
	private volatile byte rrSuspension;
	private final PropertyChangeSupport propertySupport;
	
	public Suspensions() {
		this.propertySupport = new PropertyChangeSupport(this);
	}
	
	public static String lfSuspensionPropertyName() {
		return "lfSuspension";
	}
	
	public static String rfSuspensionPropertyName() {
		return "rfSuspension";
	}
	
	public static String lrSuspensionPropertyName() {
		return "lrSuspension";
	}
	
	public static String rrSuspensionPropertyName() {
		return "rrSuspension";
	}

	public byte getLfSuspension() {
		return lfSuspension;
	}

	public void setLfSuspension(byte lfSuspension) {
		byte oldLfSuspension = this.lfSuspension;
		this.lfSuspension = lfSuspension;
		propertySupport.firePropertyChange("lfSuspension", oldLfSuspension, lfSuspension);
	}

	public byte getRfSuspension() {
		return rfSuspension;
	}

	public void setRfSuspension(byte rfSuspension) {
		byte oldRfSuspension = this.rfSuspension;
		this.rfSuspension = rfSuspension;
		propertySupport.firePropertyChange("rfSuspension", oldRfSuspension, rfSuspension);
	}

	public byte getLrSuspension() {
		return lrSuspension;
	}

	public void setLrSuspension(byte lrSuspension) {
		byte oldLrSuspension = this.lrSuspension;
		this.lrSuspension = lrSuspension;
		propertySupport.firePropertyChange("lrSuspension", oldLrSuspension, lrSuspension);
	}

	public byte getRrSuspension() {
		return rrSuspension;
	}

	public void setRrSuspension(byte rrSuspension) {
		byte oldRrSuspension = this.rrSuspension;
		this.rrSuspension = rrSuspension;
		propertySupport.firePropertyChange("rrSuspension", oldRrSuspension, rrSuspension);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }
}
