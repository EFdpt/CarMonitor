package it.uniroma1.fastcharge.carmonitor.app.models.radio;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventListener;
import java.util.List;
import java.util.stream.Collectors;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;

public class SerialRadio implements ISerialRadio {
	
	private SerialPort serialPort = null;
	
	private SerialRadio() {}

	@Override
	public List<ISerialRadio> getCommPorts() {
		return Arrays.asList(SerialPort.getCommPorts()).stream()
				.map(p -> {
					SerialRadio radio = new SerialRadio();
					radio.serialPort = p;
					return radio;
				})
				.collect(Collectors.toCollection(ArrayList::new)); 
	}
	
	@Override
	public String getPortDescription() {
		if (serialPort == null)
			return null;
		return this.serialPort.getPortDescription();
	}
	

	@Override
	public String getSystemPortName() {
		if (serialPort == null)
			return null;
		return serialPort.getSystemPortName();
	}
	
	@Override
	public boolean setPort(String portDescriptor) {
		if (serialPort == null)
			return false;
		SerialPort port = SerialPort.getCommPort(portDescriptor);
		if (port.getPortDescription().equals("Bad Port"))
			return false;
		serialPort = port;
		return true;
	}

	@Override
	public int getBaudRate() {
		if (serialPort == null)
			return -1;
		return serialPort.getBaudRate();
	}

	@Override
	public void setBaudRate(int baudRate) {
		if (serialPort == null)
			return;
	}

	@Override
	public boolean isOpen() {
		if (serialPort == null)
			return false;
		return serialPort.isOpen();
	}

	@Override
	public boolean open() {
		if (serialPort == null)
			return false;
		return serialPort.openPort();
	}

	@Override
	public boolean close() {
		if (serialPort == null)
			return false;
		return serialPort.closePort();
	}

	@Override
	public boolean addDataListener(EventListener listener) {
		if (serialPort == null)
			return false;
		return serialPort.addDataListener((SerialPortDataListener) listener);
	}

	@Override
	public InputStream getInputStream() {
		if (serialPort == null)
			return null;
		return serialPort.getInputStream();
	}

	@Override
	public int bytesAvailable() {
		if (serialPort == null)
			return -1;
		return serialPort.bytesAvailable();
	}

	@Override
	public int readBytes(byte[] buffer, long bytesToRead) {
		if (serialPort == null)
			return -1;
		return serialPort.readBytes(buffer, bytesToRead);
	}
}
