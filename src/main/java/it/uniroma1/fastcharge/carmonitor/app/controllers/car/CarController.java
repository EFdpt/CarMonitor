package it.uniroma1.fastcharge.carmonitor.app.controllers.car;

import java.net.URL;
import java.util.ResourceBundle;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import it.uniroma1.fastcharge.carmonitor.app.models.car.Pedals;
import it.uniroma1.fastcharge.carmonitor.app.models.car.Suspensions;
import it.uniroma1.fastcharge.carmonitor.app.models.car.Wheels;
import it.uniroma1.fastcharge.carmonitor.app.models.session.Session;
import it.uniroma1.fastcharge.carmonitor.app.views.i18n.I18N;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.adapter.JavaBeanObjectPropertyBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class CarController implements Initializable {
	
	private Stage primaryStage;
	/*
	@FXML
	private GridPane imagePane, pedalsGrid, wheelsGrid, suspensionsGrid, accelerometersGrid;
	*/
	
	@FXML
	private ImageView carImageView;
	
	@FXML
	private VBox pedalsBox, wheelsBox, suspensionsBox, accelerometersBox/*, dataPane*/;
	
	private Gauge appsGauge, brakeGauge, lfWheelGauge, rfWheelGauge, lrWheelGauge, rrWheelGauge,
			lfSuspensionGauge, rfSuspensionGauge, lrSuspensionGauge, rrSuspensionGauge,
			accelerometerXGauge, accelerometerZGauge;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/*carImageView.fitHeightProperty().bind(imagePane.heightProperty().multiply(0.65));
		imagePane.heightProperty().addListener((observable, oldValue, newValue) -> {
			dataPane.setMaxHeight(imagePane.getHeight() * 0.65);
		});*/
		
		
		@SuppressWarnings("rawtypes")
		GaugeBuilder appsGaugeBuilder = GaugeBuilder.create()
											.skinType(Gauge.SkinType.SLIM)
											.barColor(Color.rgb(239, 127, 2))
											.barBackgroundColor(Color.rgb(105, 105, 105, 0.2))
											.animated(true)
											.decimals(0)
											.maxValue(100)
											.unit("%");
		
		appsGauge = appsGaugeBuilder.build();
		appsGauge.setBarColor(Color.GREEN);
		// appsGauge.setTitle("acc");//.titleProperty().bind(I18N.createStringBinding("Vehicle.Pedals.APPS"));
		
		brakeGauge = appsGaugeBuilder.build();
		brakeGauge.setBarColor(Color.RED);
		// brakeGauge.titleProperty().bind(I18N.createStringBinding("Vehicle.Pedals.Brake"));
		
		Rectangle pedalsBar = new Rectangle(600, 3);
		pedalsBar.setArcWidth(6);
		pedalsBar.setArcHeight(6);
		pedalsBar.setFill(Color.rgb(239, 127, 2));
 
        Label pedalsLabel = new Label();
        pedalsLabel.textProperty().bind(I18N.createStringBinding("Vehicle.Pedals"));
        pedalsLabel.setTextFill(Color.rgb(239, 127, 2));
        pedalsLabel.setPadding(new Insets(0, 0, 10, 0));
 
        pedalsBox.getChildren().add(pedalsBar);
        pedalsBox.getChildren().add(pedalsLabel);
        pedalsBox.setSpacing(3);
        
        GridPane pedalsPane = new GridPane();
        pedalsPane.add(appsGauge, 1, 0);
        pedalsPane.add(brakeGauge, 0, 0);
        pedalsBox.getChildren().add(pedalsPane);
        
        @SuppressWarnings("rawtypes")
		GaugeBuilder accelerometersGaugeBuilder = GaugeBuilder.create()
													.skinType(Gauge.SkinType.SLIM)
													.barColor(Color.rgb(239, 127, 2))
													.barBackgroundColor(Color.rgb(105, 105, 105, 0.2))
													.animated(true)
													.decimals(0)
													.maxValue(5)
													.unit("m/s^2");

		accelerometerXGauge = accelerometersGaugeBuilder.build();
		accelerometerZGauge = accelerometersGaugeBuilder.build();

		Rectangle accelerometersBar = new Rectangle(600, 3);
		accelerometersBar.setArcWidth(6);
		accelerometersBar.setArcHeight(6);
		accelerometersBar.setFill(Color.rgb(239, 127, 2));

		Label accelerometersLabel = new Label();
		accelerometersLabel.textProperty().bind(I18N.createStringBinding("Vehicle.Accelerometers"));
		accelerometersLabel.setTextFill(Color.rgb(239, 127, 2));
		accelerometersLabel.setPadding(new Insets(0, 0, 10, 0));

		accelerometersBox.getChildren().add(accelerometersBar);
		accelerometersBox.getChildren().add(accelerometersLabel);
		accelerometersBox.setSpacing(3);
        
		GridPane accelerometersPane = new GridPane();
        accelerometersPane.add(accelerometerXGauge, 0, 0);
        accelerometersPane.add(accelerometerZGauge, 1, 0);
        accelerometersBox.getChildren().add(accelerometersPane);
        
        @SuppressWarnings("rawtypes")
		GaugeBuilder wheelsGaugeBuilder = GaugeBuilder.create()
											.skinType(Gauge.SkinType.SLIM)
											.barColor(Color.rgb(239, 127, 2))
											.barBackgroundColor(Color.rgb(105, 105, 105, 0.2))
											.animated(true)
											.decimals(0)
											.maxValue(2500)
											.unit("RPM");
		
		lfWheelGauge = wheelsGaugeBuilder.build();
		rfWheelGauge = wheelsGaugeBuilder.build();
		lrWheelGauge = wheelsGaugeBuilder.build();
		rrWheelGauge = wheelsGaugeBuilder.build();
		
		Rectangle wheelsBar = new Rectangle(600, 3);
		wheelsBar.setArcWidth(6);
		wheelsBar.setArcHeight(6);
		wheelsBar.setFill(Color.rgb(239, 127, 2));
 
        Label wheelsLabel = new Label();
        wheelsLabel.textProperty().bind(I18N.createStringBinding("Vehicle.Wheels"));
        
        wheelsLabel.setTextFill(Color.rgb(239, 127, 2));
        wheelsLabel.setPadding(new Insets(0, 0, 10, 0));
 
        wheelsBox.getChildren().add(wheelsBar);
        wheelsBox.getChildren().add(wheelsLabel);
        wheelsBox.setSpacing(3);
        
        GridPane wheelsPane = new GridPane();
        wheelsPane.add(lfWheelGauge, 0, 0);
        wheelsPane.add(rfWheelGauge, 1, 0);
        wheelsPane.add(lrWheelGauge, 0, 1);
        wheelsPane.add(rrWheelGauge, 1, 1);
        wheelsBox.getChildren().add(wheelsPane);
        
        // SUSPENSIONS
        @SuppressWarnings("rawtypes")
		GaugeBuilder suspensionsGaugeBuilder = GaugeBuilder.create()
													.skinType(Gauge.SkinType.SLIM)
													.barColor(Color.rgb(239, 127, 2))
													.barBackgroundColor(Color.rgb(105, 105, 105, 0.2))
													.animated(true)
													.decimals(0)
													.maxValue(75)
													.unit("mm");

		lfSuspensionGauge = suspensionsGaugeBuilder.build();
		rfSuspensionGauge = suspensionsGaugeBuilder.build();
		lrSuspensionGauge = suspensionsGaugeBuilder.build();
		rrSuspensionGauge = suspensionsGaugeBuilder.build();

		Rectangle suspensionsBar = new Rectangle(600, 3);
		suspensionsBar.setArcWidth(6);
		suspensionsBar.setArcHeight(6);
		suspensionsBar.setFill(Color.rgb(239, 127, 2));

		Label suspensionsLabel = new Label();
		suspensionsLabel.textProperty().bind(I18N.createStringBinding("Vehicle.Suspensions"));
		suspensionsLabel.setTextFill(Color.rgb(239, 127, 2));
		suspensionsLabel.setPadding(new Insets(0, 0, 10, 0));

		suspensionsBox.getChildren().add(suspensionsBar);
		suspensionsBox.getChildren().add(suspensionsLabel);
		suspensionsBox.setSpacing(3);
        
        GridPane suspensionsPane = new GridPane();
        suspensionsPane.add(lfSuspensionGauge, 0, 0);
        suspensionsPane.add(rfSuspensionGauge, 1, 0);
        suspensionsPane.add(lrSuspensionGauge, 0, 1);
        suspensionsPane.add(rrSuspensionGauge, 1, 1);
        suspensionsBox.getChildren().add(suspensionsPane);
	}
	
	public CarController(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	public void updateView() {
		appsGauge.setValue(Session.getDefaultInstance().getCar().getPedals().getTps1());
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
		
	}

}
