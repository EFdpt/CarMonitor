package it.uniroma1.fastcharge.carmonitor.app.controllers.preferences;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.ResourceBundle;

import it.uniroma1.fastcharge.carmonitor.app.controllers.MainController;
import it.uniroma1.fastcharge.carmonitor.app.views.i18n.I18N;
import it.uniroma1.fastcharge.carmonitor.config.ApplicationPreferences;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.GaussianBlur;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.scene.layout.Pane;

public class PreferencesController implements Initializable {
	
	private Stage parentStage, stage;
	
	private StringProperty logDirProperty;
	
	@FXML
	private Label logDirLabel, selectedDirLabel, baudrateLabel, chartRefTimeLabel, chartTimeLabel, languageLabel,
					viewRefTimeLabel, viewTimeLabel,
					rpmMaxValueLabel, suspMaxValueLabel, accMaxValueLabel;
	
	@FXML
	private TextField baudrateTextField, rpmMaxTextField, suspMaxTextField, accMaxTextField;
	
	@FXML
	private Slider chartRefreshTimeSlider, viewRefreshTimeSlider;
	
	@FXML
	private ComboBox<Locale> languageComboBox;
	
	@FXML
	private Button applyButton, cancelButton, chooseLogDirButton;
	
	@FXML
	private Accordion preferencesAccordion;
	
	@FXML
	private TitledPane appPreferencesPane, vehiclePreferencesPane;
	
	private MainController rootController;

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
		viewRefTimeLabel.textProperty().bind(I18N.createStringBinding("Preferences.ViewTime"));
		chartTimeLabel.textProperty().bind(Bindings.format("%.1f s", chartRefreshTimeSlider.valueProperty()));
		viewTimeLabel.textProperty().bind(Bindings.format("x%.1f", viewRefreshTimeSlider.valueProperty()));
		languageLabel.textProperty().bind(I18N.createStringBinding("Preferences.Language"));
		applyButton.textProperty().bind(I18N.createStringBinding("Preferences.Apply"));
		cancelButton.textProperty().bind(I18N.createStringBinding("Preferences.Cancel"));
		chooseLogDirButton.textProperty().bind(I18N.createStringBinding("Preferences.Choose"));
		
		baudrateTextField.setText(Integer.toString(ApplicationPreferences.getConfiguration().getBaudRate()));
		
		chartRefreshTimeSlider.setValue(ApplicationPreferences.getConfiguration().getChartRefreshTime());
		viewRefreshTimeSlider.setValue(ApplicationPreferences.getConfiguration().getViewRefreshTime());
		
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
		
		rpmMaxTextField.setText(Integer.toString(ApplicationPreferences.getConfiguration().getCarPreferences().getRpmMaxValueProperty().get()));
		suspMaxTextField.setText(Integer.toString(ApplicationPreferences.getConfiguration().getCarPreferences().getSuspensionsMaxValueProperty().get()));
		accMaxTextField.setText(Integer.toString(ApplicationPreferences.getConfiguration().getCarPreferences().getAccelerometersMaxValueProperty().get()));
		
		rpmMaxValueLabel.textProperty().bind(I18N.createStringBinding("Preferences.RpmMaxValue"));
		suspMaxValueLabel.textProperty().bind(I18N.createStringBinding("Preferences.SuspMaxValue"));
		accMaxValueLabel.textProperty().bind(I18N.createStringBinding("Preferences.AccMaxValue"));
		
		applyButton.setOnAction(this::applyPreferences);
		cancelButton.setOnAction(this::cancelPreferences);
		
		chooseLogDirButton.setOnAction(this::handleChooseLogDir);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		appPreferencesPane.textProperty().bind(I18N.createStringBinding("Preferences.Pane.AppPref"));
		vehiclePreferencesPane.textProperty().bind(I18N.createStringBinding("Preferences.Pane.CarPref"));
		preferencesAccordion.setExpandedPane(appPreferencesPane);
		
		Tooltip tp = new Tooltip();
		tp.textProperty().bind(selectedDirLabel.textProperty());
		selectedDirLabel.setTooltip(tp);
	}
	
	public PreferencesController(Stage parentStage, Stage stage, MainController rootController) {
		this.parentStage = parentStage;
		this.stage = stage;
		this.rootController = rootController;
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
		Locale l;
		
		int rpmMaxValue;
		int suspMaxValue;
		int accMaxValue;
		
		try {
			baudRate = Integer.parseInt(baudrateTextField.getText());
		} catch (Exception e) {
			baudRate = ApplicationPreferences.getConfiguration().getDefaultBaudRate();
		}
		
		try {
			rpmMaxValue = Integer.parseInt(rpmMaxTextField.getText());
		} catch (Exception e) {
			rpmMaxValue = ApplicationPreferences.getConfiguration().getCarPreferences().getDefaultRpmMaxValue();
		}
		
		try {
			suspMaxValue = Integer.parseInt(suspMaxTextField.getText());
		} catch (Exception e) {
			suspMaxValue = ApplicationPreferences.getConfiguration().getCarPreferences().getDefaultSuspensionsMaxValue();
		}
		
		try {
			accMaxValue = Integer.parseInt(accMaxTextField.getText());
		} catch (Exception e) {
			accMaxValue = ApplicationPreferences.getConfiguration().getCarPreferences().getDefaultAccelerometersMaxValue();
		}
		
		ApplicationPreferences.getConfiguration().setBaudRate(baudRate);
		ApplicationPreferences.getConfiguration().getCarPreferences().getRpmMaxValueProperty().set(rpmMaxValue);
		ApplicationPreferences.getConfiguration().getCarPreferences().getSuspensionsMaxValueProperty().set(suspMaxValue);
		ApplicationPreferences.getConfiguration().getCarPreferences().getAccelerometersMaxValueProperty().set(accMaxValue);
		
		
		if (languageComboBox.getValue() != null)
			l = languageComboBox.getValue();
		else
			l = I18N.getDefaultLocale();
		
		I18N.setLocale(l);
		ApplicationPreferences.getConfiguration().setLocale(l);
		
		ApplicationPreferences.getConfiguration().setChartRefreshTime(chartRefreshTimeSlider.getValue());
		ApplicationPreferences.getConfiguration().setViewRefreshTime(viewRefreshTimeSlider.getValue());
		
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
		rootController.showUserNotice(I18N.get("Notice.PreferencesSaved"));
	}
	
	private void cancelPreferences(ActionEvent event) {
		shutdown();
		stage.close();
	}

}
