package it.uniroma1.fastcharge.carmonitor.app;

import it.uniroma1.fastcharge.carmonitor.app.controllers.MainController;
import it.uniroma1.fastcharge.carmonitor.app.views.i18n.I18N;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXDecorator;

public class MainApp extends Application {
	private Stage primaryStage;
	private Stage splashStage;
	private Parent rootLayout;
	private MainController rootController;
	
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
    	this.primaryStage = primaryStage;
    	
    	loadSplashScreen();
    }
    
    @Override
    public void stop() {
    	rootController.stop();
    	Platform.exit();
    }
    
    private void loadSplashScreen() {
    	Scene scene;
    	splashStage = new Stage();
    	splashStage.getIcons().add(new Image(MainApp.class.getResourceAsStream("/it/uniroma1/fastcharge/carmonitor/app/assets/resources/images/fast_charge_icon.png")));
    	
        try {
            StackPane pane = FXMLLoader.load(MainApp.class.getResource("/it/uniroma1/fastcharge/carmonitor/app/views/splash/SplashView.fxml"));
            scene = new Scene(pane);
            scene.setFill(Color.TRANSPARENT);
            splashStage.setScene(scene);
            splashStage.initStyle(StageStyle.TRANSPARENT);
            splashStage.show();
            
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), pane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), pane);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            fadeIn.play();

            fadeIn.setOnFinished((e) -> {
                fadeOut.play();
            });

            fadeOut.setOnFinished((e) -> {
                try {
                	loadMainScreen();
                } catch (IOException ex) {
                    Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

        } catch (IOException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadMainScreen() throws IOException {
    	
    	rootController = new MainController(primaryStage);
    	rootLayout = rootController.getRootParent();
    	
		JFXDecorator decorator = new JFXDecorator(primaryStage, rootLayout);
		
		primaryStage.setMinHeight(600.0);
		primaryStage.setMinWidth(800.0);	
		primaryStage.getIcons().add(new Image(MainApp.class.getResourceAsStream("/it/uniroma1/fastcharge/carmonitor/app/assets/resources/images/fast_charge_icon.png")));
		
		Scene scene = new Scene(decorator);
		scene.getStylesheets().add(getClass().getResource("assets/stylesheets/application.css").toExternalForm());

		this.primaryStage.setScene(scene);
		this.primaryStage.titleProperty().bind(I18N.createStringBinding("appTitle"));
		//this.primaryStage.setMaximized(true);
		splashStage.close();
		primaryStage.show();
    }
}