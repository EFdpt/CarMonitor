package it.uniroma1.fastcharge.carmonitor.app.models.car;

public class Wheels {
	private volatile short lf_wheel_rpm;
	private volatile short rf_wheel_rpm;
	private volatile short lr_wheel_rpm;
	private volatile short rr_wheel_rpm;
	
	public Wheels() {}
	
	public short getLfWheelRpm() {
		return lf_wheel_rpm;
	}
	
	public void setLfWheelRpm(short lfWheelRpm) {
		this.lf_wheel_rpm = lfWheelRpm;
	}

	public short getRfWheelRpm() {
		return rf_wheel_rpm;
	}

	public void setRfWheelRpm(short rfWheelRpm) {
		this.rf_wheel_rpm = rfWheelRpm;
	}

	public short getLrWheelRpm() {
		return lr_wheel_rpm;
	}

	public void setLrWheelRpm(short lrWheelRpm) {
		this.lr_wheel_rpm = lrWheelRpm;
	}

	public short getRrWheelRpm() {
		return rr_wheel_rpm;
	}

	public void setRrWheelRpm(short rrWheelRpm) {
		this.rr_wheel_rpm = rrWheelRpm;
	}
}
