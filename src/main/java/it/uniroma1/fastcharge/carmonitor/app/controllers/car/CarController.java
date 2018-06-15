package it.uniroma1.fastcharge.carmonitor.app.controllers.car;

import java.net.URL;
import java.util.ResourceBundle;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Gauge.KnobType;
import eu.hansolo.medusa.Gauge.LedType;
import eu.hansolo.medusa.Gauge.NeedleShape;
import eu.hansolo.medusa.Gauge.NeedleSize;
import eu.hansolo.medusa.Gauge.ScaleDirection;
import eu.hansolo.medusa.Gauge.SkinType;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.LcdDesign;
import eu.hansolo.medusa.LcdFont;
import eu.hansolo.medusa.TickLabelLocation;
import eu.hansolo.medusa.TickLabelOrientation;
import eu.hansolo.medusa.TickMarkType;
import eu.hansolo.medusa.skins.ModernSkin;
import eu.hansolo.medusa.skins.SlimSkin;
import it.uniroma1.fastcharge.carmonitor.app.models.car.Suspensions;
import it.uniroma1.fastcharge.carmonitor.app.models.car.Wheels;
import it.uniroma1.fastcharge.carmonitor.app.models.session.Session;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.adapter.JavaBeanDoublePropertyBuilder;
import javafx.beans.property.adapter.JavaBeanObjectPropertyBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class CarController implements Initializable {
	
	private Stage primaryStage;
	
	@FXML
	private GridPane imagePane, wheelsGrid, suspensionsGrid;
	
	@FXML
	private ImageView carImageView;
	
	@FXML
	private VBox pedalsPane, wheelsPane, suspensionsPane, accelerometersPane;
	
	private Gauge lfWheelGauge, rfWheelGauge, lrWheelGauge, rrWheelGauge,
			lfSuspensionGauge, rfSuspensionGauge, lrSuspensionGauge, rrSuspensionGauge;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		carImageView.fitHeightProperty().bind(imagePane.heightProperty().multiply(0.65));
		
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
		
		Rectangle bar = new Rectangle(600, 3);
        bar.setArcWidth(6);
        bar.setArcHeight(6);
        bar.setFill(Color.rgb(239, 127, 2));
 
        Label label = new Label("WHEELS");
        label.setTextFill(Color.rgb(239, 127, 2));
        label.setAlignment(Pos.CENTER);
        label.setPadding(new Insets(0, 0, 10, 0));
 
        VBox vBox = new VBox(bar, label);
        vBox.setSpacing(3);
        vBox.setAlignment(Pos.CENTER);
        wheelsGrid.add(vBox, 0, 0);
        GridPane.setColumnSpan(vBox, 2);
        
        wheelsGrid.add(lfWheelGauge, 0, 1);
        wheelsGrid.add(rfWheelGauge, 1, 1);
        wheelsGrid.add(lrWheelGauge, 0, 2);
        wheelsGrid.add(rrWheelGauge, 1, 2);
        
        
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

		Rectangle suspensionBar = new Rectangle(600, 3);
		suspensionBar.setArcWidth(6);
		suspensionBar.setArcHeight(6);
		suspensionBar.setFill(Color.rgb(239, 127, 2));

		Label suspensionsLabel = new Label("SUSPENSIONS");
		suspensionsLabel.setTextFill(Color.rgb(239, 127, 2));
		suspensionsLabel.setAlignment(Pos.CENTER);
		suspensionsLabel.setPadding(new Insets(0, 0, 10, 0));

		VBox suspensionsVBox = new VBox(suspensionBar, suspensionsLabel);
		suspensionsVBox.setSpacing(3);
		suspensionsVBox.setAlignment(Pos.CENTER);
		suspensionsGrid.add(suspensionsVBox, 0, 0);
		GridPane.setColumnSpan(suspensionsVBox, 2);

		suspensionsGrid.add(lfSuspensionGauge, 0, 1);
		suspensionsGrid.add(rfSuspensionGauge, 1, 1);
		suspensionsGrid.add(lrSuspensionGauge, 0, 2);
		suspensionsGrid.add(rrSuspensionGauge, 1, 2);
		
		Session.getInstance();
		bindView();
	}
	
	public CarController(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	@SuppressWarnings("unchecked")
	public void bindView() {
		try {
			ObjectProperty<Number> lfWheelProp = new JavaBeanObjectPropertyBuilder<Number>()
				        .bean(Session.getDefaultInstance().getCar().getWheels())
				        .name(Wheels.lfWheelPropertyName())
				        .build();
			lfWheelProp.addListener((obs, oldValue, newValue) -> {
				lfWheelGauge.setValue(newValue.doubleValue());
			});
			
			ObjectProperty<Number> rfWheelProp = new JavaBeanObjectPropertyBuilder<Number>()
				        .bean(Session.getDefaultInstance().getCar().getWheels())
				        .name(Wheels.rfWheelPropertyName())
				        .build();
			rfWheelProp.addListener((obs, oldValue, newValue) -> {
				rfWheelGauge.setValue(newValue.doubleValue());
			});
			
			ObjectProperty<Number> lrWheelProp = new JavaBeanObjectPropertyBuilder<Number>()
				        .bean(Session.getDefaultInstance().getCar().getWheels())
				        .name(Wheels.lrWheelPropertyName())
				        .build();
			lrWheelProp.addListener((obs, oldValue, newValue) -> {
				lrWheelGauge.setValue(newValue.doubleValue());
			});
			
			ObjectProperty<Number> rrWheelProp = new JavaBeanObjectPropertyBuilder<Number>()
				        .bean(Session.getDefaultInstance().getCar().getWheels())
				        .name(Wheels.rrWheelPropertyName())
				        .build();
			rrWheelProp.addListener((obs, oldValue, newValue) -> {
				rrWheelGauge.setValue(newValue.doubleValue());
			});
			
			ObjectProperty<Number> lfSuspensionProp = new JavaBeanObjectPropertyBuilder<Number>()
					.bean(Session.getDefaultInstance().getCar().getSuspensions()).name(Suspensions.lfSuspensionPropertyName()).build();
			lfSuspensionProp.addListener((obs, oldValue, newValue) -> {
				lfSuspensionGauge.setValue(newValue.doubleValue());
			});

			ObjectProperty<Number> rfSuspensionProp = new JavaBeanObjectPropertyBuilder<Number>()
					.bean(Session.getDefaultInstance().getCar().getSuspensions()).name(Suspensions.rfSuspensionPropertyName()).build();
			rfSuspensionProp.addListener((obs, oldValue, newValue) -> {
				rfSuspensionGauge.setValue(newValue.doubleValue());
			});
			
			ObjectProperty<Number> lrSuspensionProp = new JavaBeanObjectPropertyBuilder<Number>()
					.bean(Session.getDefaultInstance().getCar().getSuspensions()).name(Suspensions.lrSuspensionPropertyName()).build();
			lrSuspensionProp.addListener((obs, oldValue, newValue) -> {
				lrSuspensionGauge.setValue(newValue.doubleValue());
			});
			
			ObjectProperty<Number> rrSuspensionProp = new JavaBeanObjectPropertyBuilder<Number>()
					.bean(Session.getDefaultInstance().getCar().getSuspensions()).name(Suspensions.rrSuspensionPropertyName()).build();
			rrSuspensionProp.addListener((obs, oldValue, newValue) -> {
				rrSuspensionGauge.setValue(newValue.doubleValue());
			});
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		
		Session.getDefaultInstance().getCar().getWheels().setLfWheelRpm((short) 2202);
		Session.getDefaultInstance().getCar().getWheels().setRfWheelRpm((short) 2204);
		Session.getDefaultInstance().getCar().getWheels().setLrWheelRpm((short) 2206);
		Session.getDefaultInstance().getCar().getWheels().setRrWheelRpm((short) 2208);
		
		Session.getDefaultInstance().getCar().getSuspensions().setLfSuspension((byte) 22);
		Session.getDefaultInstance().getCar().getSuspensions().setRfSuspension((byte) 27);
		Session.getDefaultInstance().getCar().getSuspensions().setLrSuspension((byte) 21);
		Session.getDefaultInstance().getCar().getSuspensions().setRrSuspension((byte) 29);
	}
	
	public void unbindView() {
		
	}

}
