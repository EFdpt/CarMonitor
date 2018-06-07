package it.uniroma1.fastcharge.carmonitor.config;

import java.util.Locale;

public class Preferences {
	
	private int DEFAULT_BAUDRATE = 9600;
	private double DEFAULT_CHART_REFRESH_TIME = 0.5;
	
	private Locale locale;
	private int baudRate;
	private double chartRefreshTime;
	
	public Preferences() {
		locale = Locale.getDefault();
		baudRate =  DEFAULT_BAUDRATE;
		chartRefreshTime = DEFAULT_CHART_REFRESH_TIME;
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
	
	public double getChartRefreshTime() {
		return chartRefreshTime;
	}
	
	public void setChartRefreshTime(double chartRefreshTime) {
		this.chartRefreshTime = chartRefreshTime;
	}
}
