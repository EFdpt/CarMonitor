package it.uniroma1.fastcharge.carmonitor.app;

import it.uniroma1.fastcharge.carmonitor.app.controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.jfoenix.controls.JFXDecorator;

// Simple Hello World JavaFX program
public class MainApp extends Application {
	private Stage primaryStage;
	private Parent rootLayout;
	
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
    	this.primaryStage = primaryStage;
    	
    	MainController rootController = new MainController();
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma1/fastcharge/carmonitor/app/views/MainView.fxml"));
		loader.setController(rootController);
		this.rootLayout = loader.load();

		JFXDecorator decorator = new JFXDecorator(this.primaryStage, rootLayout);
		
		Scene scene = new Scene(decorator);
		scene.getStylesheets().add(getClass().getResource("assets/stylesheets/application.css").toExternalForm());

		this.primaryStage.setScene(scene);
		this.primaryStage.setTitle("FastCharge CarMonitor");
		this.primaryStage.setMaximized(true);
        this.primaryStage.show();
    }
}