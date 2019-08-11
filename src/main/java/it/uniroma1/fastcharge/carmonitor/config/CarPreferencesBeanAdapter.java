package it.uniroma1.fastcharge.carmonitor.config;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class CarPreferencesBeanAdapter implements JsonSerializer<CarPreferences>, JsonDeserializer<CarPreferences> {
	public CarPreferencesBeanAdapter() {}
	
	 @Override
	 public JsonElement serialize(CarPreferences src, Type typeOfSrc,
	            JsonSerializationContext context) {

	        JsonObject obj = new JsonObject();
	        
	        obj.addProperty("rpmMaxValue", src.getRpmMaxValueProperty().get());
	        obj.addProperty("accelerometersMaxValue", src.getAccelerometersMaxValueProperty().get());
	        obj.addProperty("suspensionsMaxValue", src.getSuspensionsMaxValueProperty().get());

	        return obj;
	    }

		@Override
		public CarPreferences deserialize(JsonElement src, Type typeOfSrc,
				JsonDeserializationContext context)
		
				throws JsonParseException {

			JsonObject jobject = src.getAsJsonObject();
			CarPreferences result = new CarPreferences();
			
			result.getRpmMaxValueProperty().set(jobject.get("rpmMaxValue").getAsInt());
			result.getAccelerometersMaxValueProperty().set(jobject.get("accelerometersMaxValue").getAsInt());
			result.getSuspensionsMaxValueProperty().set(jobject.get("suspensionsMaxValue").getAsInt());
			
			return result;
		}
}
