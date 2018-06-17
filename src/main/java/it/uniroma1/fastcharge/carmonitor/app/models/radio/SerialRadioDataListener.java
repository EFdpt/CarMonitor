package it.uniroma1.fastcharge.carmonitor.app.models.radio;

import java.io.IOException;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

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
			recv = new String(recvBuffer, "ASCII");
			recvString += recv;
			if (recvString.contains("\r\n")) {
				Car car;
				car = CarRadioAdapter.getAdapter().deserialize(recvString.getBytes());
				Session.getDefaultInstance().getOutputStream().writeObject(car);
				recvString = "";
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
}
