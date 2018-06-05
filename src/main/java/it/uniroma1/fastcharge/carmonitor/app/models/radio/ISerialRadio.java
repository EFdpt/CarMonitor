package it.uniroma1.fastcharge.carmonitor.app.models.radio;

import java.io.InputStream;
import java.util.EventListener;
import java.util.List;

public interface ISerialRadio {
	public List<ISerialRadio> getCommPorts();
	public String getPortDescription();
	public String getSystemPortName();
	public boolean setPort(String portDescriptor);
	public int getBaudRate();
	public void setBaudRate(int baudRate);
	public boolean isOpen();
	public boolean open();
	public boolean close();
	public boolean addDataListener(EventListener listener);
	public InputStream getInputStream();
	public int bytesAvailable();
	public int readBytes(byte[] buffer, long bytesToRead);
}
