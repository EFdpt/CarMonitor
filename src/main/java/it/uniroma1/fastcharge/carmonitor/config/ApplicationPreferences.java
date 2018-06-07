package it.uniroma1.fastcharge.carmonitor.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ApplicationPreferences {
	private static Preferences preferences = new Preferences(); // initialize default preferences
	
	public static Preferences getConfiguration() {
		return preferences;
	}
	
	public static void loadConfiguration(String location) throws IOException {
		GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
		gsonBuilder.registerTypeAdapter(Preferences.class, new OptionsBeanAdapter());
		Gson JSON = gsonBuilder.create();
		String file = readFile(location);
		preferences = JSON.fromJson(file, Preferences.class);
	}
	
	public static void saveConfiguration(String location) throws IOException {
		GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
		gsonBuilder.registerTypeAdapter(Preferences.class, new OptionsBeanAdapter());
		Gson JSON = gsonBuilder.create();
		String file = JSON.toJson(preferences, Preferences.class);
		writeFile(location, file);
	}
	
	private static void writeFile(String fileName, String text) throws IOException {
		PrintWriter out = new PrintWriter(fileName);
		try {
			out.println(text);
		} finally {
			out.close();
		}
	}
	
	private static String readFile(String fileName) throws IOException {
	    BufferedReader br = new BufferedReader(new FileReader(fileName));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = "";

	        while ((line = br.readLine()) != null) {
	            sb.append(line);
	            sb.append("\n");
	        }
	        return sb.toString();
	    } finally {
	        br.close();
	    }
	}
}
