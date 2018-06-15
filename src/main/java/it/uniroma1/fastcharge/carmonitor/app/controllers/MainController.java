package it.uniroma1.fastcharge.carmonitor.app.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDecorator;

import it.uniroma1.fastcharge.carmonitor.app.MainApp;
import it.uniroma1.fastcharge.carmonitor.app.controllers.car.CarController;
import it.uniroma1.fastcharge.carmonitor.app.controllers.preferences.PreferencesController;
import it.uniroma1.fastcharge.carmonitor.app.models.activities.atomic.LoadPreferencesTask;
import it.uniroma1.fastcharge.carmonitor.app.models.activities.atomic.RadioConnectTask;
import it.uniroma1.fastcharge.carmonitor.app.models.activities.atomic.RadioDisconnectTask;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MainController implements Initializable {
	
	private Stage primaryStage;
	private Parent rootLayout;
	
	@FXML
	private final CarController carController;
	
	@FXML
	private final MenuBarController menuBarController;
	
	@FXML
	private Pane mainPane;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	public MainController(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		
		TaskExecutor.getInstance().perform(new LoadPreferencesTask());
		
		menuBarController = new MenuBarController(primaryStage);
		carController = new CarController(primaryStage);
    	
		FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/it/uniroma1/fastcharge/carmonitor/app/views/MainView.fxml"));
		
    	Callback<Class<?>, Object> controllerFactory = type -> {
		    if (type == MainController.class) {
		        return this ;
		    } else if (type == CarController.class) {
		        return carController ;
		    } else if (type == MenuBarController.class) {
		    	return menuBarController;
		    } else { 
		        // default behavior for controllerFactory:
		        try {
		            return type.newInstance();
		        } catch (Exception exc) {
		            exc.printStackTrace();
		            throw new RuntimeException(exc); // fatal, just bail...
		        }
		    }
		};
    	
    	loader.setControllerFactory(controllerFactory);
		this.rootLayout = loader.load();
	}
	
	public Parent getRootParent() {
		return rootLayout;
	}
}
