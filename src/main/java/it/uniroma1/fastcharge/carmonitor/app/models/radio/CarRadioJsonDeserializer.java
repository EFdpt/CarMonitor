package it.uniroma1.fastcharge.carmonitor.app.models.radio;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import it.uniroma1.fastcharge.carmonitor.app.models.car.Car;
import it.uniroma1.fastcharge.carmonitor.app.models.session.Session;

class CarRadioJsonDeserializer implements JsonDeserializer<Car> {

	@Override
	public Car deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		 JsonObject jsonObject = json.getAsJsonObject();
		 
		 // pedals
		 JsonObject pedals = jsonObject.getAsJsonObject("pedals");
		 Session.getDefaultInstance().getCar().getPedals().setTps1(pedals.get("tps1").getAsByte());
		 Session.getDefaultInstance().getCar().getPedals().setTps2(pedals.get("tps2").getAsByte());
		 Session.getDefaultInstance().getCar().getPedals().setBrake(pedals.get("brake").getAsByte());
		 Session.getDefaultInstance().getCar().getPedals().setAppsPlausibility(pedals.get("apps_plaus").getAsBoolean());
		 Session.getDefaultInstance().getCar().getPedals().setBrakePlausibility(pedals.get("brake_plaus").getAsBoolean());
		 
		 // suspensions
		 JsonObject suspensions = jsonObject.getAsJsonObject("suspensions");
		 Session.getDefaultInstance().getCar().getSuspensions().setLfSuspension(suspensions.get("front_sx").getAsByte());
		 Session.getDefaultInstance().getCar().getSuspensions().setRfSuspension(suspensions.get("front_dx").getAsByte());
		 Session.getDefaultInstance().getCar().getSuspensions().setLrSuspension(suspensions.get("retro_sx").getAsByte());
		 Session.getDefaultInstance().getCar().getSuspensions().setRrSuspension(suspensions.get("retro_dx").getAsByte());
		 
		 // wheels
		 JsonObject wheels = jsonObject.getAsJsonObject("wheels");
		 Session.getDefaultInstance().getCar().getWheels().setLfWheelRpm(wheels.get("front_sx").getAsShort());
		 Session.getDefaultInstance().getCar().getWheels().setRfWheelRpm(wheels.get("front_dx").getAsShort());
		 Session.getDefaultInstance().getCar().getWheels().setLrWheelRpm(wheels.get("retro_sx").getAsShort());
		 Session.getDefaultInstance().getCar().getWheels().setRrWheelRpm(wheels.get("retro_dx").getAsShort());
		 
		 // accelerometers
		 JsonObject accelerometers = jsonObject.getAsJsonObject("accelerometers");
		 Session.getDefaultInstance().getCar().getAccelerometers().setAccelerometerX(accelerometers.get("acc_x").getAsByte());
		 Session.getDefaultInstance().getCar().getAccelerometers().setAccelerometerZ(accelerometers.get("acc_z").getAsByte());
		 
		 return Session.getDefaultInstance().getCar();
	}

}
