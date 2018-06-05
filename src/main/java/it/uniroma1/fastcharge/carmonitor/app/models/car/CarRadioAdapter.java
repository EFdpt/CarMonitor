package it.uniroma1.fastcharge.carmonitor.app.models.car;

import java.io.IOException;
import java.io.ObjectInputStream;

public class CarRadioAdapter implements ICarDeserializer {
	
	private final static CarRadioAdapter adapter = new CarRadioAdapter();

	public static CarRadioAdapter getAdapter() {
		return adapter;
	}
	
	@Override
	public Car deserialize(ObjectInputStream in) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
