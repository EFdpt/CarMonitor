package it.uniroma1.fastcharge.carmonitor.app.models.car;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Suspensions {
	
	private volatile byte lfSuspension;
	private volatile byte rfSuspension;
	private volatile byte lrSuspension;
	private volatile byte rrSuspension;
	private final PropertyChangeSupport propertySupport;
	
	public Suspensions() {
		this.propertySupport = new PropertyChangeSupport(this);
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
