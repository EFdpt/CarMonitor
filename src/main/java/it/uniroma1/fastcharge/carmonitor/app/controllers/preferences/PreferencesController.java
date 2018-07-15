package it.uniroma1.fastcharge.carmonitor.app.controllers.preferences;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.ResourceBundle;

import it.uniroma1.fastcharge.carmonitor.app.views.i18n.I18N;
import it.uniroma1.fastcharge.carmonitor.config.ApplicationPreferences;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.scene.layout.Pane;

public class PreferencesController implements Initializable {
	
	private Stage parentStage, stage;
	
	private StringProperty logDirProperty;
	
	@FXML
	private Label logDirLabel, selectedDirLabel, baudrateLabel, chartRefTimeLabel, chartTimeLabel, languageLabel;
	
	@FXML
	private TextField baudrateTextField;
	
	@FXML
	private Slider refreshTimeSlider;
	
	@FXML
	private ComboBox<Locale> languageComboBox;
	
	@FXML
	private Button applyButton, cancelButton, chooseLogDirButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
		GaussianBlur blur = new GaussianBlur(10);
		parentStage.getScene().getRoot().setEffect(blur);
		
		logDirProperty = new SimpleStringProperty(ApplicationPreferences.getConfiguration().getLogDir());
		selectedDirLabel.textProperty().bind(logDirProperty);
		logDirLabel.textProperty().bind(I18N.createStringBinding("Preferences.LogDirectory"));
		baudrateLabel.textProperty().bind(I18N.createStringBinding("Preferences.BaudRate"));
		chartRefTimeLabel.textProperty().bind(I18N.createStringBinding("Preferences.ChartTime"));
		chartTimeLabel.textProperty().bind(Bindings.format("%.1f s", refreshTimeSlider.valueProperty()));
		languageLabel.textProperty().bind(I18N.createStringBinding("Preferences.Language"));
		applyButton.textProperty().bind(I18N.createStringBinding("Preferences.Apply"));
		cancelButton.textProperty().bind(I18N.createStringBinding("Preferences.Cancel"));
		chooseLogDirButton.textProperty().bind(I18N.createStringBinding("Preferences.Choose"));
		
		baudrateTextField.setText(Integer.toString(ApplicationPreferences.getConfiguration().getBaudRate()));
		
		languageComboBox.setItems(FXCollections.observableArrayList(I18N.getSupportedLocales()));
		
		languageComboBox.setConverter(new StringConverter<Locale>() {

			@Override
			public String toString(Locale object) {
				return I18N.localeToString(object);
			}

			@Override
			public Locale fromString(String string) {
				return I18N.stringToLocale(string);
			}
		});
		
		languageComboBox.getSelectionModel().select(ApplicationPreferences.getConfiguration().getLocale());
		
		applyButton.setOnAction(this::applyPreferences);
		cancelButton.setOnAction(this::cancelPreferences);
		
		chooseLogDirButton.setOnAction(this::handleChooseLogDir);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public PreferencesController(Stage parentStage, Stage stage) {
		this.parentStage = parentStage;
		this.stage = stage;
	}
	
	public void shutdown() {
		parentStage.getScene().getRoot().setEffect(null);
    }
	
	private void handleChooseLogDir(ActionEvent event) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = 
                directoryChooser.showDialog(stage);
         
        if (selectedDirectory != null)
        	logDirProperty.setValue(selectedDirectory.getAbsolutePath());
	}
	
	private void applyPreferences(ActionEvent event) {
		int baudRate;
		try {
			if (!baudrateTextField.getText().equals(""))
				baudRate = Integer.parseInt(baudrateTextField.getText());
			else
				baudRate = ApplicationPreferences.getConfiguration().getDefaultBaudRate();
			
			ApplicationPreferences.getConfiguration().setBaudRate(baudRate);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Locale l;
		if (languageComboBox.getValue() != null)
			l = languageComboBox.getValue();
		else
			l = I18N.getDefaultLocale();
		
		I18N.setLocale(l);
		ApplicationPreferences.getConfiguration().setLocale(l);
		
		ApplicationPreferences.getConfiguration().setChartRefreshTime(refreshTimeSlider.getValue());
		
		if (Files.isWritable(Paths.get(logDirProperty.get())))
			ApplicationPreferences.getConfiguration().setLogDir(logDirProperty.get());
		else
			System.out.println("Invalid directory permission");
		
		try {
			ApplicationPreferences.savePreferences();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		shutdown();
		stage.close();
	}
	
	private void cancelPreferences(ActionEvent event) {
		shutdown();
		stage.close();
	}

}
