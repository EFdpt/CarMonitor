package it.uniroma1.fastcharge.carmonitor.app.controllers.car;

import java.net.URL;
import java.util.ResourceBundle;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import it.uniroma1.fastcharge.carmonitor.app.models.session.Session;
import it.uniroma1.fastcharge.carmonitor.app.views.i18n.I18N;
import it.uniroma1.fastcharge.carmonitor.config.ApplicationPreferences;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CarController implements Initializable {
	
	private Stage primaryStage;
	
	@FXML
	private GridPane pedalsPane, wheelsPane, suspensionsPane, accelerometersPane;
	
	@FXML
	private Label pedalsLabel, wheelsLabel, suspensionsLabel, accelerometersLabel;
	
	@FXML
	private AnchorPane carImageView;
	
	@FXML
	private Rectangle pedalsRectangle, wheelsRectangle, suspensionsRectangle, accelerometersRectangle;
	
	@FXML
	private Gauge acceleratorGauge, brakeGauge, lfWheelGauge, rfWheelGauge, lrWheelGauge, rrWheelGauge,
			lfSuspensionGauge, rfSuspensionGauge, lrSuspensionGauge, rrSuspensionGauge,
			accelerometerXGauge, accelerometerZGauge;
	
	@FXML
	private Label userNoticeLabel;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {		
		
		// pedals
		acceleratorGauge.titleProperty().bind(I18N.createStringBinding("Vehicle.Pedals.APPS"));
		
		brakeGauge.titleProperty().bind(I18N.createStringBinding("Vehicle.Pedals.Brake"));
		
		pedalsRectangle.widthProperty().bind(pedalsPane.widthProperty());
        pedalsLabel.textProperty().bind(I18N.createStringBinding("Vehicle.Pedals"));
        
        // accelerometers
        accelerometerXGauge.titleProperty().bind(I18N.createStringBinding("Vehicle.Accelerometers.X"));
        accelerometerZGauge.titleProperty().bind(I18N.createStringBinding("Vehicle.Accelerometers.Z"));
        
        accelerometersRectangle.widthProperty().bind(accelerometersPane.widthProperty());
		accelerometersLabel.textProperty().bind(I18N.createStringBinding("Vehicle.Accelerometers"));
        
        // wheels
        lfWheelGauge.titleProperty().bind(I18N.createStringBinding("Vehicle.Wheels.FL"));
        rfWheelGauge.titleProperty().bind(I18N.createStringBinding("Vehicle.Wheels.FR"));
        lrWheelGauge.titleProperty().bind(I18N.createStringBinding("Vehicle.Wheels.RL"));
        rrWheelGauge.titleProperty().bind(I18N.createStringBinding("Vehicle.Wheels.RR"));
		
		wheelsRectangle.widthProperty().bind(wheelsPane.widthProperty());
		wheelsLabel.textProperty().bind(I18N.createStringBinding("Vehicle.Wheels"));

		// suspensions
		lfSuspensionGauge.titleProperty().bind(I18N.createStringBinding("Vehicle.Suspensions.FL"));
		rfSuspensionGauge.titleProperty().bind(I18N.createStringBinding("Vehicle.Suspensions.FR"));
		lrSuspensionGauge.titleProperty().bind(I18N.createStringBinding("Vehicle.Suspensions.RL"));
		rrSuspensionGauge.titleProperty().bind(I18N.createStringBinding("Vehicle.Suspensions.RR"));

		suspensionsRectangle.widthProperty().bind(suspensionsPane.widthProperty());
		suspensionsLabel.textProperty().bind(I18N.createStringBinding("Vehicle.Suspensions"));
        
        primaryStage.widthProperty().addListener((__) -> {
        	wheelsPane.setMaxWidth(wheelsPane.getHeight() * 1.2);
	        suspensionsPane.setMaxWidth(suspensionsPane.getHeight() * 1.2);
	        accelerometersPane.setMaxWidth(accelerometersPane.getHeight() * 1.2);
	        pedalsPane.setMaxWidth(pedalsPane.getHeight() * 1.2);
	    });
        
        primaryStage.widthProperty().addListener((__) -> {
           userNoticeLabel.setPrefWidth(primaryStage.getWidth());
    	});
        
        //primaryStage.heightProperty().addListener((__) -> {
        //	carImageView.setFitHeight(wheelsPane.getHeight());
	    //});
        
        //carImageView.setStyle("-fx-background-image: url('../../assets/resources/images/car.png'); -fx-background-size: contain;");
	}
	
	public CarController(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	public void updateView() {
		acceleratorGauge.setValue(Session.getDefaultInstance().getCar().getPedals().getTps1());
		brakeGauge.setValue(Session.getDefaultInstance().getCar().getPedals().getBrake());
		
		lfWheelGauge.setValue(Session.getDefaultInstance().getCar().getWheels().getLfWheelRpm());
		rfWheelGauge.setValue(Session.getDefaultInstance().getCar().getWheels().getRfWheelRpm());
		lrWheelGauge.setValue(Session.getDefaultInstance().getCar().getWheels().getLrWheelRpm());
		rrWheelGauge.setValue(Session.getDefaultInstance().getCar().getWheels().getRrWheelRpm());
		
		lfSuspensionGauge.setValue(Session.getDefaultInstance().getCar().getSuspensions().getLfSuspension());
		rfSuspensionGauge.setValue(Session.getDefaultInstance().getCar().getSuspensions().getRfSuspension());
		lrSuspensionGauge.setValue(Session.getDefaultInstance().getCar().getSuspensions().getLrSuspension());
		rrSuspensionGauge.setValue(Session.getDefaultInstance().getCar().getSuspensions().getRrSuspension());
		
		accelerometerXGauge.setValue(Session.getDefaultInstance().getCar().getAccelerometers().getAccelerometerX());
		accelerometerZGauge.setValue(Session.getDefaultInstance().getCar().getAccelerometers().getAccelerometerZ());
	}
	
	public void unbindView() {
		acceleratorGauge.setValue(0);
		brakeGauge.setValue(0);
		
		lfWheelGauge.setValue(0);
		rfWheelGauge.setValue(0);
		lrWheelGauge.setValue(0);
		rrWheelGauge.setValue(0);
		
		lfSuspensionGauge.setValue(0);
		rfSuspensionGauge.setValue(0);
		lrSuspensionGauge.setValue(0);
		rrSuspensionGauge.setValue(0);
		
		accelerometerXGauge.setValue(0);
		accelerometerZGauge.setValue(0);
	}
	
	public void showUserNotice(String message) {
		userNoticeLabel.setText(I18N.get(message));
		
		Timeline timeline = new Timeline(
				new KeyFrame(Duration.seconds(8), e -> {
			        userNoticeLabel.setText("");
			    })
			);
		
		timeline.setCycleCount(1);
		timeline.play();
	}

}
