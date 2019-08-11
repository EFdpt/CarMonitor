package it.uniroma1.fastcharge.carmonitor.app.views.i18n;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.LocaleUtils;

import it.uniroma1.fastcharge.carmonitor.config.ApplicationPreferences;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public final class I18N {
    /** the current selected Locale. */
    private static final ObjectProperty<Locale> locale;
    
    static {
        locale = new SimpleObjectProperty<>(ApplicationPreferences.getConfiguration().getLocale());
        locale.addListener((observable, oldValue, newValue) -> Locale.setDefault(newValue));
    }
	
    public static List<Locale> getSupportedLocales() {
        return new ArrayList<>(Arrays.asList(new Locale("it", "IT"), new Locale("en", "EN"), new Locale("fr", "FR"), new Locale("de", "DE"), new Locale("el", "GR")));
    }
    
    public static Locale getDefaultLocale() {
        Locale sysDefault = Locale.getDefault();
        return getSupportedLocales().contains(sysDefault) ? sysDefault : new Locale("en", "EN");
    }
    
    public static Locale getLocale() {
        return locale.get();
    }
    
    public static void setLocale(Locale locale) {
    	localeProperty().set(locale);
        Locale.setDefault(locale);
    }
    
    public static ObjectProperty<Locale> localeProperty() {
        return locale;
    }
    
    public static String get(final String key, final Object... args) {
        ResourceBundle bundle = ResourceBundle.getBundle("it/uniroma1/fastcharge/carmonitor/config/locales/LocaleMessages", locale.get());
        return MessageFormat.format(bundle.getString(key), args);
    }
    
    public static StringBinding createStringBinding(final String key, Object... args) {
        return Bindings.createStringBinding(() -> get(key, args), locale);
    }
    
    public static StringBinding createStringBinding(Callable<String> func) {
        return Bindings.createStringBinding(func, locale);
    }
    
    public static String localeToString(Locale l) {
		String language = l.getDisplayLanguage();
		return language.substring(0, 1).toUpperCase() + language.substring(1);
	}

	public static Locale stringToLocale(String localeString) {
		return LocaleUtils.toLocale(localeString.substring(0, 1).toLowerCase() + localeString.substring(1));
	}
}
