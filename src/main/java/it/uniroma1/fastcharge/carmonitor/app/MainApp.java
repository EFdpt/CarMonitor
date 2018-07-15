package it.uniroma1.fastcharge.carmonitor.app;

import it.uniroma1.fastcharge.carmonitor.app.controllers.MainController;
import it.uniroma1.fastcharge.carmonitor.app.views.i18n.I18N;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
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
    	
    	MainController rootController = new MainController(primaryStage);
    	rootLayout = rootController.getRootParent();
    	
		JFXDecorator decorator = new JFXDecorator(this.primaryStage, rootLayout);
		
		primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {
			Screen screen = Screen.getPrimary();
			Rectangle2D bounds = screen.getVisualBounds();
			primaryStage.setX(bounds.getMinX());
			primaryStage.setWidth(bounds.getWidth());
        });
		
		primaryStage.heightProperty().addListener((observable, oldValue, newValue) -> {
			Screen screen = Screen.getPrimary();
			Rectangle2D bounds = screen.getVisualBounds();
			primaryStage.setY(bounds.getMinY());
			primaryStage.setHeight(bounds.getHeight());
        });
		
		Scene scene = new Scene(decorator);
		scene.getStylesheets().add(getClass().getResource("assets/stylesheets/application.css").toExternalForm());

		this.primaryStage.setScene(scene);
		this.primaryStage.titleProperty().bind(I18N.createStringBinding("appTitle"));
		this.primaryStage.setMaximized(true);
		
        this.primaryStage.show();
    }
    
    @Override
    public void stop() {
    	Platform.exit();
    }
}