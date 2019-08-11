package it.uniroma1.fastcharge.carmonitor.app.models.radio;

import java.io.IOException;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.google.gson.JsonParseException;

import it.uniroma1.fastcharge.carmonitor.app.models.car.Car;
import it.uniroma1.fastcharge.carmonitor.app.models.session.Session;

class SerialRadioDataListener implements SerialPortDataListener {
	
	private final int DEFAULT_TIMEOUT = 1000;
	private final String DEFAULT_BUFFER_SEPARATOR = "\r\n";
	private int timeout;
	StringBuilder sb;
	
	public SerialRadioDataListener() {
		timeout = DEFAULT_TIMEOUT;
		sb = new StringBuilder();
	}

	@Override
	public int getListeningEvents() {
		return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
	}

	@Override
	public void serialEvent(SerialPortEvent event) {
		if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
	         return;
		
		int recvDataLen = event.getSerialPort().bytesAvailable();
		byte[] recvBuffer = new byte[recvDataLen];
		String recvString;
		
		event.getSerialPort().readBytes(recvBuffer, recvDataLen);
		
		try {
			sb.append(new String(recvBuffer));
			recvString = sb.toString();
			
			if (recvString.contains(DEFAULT_BUFFER_SEPARATOR)) {
				Car car;
				
				car = CarRadioAdapter.getAdapter().deserialize(recvString.substring(0, recvString.lastIndexOf(DEFAULT_BUFFER_SEPARATOR)).getBytes());
				
				Session.getDefaultInstance().setCar(car);
				Session.getDefaultInstance().getOutputStream().writeObject(car);
				Session.getDefaultInstance().getOutputStream().reset();
				Session.getDefaultInstance().getOutputStream().flush();
				
				sb.setLength(0);
			}
		} catch (JsonParseException e) {
			sb.setLength(0);
			if ((timeout--) == 0) {
				timeout = 0;
				throw new RadioDeserializationException();
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			sb.setLength(0);
		}
	}
}
