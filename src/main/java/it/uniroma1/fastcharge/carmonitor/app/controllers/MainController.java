package it.uniroma1.fastcharge.carmonitor.app.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDecorator;

import it.uniroma1.fastcharge.carmonitor.app.MainApp;
import it.uniroma1.fastcharge.carmonitor.app.controllers.preferences.PreferencesController;
import it.uniroma1.fastcharge.carmonitor.app.models.activities.atomic.RadioConnectTask;
import it.uniroma1.fastcharge.carmonitor.app.models.activities.framework.TaskExecutor;
import it.uniroma1.fastcharge.carmonitor.app.models.radio.SerialRadio;
import it.uniroma1.fastcharge.carmonitor.app.models.session.Session;
import it.uniroma1.fastcharge.carmonitor.app.views.i18n.I18N;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController implements Initializable {
	
	private Stage primaryStage;
	
	@FXML
	private BorderPane mainPane;
	
	@FXML
	private Menu fileMenu, exportMenu, radioMenu, serialPortMenu, windowMenu, helpMenu;
	
	@FXML
	private MenuItem exportCsvMenuItem, exportPrevMenuItem, connectMenuItem, disconnectMenuItem, closeMenuItem, preferencesMenuItem, aboutMenuItem;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fileMenu.textProperty().bind(I18N.createStringBinding("Menu.File"));
		exportMenu.textProperty().bind(I18N.createStringBinding("Menu.File.Export"));
		exportCsvMenuItem.textProperty().bind(I18N.createStringBinding("Menu.File.Export.Csv"));
		exportPrevMenuItem.textProperty().bind(I18N.createStringBinding("Menu.File.Export.Prev"));
		closeMenuItem.textProperty().bind(I18N.createStringBinding("Menu.File.Close"));	
		radioMenu.textProperty().bind(I18N.createStringBinding("Menu.Radio"));
		serialPortMenu.textProperty().bind(I18N.createStringBinding("Menu.Radio.Port"));
		connectMenuItem.textProperty().bind(I18N.createStringBinding("Menu.Radio.Connect"));
		disconnectMenuItem.textProperty().bind(I18N.createStringBinding("Menu.Radio.Disconnect"));
		windowMenu.textProperty().bind(I18N.createStringBinding("Menu.Window"));
		preferencesMenuItem.textProperty().bind(I18N.createStringBinding("Menu.Window.Preferences"));
		helpMenu.textProperty().bind(I18N.createStringBinding("Menu.Help"));
		aboutMenuItem.textProperty().bind(I18N.createStringBinding("Menu.Help.About"));
		
		connectMenuItem.setOnAction(this::handleSerialRadioConnect);
		preferencesMenuItem.setOnAction(this::handleShowPreferences);
		radioMenu.showingProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> observable,
		            Boolean oldValue, Boolean newValue) {

		        if (newValue) {
		        	handleGetSerialPorts();
		        }
		    }
		});
	}
	
	public MainController(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	private void handleGetSerialPorts() {
		
		// add serialPort menuItem if not already present
		SerialRadio.getCommPorts().forEach((port) -> {
			MenuItem i = serialPortMenu.getItems().stream()
										.filter(item -> item.getText().equals(port.getSystemPortName()))
										.findFirst()
										.orElse(null);
			if (i == null) {
				MenuItem item = new MenuItem(port.getSystemPortName());
				item.setOnAction(this::handleSetSerialPort);
				serialPortMenu.getItems().add(item);
				if (serialPortMenu.getItems().get(0) != null)
					serialPortMenu.getItems().get(0).getStyleClass().add("menu-item-first");
			}
		});
		
		// remove serialPort menuItem if disconnected
		serialPortMenu.getItems().parallelStream().forEach(item -> {
			if (SerialRadio.getCommPorts().stream()
						.filter(port -> port.getSystemPortName().equals(item.getText()))
						.findFirst()
						.orElse(null) == null)
				serialPortMenu.getItems().remove(item);
			if (serialPortMenu.getItems().get(0) != null)
				serialPortMenu.getItems().get(0).getStyleClass().add("menu-item-first");
		});
	}
	
	private void handleSetSerialPort(ActionEvent event) {
		MenuItem i = (MenuItem) event.getSource();
		SerialRadio radio = SerialRadio.getCommPorts().parallelStream()
						.filter(p -> p.getSystemPortName().equals(i.getText()))
						.findFirst()
						.orElse(null);
		if (radio == null)
			return;
		if (Session.getDefaultInstance().getRadio() == null || !Session.getDefaultInstance().getRadio().isOpen()) {
			Session.getDefaultInstance().setRadio(radio);
			System.out.println("Serial port successfully setted");
			serialPortMenu.setText("Serial port: (" + radio.getSystemPortName() + ")");
		}
	}
	
	private void handleSerialRadioConnect(ActionEvent e) {
		if (Session.getDefaultInstance().getRadio() == null || Session.getDefaultInstance().getRadio().isOpen())
			return;
		TaskExecutor.getInstance().perform(new RadioConnectTask());
		serialPortMenu.setDisable(true);
		connectMenuItem.setDisable(true);
	}
	
	private void handleShowPreferences(ActionEvent event) {
		Parent root;
		Scene scene;
		
		Stage stage = new Stage();
		stage.titleProperty().bind(I18N.createStringBinding("Preferences.StageTitle"));
		
        try {
        	PreferencesController preferencesController = new PreferencesController(primaryStage, stage);
        	FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/it/uniroma1/fastcharge/carmonitor/app/views/preferences/PreferencesView.fxml"));
    		loader.setController(preferencesController);
    		root = loader.load();
        	
            JFXDecorator decorator = new JFXDecorator(stage, root);
            
            scene = new Scene(decorator, 400, 600);
            scene.getStylesheets().add(MainApp.class.getResource("/it/uniroma1/fastcharge/carmonitor/app/assets/stylesheets/application.css").toExternalForm());
            scene.getStylesheets().add(MainApp.class.getResource("/it/uniroma1/fastcharge/carmonitor/app/assets/stylesheets/preferences.css").toExternalForm());
            stage.setScene(scene);
            
            stage.setResizable(false);
            
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            
            stage.centerOnScreen();
            stage.setOnHidden(e -> preferencesController.shutdown());

            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}

}
