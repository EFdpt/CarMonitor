package it.uniroma1.fastcharge.carmonitor.app.models.car;

import java.io.IOException;
import java.io.ObjectInputStream;

public interface ICarDeserializer {
	public Car deserialize(ObjectInputStream in) throws IOException, ClassNotFoundException;
}
