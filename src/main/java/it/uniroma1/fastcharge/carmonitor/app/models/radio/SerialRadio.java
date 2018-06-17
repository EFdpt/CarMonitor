package it.uniroma1.fastcharge.carmonitor.app.models.radio;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fazecast.jSerialComm.SerialPort;

public class SerialRadio {
	
	private SerialPort serialPort = null;
	
	private SerialRadio() {}

	public static List<SerialRadio> getCommPorts() {
		return Arrays.asList(SerialPort.getCommPorts()).stream()
				.map(p -> {
					SerialRadio radio = new SerialRadio();
					radio.serialPort = p;
					p.addDataListener(new SerialRadioDataListener());
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

	public int getBaudRate() {
		if (serialPort == null)
			return -1;
		return serialPort.getBaudRate();
	}

	public void setBaudRate(int baudRate) {
		if (serialPort == null || serialPort.isOpen())
			return;
		serialPort.setBaudRate(baudRate);
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
