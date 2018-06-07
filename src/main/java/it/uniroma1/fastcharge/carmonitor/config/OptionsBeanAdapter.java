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

public final class OptionsBeanAdapter implements JsonSerializer<Preferences>, JsonDeserializer<Preferences> {

	public OptionsBeanAdapter() {}
	
	 @Override
	 public JsonElement serialize(Preferences src, Type typeOfSrc,
	            JsonSerializationContext context) {

	        JsonObject obj = new JsonObject();
	        obj.addProperty("locale", localeToString(src.getLocale()));
	        obj.addProperty("baudRate", src.getBaudRate());
	        obj.addProperty("chartRefreshTime", src.getChartRefreshTime());

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
			
			/*
		    StringTokenizer tempStringTokenizer = new StringTokenizer(s ,",");
		    String l = "", c = "";
			if (tempStringTokenizer.hasMoreTokens())
		    	l = (String) tempStringTokenizer.nextElement();
		    if (tempStringTokenizer.hasMoreTokens())
		    	c = (String) tempStringTokenizer.nextElement();
		    return new Locale(l,c);*/
			
			/*
			if (localeString == null)
	        {
	            return null;
	        }
	        localeString = localeString.trim();
	        if (localeString.toLowerCase().equals("default"))
	        {
	            return Locale.getDefault();
	        }

	        // Extract language
	        int languageIndex = localeString.indexOf('_');
	        String language = null;
	        if (languageIndex == -1)
	        {
	            // No further "_" so is "{language}" only
	            return new Locale(localeString, "");
	        }
	        else
	        {
	            language = localeString.substring(0, languageIndex);
	        }

	        // Extract country
	        int countryIndex = localeString.indexOf('_', languageIndex + 1);
	        String country = null;
	        if (countryIndex == -1)
	        {
	            // No further "_" so is "{language}_{country}"
	            country = localeString.substring(languageIndex+1);
	            return new Locale(language, country);
	        }
	        else
	        {
	            // Assume all remaining is the variant so is "{language}_{country}_{variant}"
	            country = localeString.substring(languageIndex+1, countryIndex);
	            String variant = localeString.substring(countryIndex+1);
	            return new Locale(language, country, variant);
	        } */
		}
	}