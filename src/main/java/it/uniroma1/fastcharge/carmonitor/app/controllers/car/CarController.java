package it.uniroma1.fastcharge.carmonitor.app.controllers.car;

import java.net.URL;
import java.util.ResourceBundle;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import it.uniroma1.fastcharge.carmonitor.app.MainApp;
import it.uniroma1.fastcharge.carmonitor.app.models.session.Session;
import it.uniroma1.fastcharge.carmonitor.app.views.i18n.I18N;
import it.uniroma1.fastcharge.carmonitor.config.ApplicationPreferences;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
	private AnchorPane carImagePane;
	
	@FXML
	private Rectangle pedalsRectangle, wheelsRectangle, suspensionsRectangle, accelerometersRectangle;
	
	@FXML
	private Gauge acceleratorGauge, brakeGauge, lfWheelGauge, rfWheelGauge, lrWheelGauge, rrWheelGauge,
			lfSuspensionGauge, rfSuspensionGauge, lrSuspensionGauge, rrSuspensionGauge,
			accelerometerXGauge, accelerometerZGauge;
	
	@FXML
	private Label userNoticeLabel, radioPortNotice;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		radioPortNotice.setText("");
		
		// pedals
		acceleratorGauge.titleProperty().bind(I18N.createStringBinding("Vehicle.Pedals.APPS"));
		
		brakeGauge.titleProperty().bind(I18N.createStringBinding("Vehicle.Pedals.Brake"));
		acceleratorGauge.setBarColor(Color.web("#0ccc1f"));
		brakeGauge.setBarColor(Color.web("#f71818"));
		
		pedalsRectangle.widthProperty().bind(pedalsPane.widthProperty());
        pedalsLabel.textProperty().bind(I18N.createStringBinding("Vehicle.Pedals"));
        
        // accelerometers
        accelerometerXGauge.titleProperty().bind(I18N.createStringBinding("Vehicle.Accelerometers.X"));
        accelerometerZGauge.titleProperty().bind(I18N.createStringBinding("Vehicle.Accelerometers.Z"));
        accelerometerXGauge.maxValueProperty().bind(ApplicationPreferences.getConfiguration().getCarPreferences().getAccelerometersMaxValueProperty());
        accelerometerZGauge.maxValueProperty().bind(ApplicationPreferences.getConfiguration().getCarPreferences().getAccelerometersMaxValueProperty());
        accelerometerXGauge.setBarColor(Color.web("#ef7f02"));
        accelerometerZGauge.setBarColor(Color.web("#ef7f02"));
        
        accelerometersRectangle.widthProperty().bind(accelerometersPane.widthProperty());
		accelerometersLabel.textProperty().bind(I18N.createStringBinding("Vehicle.Accelerometers"));
        
        // wheels
        lfWheelGauge.titleProperty().bind(I18N.createStringBinding("Vehicle.Wheels.FL"));
        rfWheelGauge.titleProperty().bind(I18N.createStringBinding("Vehicle.Wheels.FR"));
        lrWheelGauge.titleProperty().bind(I18N.createStringBinding("Vehicle.Wheels.RL"));
        rrWheelGauge.titleProperty().bind(I18N.createStringBinding("Vehicle.Wheels.RR"));
        lfWheelGauge.maxValueProperty().bind(ApplicationPreferences.getConfiguration().getCarPreferences().getRpmMaxValueProperty());
        rfWheelGauge.maxValueProperty().bind(ApplicationPreferences.getConfiguration().getCarPreferences().getRpmMaxValueProperty());
        lrWheelGauge.maxValueProperty().bind(ApplicationPreferences.getConfiguration().getCarPreferences().getRpmMaxValueProperty());
        rrWheelGauge.maxValueProperty().bind(ApplicationPreferences.getConfiguration().getCarPreferences().getRpmMaxValueProperty());
        lfWheelGauge.setBarColor(Color.web("#ef7f02"));
        rfWheelGauge.setBarColor(Color.web("#ef7f02"));
        lrWheelGauge.setBarColor(Color.web("#ef7f02"));
        rrWheelGauge.setBarColor(Color.web("#ef7f02"));
		
		wheelsRectangle.widthProperty().bind(wheelsPane.widthProperty());
		wheelsLabel.textProperty().bind(I18N.createStringBinding("Vehicle.Wheels"));

		// suspensions
		lfSuspensionGauge.titleProperty().bind(I18N.createStringBinding("Vehicle.Suspensions.FL"));
		rfSuspensionGauge.titleProperty().bind(I18N.createStringBinding("Vehicle.Suspensions.FR"));
		lrSuspensionGauge.titleProperty().bind(I18N.createStringBinding("Vehicle.Suspensions.RL"));
		rrSuspensionGauge.titleProperty().bind(I18N.createStringBinding("Vehicle.Suspensions.RR"));
		lfSuspensionGauge.maxValueProperty().bind(ApplicationPreferences.getConfiguration().getCarPreferences().getSuspensionsMaxValueProperty());
		rfSuspensionGauge.maxValueProperty().bind(ApplicationPreferences.getConfiguration().getCarPreferences().getSuspensionsMaxValueProperty());
		lrSuspensionGauge.maxValueProperty().bind(ApplicationPreferences.getConfiguration().getCarPreferences().getSuspensionsMaxValueProperty());
		rrSuspensionGauge.maxValueProperty().bind(ApplicationPreferences.getConfiguration().getCarPreferences().getSuspensionsMaxValueProperty());
		lfSuspensionGauge.setBarColor(Color.web("#ef7f02"));
		rfSuspensionGauge.setBarColor(Color.web("#ef7f02"));
		lrSuspensionGauge.setBarColor(Color.web("#ef7f02"));
		rrSuspensionGauge.setBarColor(Color.web("#ef7f02"));
		
		suspensionsRectangle.widthProperty().bind(suspensionsPane.widthProperty());
		suspensionsLabel.textProperty().bind(I18N.createStringBinding("Vehicle.Suspensions"));
		
		carImagePane.setMaxWidth(pedalsPane.getHeight() * 1.2);
        
        primaryStage.widthProperty().addListener((__) -> {
        	pedalsPane.setMaxWidth(pedalsPane.getHeight() * 1.2);
        	wheelsPane.setMaxWidth(pedalsPane.getHeight() * 1.2);
	        suspensionsPane.setMaxWidth(pedalsPane.getHeight() * 1.2);
	        accelerometersPane.setMaxWidth(pedalsPane.getHeight() * 1.2);
	        carImagePane.setMaxWidth(pedalsPane.getHeight() * 1.2);
	        userNoticeLabel.setPrefWidth(primaryStage.getWidth());
	    });
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
		userNoticeLabel.setText(message);
		
		Timeline timeline = new Timeline(
				new KeyFrame(Duration.seconds(10), e -> {
			        userNoticeLabel.setText("");
			    })
			);
		
		timeline.setCycleCount(1);
		timeline.play();
	}

	public void showRadioStatus(String message) {
		radioPortNotice.setText(message);
	}
}
