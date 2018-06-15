package it.uniroma1.fastcharge.carmonitor.app.models.radio;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import it.uniroma1.fastcharge.carmonitor.app.models.car.Car;
import it.uniroma1.fastcharge.carmonitor.app.models.session.Session;

public class SerialRadioDataListener implements SerialPortDataListener {

	@Override
	public int getListeningEvents() {
		return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
	}

	@Override
	public void serialEvent(SerialPortEvent event) {
		if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
	         return;
		byte[] recvBuffer = new byte[event.getSerialPort().bytesAvailable()];
		event.getSerialPort().readBytes(recvBuffer, recvBuffer.length);
		
		System.out.println(new String(recvBuffer));
		
		
		// deserialize recvBuffer
		try {
			Car car = CarRadioAdapter.getAdapter().deserialize(new ObjectInputStream(new ByteArrayInputStream(recvBuffer)));
			Session.getDefaultInstance().getOutputStream().writeObject(car);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
}
