package it.uniroma1.fastcharge.carmonitor.app.models.car;

import java.io.IOException;

public interface ICarDeserializer {
	public Car deserialize(byte[] in) throws IOException, ClassNotFoundException;
}
