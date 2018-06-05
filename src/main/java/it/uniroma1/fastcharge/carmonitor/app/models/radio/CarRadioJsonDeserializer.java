package it.uniroma1.fastcharge.carmonitor.app.models.radio;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import it.uniroma1.fastcharge.carmonitor.app.models.car.Car;

public class CarRadioJsonDeserializer implements JsonDeserializer<Car> {

	@Override
	public Car deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		 JsonObject jsonObject = json.getAsJsonObject();
		 
		 // pedals
		 JsonObject pedals = jsonObject.getAsJsonObject("pedals");
		 Car.getInstance().getPedals().setTps1(pedals.get("tps1").getAsByte());
		 Car.getInstance().getPedals().setTps2(pedals.get("tps2").getAsByte());
		 Car.getInstance().getPedals().setBrake(pedals.get("brake").getAsByte());
		 
		 // wheels
		 JsonObject wheels = jsonObject.getAsJsonObject("wheels");
		 Car.getInstance().getWheels().setLfWheelRpm(wheels.get("front_sx").getAsShort());
		 Car.getInstance().getWheels().setRfWheelRpm(wheels.get("front_dx").getAsShort());
		 Car.getInstance().getWheels().setLrWheelRpm(wheels.get("retro_sx").getAsShort());
		 Car.getInstance().getWheels().setRrWheelRpm(wheels.get("retro_dx").getAsShort());
		 
		 // accelerometers
		 
		 // suspensions
		 
		 return Car.getInstance();
	}

}
