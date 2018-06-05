package it.uniroma1.fastcharge.carmonitor.app.models.radio;

import java.io.IOException;
import java.io.ObjectInputStream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.uniroma1.fastcharge.carmonitor.app.models.car.Car;
import it.uniroma1.fastcharge.carmonitor.app.models.car.ICarDeserializer;

public class CarRadioAdapter implements ICarDeserializer {
	
	private final static CarRadioAdapter adapter = new CarRadioAdapter();
	private static Gson gson;
	
	static {
		GsonBuilder gsonBuilder = new GsonBuilder();
	    gsonBuilder.registerTypeAdapter(Car.class, new CarRadioJsonDeserializer());
	    gson = gsonBuilder.create();
	}

	public static CarRadioAdapter getAdapter() {
		return adapter;
	}
	
	@Override
	public Car deserialize(ObjectInputStream in) throws IOException, ClassNotFoundException {
		gson.fromJson((String) in.readObject(), Car.class);
		return null;
	}	
}
