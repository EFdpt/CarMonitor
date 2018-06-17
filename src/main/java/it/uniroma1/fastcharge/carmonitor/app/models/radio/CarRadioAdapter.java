package it.uniroma1.fastcharge.carmonitor.app.models.radio;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.uniroma1.fastcharge.carmonitor.app.models.car.Car;
import it.uniroma1.fastcharge.carmonitor.app.models.car.ICarDeserializer;

class CarRadioAdapter implements ICarDeserializer {
	
	private final static CarRadioAdapter adapter = new CarRadioAdapter();
	private static Gson gson;
	
	private CarRadioAdapter() {
		GsonBuilder gsonBuilder = new GsonBuilder();
	    gsonBuilder.registerTypeAdapter(Car.class, new CarRadioJsonDeserializer());
	    gson = gsonBuilder.create();
	}

	public static CarRadioAdapter getAdapter() {
		return adapter;
	}
	
	@Override
	public Car deserialize(byte[] in) throws IOException, ClassNotFoundException {
		return gson.fromJson(new String(in, "ASCII").replace("\r\n", ""), Car.class);
	}	
}
