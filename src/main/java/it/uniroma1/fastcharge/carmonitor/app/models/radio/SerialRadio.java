package it.uniroma1.fastcharge.carmonitor.app.models.radio;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventListener;
import java.util.List;
import java.util.stream.Collectors;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;

public class SerialRadio {
	
	private SerialPort serialPort = null;
	
	private SerialRadio() {}

	public static List<SerialRadio> getCommPorts() {
		return Arrays.asList(SerialPort.getCommPorts()).stream()
				.map(p -> {
					SerialRadio radio = new SerialRadio();
					radio.serialPort = p;
					return radio;
				})
				.collect(Collectors.toCollection(ArrayList::new)); 
	}
	
	public String getPortDescription() {
		if (serialPort == null)
			return null;
		return this.serialPort.getPortDescription();
	}
	

	public String getSystemPortName() {
		if (serialPort == null)
			return null;
		return serialPort.getSystemPortName();
	}
	
	public boolean setPort(String portDescriptor) {
		if (serialPort == null)
			return false;
		SerialPort port = SerialPort.getCommPort(portDescriptor);
		if (port.getPortDescription().equals("Bad Port"))
			return false;
		serialPort = port;
		return true;
	}

	public int getBaudRate() {
		if (serialPort == null)
			return -1;
		return serialPort.getBaudRate();
	}

	public void setBaudRate(int baudRate) {
		if (serialPort == null)
			return;
	}

	public boolean isOpen() {
		if (serialPort == null)
			return false;
		return serialPort.isOpen();
	}

	public boolean open() {
		if (serialPort == null)
			return false;
		return serialPort.openPort();
	}

	public boolean close() {
		if (serialPort == null)
			return false;
		return serialPort.closePort();
	}

	public boolean addDataListener(EventListener listener) {
		if (serialPort == null)
			return false;
		return serialPort.addDataListener((SerialPortDataListener) listener);
	}

	public InputStream getInputStream() {
		if (serialPort == null)
			return null;
		return serialPort.getInputStream();
	}

	public int bytesAvailable() {
		if (serialPort == null)
			return -1;
		return serialPort.bytesAvailable();
	}

	public int readBytes(byte[] buffer, long bytesToRead) {
		if (serialPort == null)
			return -1;
		return serialPort.readBytes(buffer, bytesToRead);
	}
}
