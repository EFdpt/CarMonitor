package it.uniroma1.fastcharge.carmonitor.config;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CarPreferences {
	private final int DEFAULT_RPM_MAX_VALUE = 2500;
	private final int DEFAULT_ACC_MAX_VALUE = 5;
	private final int DEFAULT_SUSP_MAX_VALUE = 75;
	
	private IntegerProperty rpmMaxValueProperty;
	private IntegerProperty accelerometersMaxValueProperty;
	private IntegerProperty suspensionsMaxValueProperty;
	
	public CarPreferences() {
		rpmMaxValueProperty = new SimpleIntegerProperty(DEFAULT_RPM_MAX_VALUE);
		accelerometersMaxValueProperty = new SimpleIntegerProperty(DEFAULT_ACC_MAX_VALUE);
		suspensionsMaxValueProperty = new SimpleIntegerProperty(DEFAULT_SUSP_MAX_VALUE);
	}
	
	public int getDefaultRpmMaxValue() {
		return DEFAULT_RPM_MAX_VALUE;
	}
	
	public int getDefaultAccelerometersMaxValue() {
		return DEFAULT_ACC_MAX_VALUE;
	}
	
	public int getDefaultSuspensionsMaxValue() {
		return DEFAULT_SUSP_MAX_VALUE;
	}

	public IntegerProperty getRpmMaxValueProperty() {
		return rpmMaxValueProperty;
	}

	public void setRpmMaxValueProperty(IntegerProperty rpmMaxValueProperty) {
		this.rpmMaxValueProperty = rpmMaxValueProperty;
	}

	public IntegerProperty getAccelerometersMaxValueProperty() {
		return accelerometersMaxValueProperty;
	}

	public void setAccelerometersMaxValueProperty(IntegerProperty accelerometersMaxValueProperty) {
		this.accelerometersMaxValueProperty = accelerometersMaxValueProperty;
	}

	public IntegerProperty getSuspensionsMaxValueProperty() {
		return suspensionsMaxValueProperty;
	}

	public void setSuspensionsMaxValueProperty(IntegerProperty suspensionsMaxValueProperty) {
		this.suspensionsMaxValueProperty = suspensionsMaxValueProperty;
	}
}
