package it.uniroma1.fastcharge.carmonitor.app.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.uniroma1.fastcharge.carmonitor.app.MainApp;
import it.uniroma1.fastcharge.carmonitor.app.controllers.car.CarController;
import it.uniroma1.fastcharge.carmonitor.app.controllers.car.chart.CarChartController;
import it.uniroma1.fastcharge.carmonitor.app.models.activities.atomic.LoadPreferencesTask;
import it.uniroma1.fastcharge.carmonitor.app.models.activities.framework.TaskExecutor;
import it.uniroma1.fastcharge.carmonitor.config.ApplicationPreferences;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.Observable;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

public class MainController implements Initializable {
	
	private Stage primaryStage;
	private Parent rootLayout;
	private Timeline timeline;
	
	private Callback<Class<?>, Object> controllerFactory;
	
	@FXML
	private final CarController carController;
	
	@FXML
	private final MenuBarController menuBarController;
	
	@FXML
	private Pane rootPane, mainPane;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	public MainController(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		
		TaskExecutor.getInstance().perform(new LoadPreferencesTask());
		
		menuBarController = new MenuBarController(this, primaryStage);
		carController = new CarController(primaryStage);
		
    	controllerFactory = type -> {
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
		
		FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/it/uniroma1/fastcharge/carmonitor/app/views/MainView.fxml"));
    	loader.setControllerFactory(controllerFactory);
    	this.rootLayout = loader.load();
	}
	
	public Parent getRootParent() {
		return rootLayout;
	}
	
	public Callback<Class<?>, Object> getControllerFactory() {
		return controllerFactory;
	}
	
	public void connectView() {
		timeline = new Timeline(
			    new KeyFrame(Duration.seconds(1), e -> {
			        // update all views
			    	carController.updateView();
			    })
			);
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.rateProperty()
        .bind(new SimpleDoubleProperty(1.0)
        .divide(ApplicationPreferences.getConfiguration().chartRefreshTimeProperty()));
		timeline.play();
	}
	
	public void disconnectView() {
		timeline.stop();
		carController.unbindView();
	}
	
	public void showUserNotice(String message) {
		carController.showUserNotice(message);
	}
}
