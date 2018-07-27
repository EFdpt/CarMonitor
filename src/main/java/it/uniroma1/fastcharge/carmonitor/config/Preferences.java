package it.uniroma1.fastcharge.carmonitor.config;

import java.util.Locale;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Preferences {
	
	private int DEFAULT_BAUDRATE = 115200;
	private double DEFAULT_CHART_REFRESH_TIME = 0.5;
	private double DEFAULT_VIEW_REFRESH_TIME = 2.0;
	private String DEFAULT_LOG_DIR = "logs";
	
	private Locale locale;
	private int baudRate;
	private double chartRefreshTime;
	private String logDir;
	
	private DoubleProperty chartRefreshTimeProperty;
	private DoubleProperty viewRefreshTimeProperty;
	
	private CarPreferences carPreferences;
	
	public Preferences() {
		locale = Locale.getDefault();
		baudRate =  DEFAULT_BAUDRATE;
		chartRefreshTime = DEFAULT_CHART_REFRESH_TIME;
		logDir = DEFAULT_LOG_DIR;
		
		chartRefreshTimeProperty = new SimpleDoubleProperty(DEFAULT_CHART_REFRESH_TIME);
		viewRefreshTimeProperty = new SimpleDoubleProperty(DEFAULT_VIEW_REFRESH_TIME);
		carPreferences = new CarPreferences();
	}
	
	public CarPreferences getCarPreferences() {
		return carPreferences;
	}
	
	public void setCarPreferences(CarPreferences carPreferences) {
		this.carPreferences = carPreferences;
	}
	
	public Locale getLocale() {
		return locale;
	}
	
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	public int getBaudRate() {
		return baudRate;
	}
	
	public int getDefaultBaudRate() {
		return DEFAULT_BAUDRATE;
	}
	
	public void setBaudRate(int baudRate) {
		this.baudRate = baudRate;
	}
	
	public double getViewRefreshTime() {
		return viewRefreshTimeProperty.get();
	}
	
	public DoubleProperty viewRefreshTimeProperty() {
		return viewRefreshTimeProperty;
	}
	
	public void setViewRefreshTime(double viewRefreshTime) {
		viewRefreshTimeProperty.set(viewRefreshTime);
	}
	
	public double getChartRefreshTime() {
		return chartRefreshTime;
	}
	
	public DoubleProperty chartRefreshTimeProperty() {
		return chartRefreshTimeProperty;
	}
	
	public void setChartRefreshTime(double chartRefreshTime) {
		this.chartRefreshTime = chartRefreshTime;
		chartRefreshTimeProperty.set(chartRefreshTime);
	}
	
	public String getLogDir() {
		return this.logDir;
	}
	
	public void setLogDir(String dir) {
		this.logDir = dir;
	}
}
