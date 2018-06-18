package it.uniroma1.fastcharge.carmonitor.config;

import java.lang.reflect.Type;
import java.util.Locale;

import org.apache.commons.lang3.LocaleUtils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import it.uniroma1.fastcharge.carmonitor.app.views.i18n.I18N;

public final class OptionsBeanAdapter implements JsonSerializer<Preferences>, JsonDeserializer<Preferences> {

	public OptionsBeanAdapter() {}
	
	 @Override
	 public JsonElement serialize(Preferences src, Type typeOfSrc,
	            JsonSerializationContext context) {

	        JsonObject obj = new JsonObject();
	        obj.addProperty("locale", localeToString(src.getLocale()));
	        obj.addProperty("baudRate", src.getBaudRate());
	        obj.addProperty("chartRefreshTime", src.getChartRefreshTime());
	        obj.addProperty("logDir", src.getLogDir());

	        return obj;
	    }

		@Override
		public Preferences deserialize(JsonElement src, Type typeOfSrc,
				JsonDeserializationContext context)
		
				throws JsonParseException {

			JsonObject jobject = src.getAsJsonObject();
			Preferences result = new Preferences();
			
			result.setLocale(stringToLocale(jobject.get("locale").getAsString()));
			result.setBaudRate(jobject.get("baudRate").getAsInt());
			result.setChartRefreshTime(jobject.get("chartRefreshTime").getAsDouble());
			result.setLogDir(jobject.get("logDir").getAsString());
			
			return result;
		}
		
		private  String localeToString(Locale l) {
		    return l.getLanguage() + "_" + l.getCountry().toUpperCase();
		}

		private Locale stringToLocale(String locale) {
			if (locale != null && !locale.isEmpty()) {
				try {
					return LocaleUtils.toLocale(locale);
				} catch (final java.lang.IllegalArgumentException e) {
						return null;
				}
			} else
				return null;
		}
	}