package it.uniroma1.fastcharge.carmonitor.app.controllers.car;

import java.net.URL;
import java.util.ResourceBundle;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import it.uniroma1.fastcharge.carmonitor.app.models.session.Session;
import it.uniroma1.fastcharge.carmonitor.app.views.i18n.I18N;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class CarController implements Initializable {
	
	private Stage primaryStage;
	
	@FXML
	private GridPane pedalsPane, wheelsPane, suspensionsPane, accelerometersPane;
	
	@FXML
	private Label pedalsLabel, wheelsLabel, suspensionsLabel, accelerometersLabel;
	
	//@FXML
	//private ImageView carImageView;
	
	@FXML
	private Rectangle pedalsRectangle, wheelsRectangle, suspensionsRectangle, accelerometersRectangle;
	
	@FXML
	private Gauge acceleratorGauge;
	
	private Gauge brakeGauge, lfWheelGauge, rfWheelGauge, lrWheelGauge, rrWheelGauge,
			lfSuspensionGauge, rfSuspensionGauge, lrSuspensionGauge, rrSuspensionGauge,
			accelerometerXGauge, accelerometerZGauge;

	@Override
	public void initialize(URL location, ResourceBundle resources) {		
		
		@SuppressWarnings("rawtypes")
		GaugeBuilder pedalsGaugeBuilder = GaugeBuilder.create()
											.skinType(Gauge.SkinType.SLIM)
											.barBackgroundColor(Color.rgb(105, 105, 105, 0.2))
											.animated(true)
											.decimals(0)
											.maxValue(100)
											.unit("%");
		
		acceleratorGauge.titleProperty().bind(I18N.createStringBinding("Vehicle.Pedals.APPS"));
		acceleratorGauge.setBarBackgroundColor(Color.rgb(105, 105, 105, 0.2));
		acceleratorGauge.setBarColor(Color.GREEN);
		acceleratorGauge.setDecimals(0);
		
		brakeGauge = pedalsGaugeBuilder.build();
		brakeGauge.setBarColor(Color.RED);
		// brakeGauge.titleProperty().bind(I18N.createStringBinding("Vehicle.Pedals.Brake"));
		
		pedalsRectangle.widthProperty().bind(pedalsPane.widthProperty());
        pedalsLabel.textProperty().bind(I18N.createStringBinding("Vehicle.Pedals"));
 
        //pedalsPane.add(appsGauge, 1, 2);
        pedalsPane.add(brakeGauge, 0, 2);
        
        @SuppressWarnings("rawtypes")
		GaugeBuilder accelerometersGaugeBuilder = GaugeBuilder.create()
													.skinType(Gauge.SkinType.SLIM)
													.barColor(Color.rgb(239, 127, 2))
													.barBackgroundColor(Color.rgb(105, 105, 105, 0.2))
													.animated(true)
													.decimals(0)
													.maxValue(5)
													.unit("m/s\u00B2"); // m/s^2

		accelerometerXGauge = accelerometersGaugeBuilder.build();
		accelerometerZGauge = accelerometersGaugeBuilder.build();

		accelerometersRectangle.widthProperty().bind(accelerometersPane.widthProperty());
		accelerometersLabel.textProperty().bind(I18N.createStringBinding("Vehicle.Accelerometers"));
        
        accelerometersPane.add(accelerometerXGauge, 0, 2);
        accelerometersPane.add(accelerometerZGauge, 1, 2);
        
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
		
		wheelsRectangle.widthProperty().bind(wheelsPane.widthProperty());
		wheelsLabel.textProperty().bind(I18N.createStringBinding("Vehicle.Wheels"));
        
        wheelsPane.add(lfWheelGauge, 0, 2);
        wheelsPane.add(rfWheelGauge, 1, 2);
        wheelsPane.add(lrWheelGauge, 0, 3);
        wheelsPane.add(rrWheelGauge, 1, 3);        
        
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

		suspensionsRectangle.widthProperty().bind(suspensionsPane.widthProperty());
		suspensionsLabel.textProperty().bind(I18N.createStringBinding("Vehicle.Suspensions"));

        suspensionsPane.add(lfSuspensionGauge, 0, 2);
        suspensionsPane.add(rfSuspensionGauge, 1, 2);
        suspensionsPane.add(lrSuspensionGauge, 0, 3);
        suspensionsPane.add(rrSuspensionGauge, 1, 3);
        
        primaryStage.widthProperty().addListener((__) -> {
        	wheelsPane.setMaxWidth(wheelsPane.getHeight() * 1.2);
	        suspensionsPane.setMaxWidth(suspensionsPane.getHeight() * 1.2);
	        accelerometersPane.setMaxWidth(accelerometersPane.getHeight() * 1.2);
	        pedalsPane.setMaxWidth(pedalsPane.getHeight() * 1.2);
	    });
        
        //primaryStage.heightProperty().addListener((__) -> {
        //	carImageView.setFitHeight(wheelsPane.getHeight());
	    //});
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
		
	}

}
