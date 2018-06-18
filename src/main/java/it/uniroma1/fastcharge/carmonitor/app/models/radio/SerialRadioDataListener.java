package it.uniroma1.fastcharge.carmonitor.app.models.radio;

import java.io.IOException;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.google.gson.JsonParseException;

import it.uniroma1.fastcharge.carmonitor.app.models.car.Car;
import it.uniroma1.fastcharge.carmonitor.app.models.session.Session;

class SerialRadioDataListener implements SerialPortDataListener {
	
	private String recvString = "";
	
	public SerialRadioDataListener() {
		
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
		
		event.getSerialPort().readBytes(recvBuffer, recvDataLen);
		String recv;
		
		try {
			recv = new String(recvBuffer);
			recvString += recv;
			if (recvString.contains("\r\n")) {
				Car car;
				
				car = CarRadioAdapter.getAdapter().deserialize(recvString.substring(0, recvString.lastIndexOf("\r\n")).getBytes());
				Session.getDefaultInstance().getOutputStream().writeObject(car);
				Session.getDefaultInstance().getOutputStream().reset();
				Session.getDefaultInstance().getOutputStream().flush();
				
				recvString = "";
			}
		} catch (JsonParseException e) { 
			recvString = "";
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			recvString = "";
		}
	}
}
