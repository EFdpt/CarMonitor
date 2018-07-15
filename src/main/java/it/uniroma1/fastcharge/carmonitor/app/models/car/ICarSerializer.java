package it.uniroma1.fastcharge.carmonitor.app.models.car;

import java.io.ObjectOutputStream;

public interface ICarSerializer {
	public ObjectOutputStream serialize(Car car);
}
