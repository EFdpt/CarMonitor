package it.uniroma1.fastcharge.carmonitor.app.controllers.car.chart;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.gillius.jfxutils.chart.ChartPanManager;
import org.gillius.jfxutils.chart.ChartZoomManager;
import org.gillius.jfxutils.chart.JFXChartUtil;

import it.uniroma1.fastcharge.carmonitor.app.MainApp;
import it.uniroma1.fastcharge.carmonitor.app.models.car.Car;
import it.uniroma1.fastcharge.carmonitor.app.views.i18n.I18N;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import javafx.util.Duration;

class ChartController implements Initializable {
	
	private CarChartController parentController;
	
	private Parent chartLayout;
	
	private File source;
	
	@FXML
	private TreeView<String> carTreeView;
	
	private TreeItem<String> rootItem, pedals, APPS, brake, wheels, wheelFL, wheelFR, wheelRL, wheelRR,
							suspensions, suspensionFL, suspensionFR, suspensionRL, suspensionRR,
							accelerometers, accelerometerX, accelerometerZ;
	
	@FXML
	private LineChart<Number, Number> lineChart;
	
	private XYChart.Series<Number, Number> APPSSeries, brakeSeries, wheelFLSeries, wheelFRSeries,
										wheelRLSeries, wheelRRSeries, suspensionFLSeries, suspensionFRSeries,
										suspensionRLSeries, suspensionRRSeries, accXSeries, accZSeries;
	
	@FXML
	private NumberAxis numberAxisX, numberAxisY;
	
	@FXML
	private Rectangle selectRect;
	
	@FXML
	private AnchorPane chartPane;
	
	private Region plotArea;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rootItem = new TreeItem<String>();
		rootItem.valueProperty().bind(I18N.createStringBinding("ChartView.TreeView.Root"));
        rootItem.setExpanded(true);
        
        pedals = new TreeItem<String>();
        pedals.valueProperty().bind(I18N.createStringBinding("ChartView.TreeView.Pedals"));
        pedals.setExpanded(false);
        rootItem.getChildren().add(pedals);
        
        APPS = new TreeItem<String>();
        APPS.valueProperty().bind(I18N.createStringBinding("ChartView.TreeView.Pedals.APPS"));
        pedals.getChildren().add(APPS);
        
        brake = new TreeItem<String>();
        brake.valueProperty().bind(I18N.createStringBinding("ChartView.TreeView.Pedals.Brake"));
        pedals.getChildren().add(brake);
        
        wheels = new TreeItem<String>();
        wheels.valueProperty().bind(I18N.createStringBinding("ChartView.TreeView.Wheels"));
        wheels.setExpanded(false);
        rootItem.getChildren().add(wheels);
        
        wheelFL = new TreeItem<String>();
        wheelFL.valueProperty().bind(I18N.createStringBinding("ChartView.TreeView.Wheels.FL"));
        wheels.getChildren().add(wheelFL);
        
        wheelFR = new TreeItem<String>();
        wheelFR.valueProperty().bind(I18N.createStringBinding("ChartView.TreeView.Wheels.FR"));
        wheels.getChildren().add(wheelFR);
        
        wheelRL = new TreeItem<String>();
        wheelRL.valueProperty().bind(I18N.createStringBinding("ChartView.TreeView.Wheels.RL"));
        wheels.getChildren().add(wheelRL);
        
        wheelRR = new TreeItem<String>();
        wheelRR.valueProperty().bind(I18N.createStringBinding("ChartView.TreeView.Wheels.RR"));
        wheels.getChildren().add(wheelRR);
        
        suspensions = new TreeItem<String>();
        suspensions.valueProperty().bind(I18N.createStringBinding("ChartView.TreeView.Suspensions"));
        suspensions.setExpanded(false);
        rootItem.getChildren().add(suspensions);
        
        suspensionFL = new TreeItem<String>();
        suspensionFL.valueProperty().bind(I18N.createStringBinding("ChartView.TreeView.Suspensions.FL"));
        suspensions.getChildren().add(suspensionFL);
        
        suspensionFR = new TreeItem<String>();
        suspensionFR.valueProperty().bind(I18N.createStringBinding("ChartView.TreeView.Suspensions.FR"));
        suspensions.getChildren().add(suspensionFR);
        
        suspensionRL = new TreeItem<String>();
        suspensionRL.valueProperty().bind(I18N.createStringBinding("ChartView.TreeView.Suspensions.RL"));
        suspensions.getChildren().add(suspensionRL);
        
        suspensionRR = new TreeItem<String>();
        suspensionRR.valueProperty().bind(I18N.createStringBinding("ChartView.TreeView.Suspensions.RR"));
        suspensions.getChildren().add(suspensionRR);
        
        accelerometers = new TreeItem<String>();
        accelerometers.valueProperty().bind(I18N.createStringBinding("ChartView.TreeView.Accelerometers"));
        rootItem.getChildren().add(accelerometers);
        
        accelerometerX = new TreeItem<String>();
        accelerometerX.valueProperty().bind(I18N.createStringBinding("ChartView.TreeView.Accelerometers.X"));
        accelerometers.getChildren().add(accelerometerX);
        
        accelerometerZ = new TreeItem<String>();
        accelerometerZ.valueProperty().bind(I18N.createStringBinding("ChartView.TreeView.Accelerometers.Z"));
        accelerometers.getChildren().add(accelerometerZ);
        
        carTreeView.setRoot(rootItem);
        
        lineChart.setCreateSymbols(false);
        lineChart.setAnimated(false);
        lineChart.setCursor(Cursor.CROSSHAIR);
        
        carTreeView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {            
                if(mouseEvent.getClickCount() == 2) {
                    if (carTreeView.getSelectionModel().getSelectedItem().equals(APPS)) {
                    	showProperty("Vehicle.Pedals.APPS", APPSSeries, (c) -> {
                    		return (c.getPedals().getTps1() + c.getPedals().getTps2()) / 2;
                    	});
                    } else if (carTreeView.getSelectionModel().getSelectedItem().equals(brake)) {
                    	showProperty("Vehicle.Pedals.Brake", brakeSeries, (c) -> {
                    		return c.getPedals().getBrake();
                    	});
                    } else if (carTreeView.getSelectionModel().getSelectedItem().equals(wheelFL)) {
                    	showProperty("Vehicle.Wheels.FL", wheelFLSeries, (c) -> {
                    		return c.getWheels().getLfWheelRpm();
                    	});
                    } else if (carTreeView.getSelectionModel().getSelectedItem().equals(wheelFR)) {
                    	showProperty("Vehicle.Wheels.FR", wheelFRSeries, (c) -> {
                    		return c.getWheels().getRfWheelRpm();
                    	});
                    } else if (carTreeView.getSelectionModel().getSelectedItem().equals(wheelRL)) {
                    	showProperty("Vehicle.Wheels.RL", wheelRLSeries, (c) -> {
                    		return c.getWheels().getLrWheelRpm();
                    	});
                    } else if (carTreeView.getSelectionModel().getSelectedItem().equals(wheelRR)) {
                    	showProperty("Vehicle.Wheels.RR", wheelRRSeries, (c) -> {
                    		return c.getWheels().getRrWheelRpm();
                    	});
                    } else if (carTreeView.getSelectionModel().getSelectedItem().equals(suspensionFL)) {
                    	showProperty("Vehicle.Suspensions.FL", suspensionFLSeries, (c) -> {
                    		return c.getSuspensions().getLfSuspension();
                    	});
                    } else if (carTreeView.getSelectionModel().getSelectedItem().equals(suspensionFR)) {
                    	showProperty("Vehicle.Suspensions.FR", suspensionFRSeries, (c) -> {
                    		return c.getSuspensions().getRfSuspension();
                    	});
                    } else if (carTreeView.getSelectionModel().getSelectedItem().equals(suspensionRL)) {
                    	showProperty("Vehicle.Suspensions.RL", suspensionRLSeries, (c) -> {
                    		return c.getSuspensions().getLrSuspension();
                    	});
                    } else if (carTreeView.getSelectionModel().getSelectedItem().equals(suspensionRR)) {
                    	showProperty("Vehicle.Suspensions.RR", suspensionRRSeries, (c) -> {
                    		return c.getSuspensions().getRrSuspension();
                    	});
                    } else if (carTreeView.getSelectionModel().getSelectedItem().equals(accelerometerX)) {
                    	showProperty("Vehicle.Accelerometers.X", accXSeries, (c) -> {
                    		return c.getAccelerometers().getAccelerometerX();
                    	});
                    } else if (carTreeView.getSelectionModel().getSelectedItem().equals(accelerometerZ)) {
                    	showProperty("Vehicle.Accelerometers.Z", accZSeries, (c) -> {
                    		return c.getAccelerometers().getAccelerometerZ();
                    	});
                    }
                }
            }
        });
		
        ChartZoomManager zoomManager = new ChartZoomManager(chartPane, selectRect, lineChart);
        ChartPanManager panManager = new ChartPanManager(lineChart);
        
        panManager.setMouseFilter( new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent mouseEvent ) {
				if (mouseEvent.getButton() != MouseButton.MIDDLE)
					mouseEvent.consume();
			}
        } );
        
        zoomManager.start();
        panManager.start();
        JFXChartUtil.addDoublePrimaryClickAutoRangeHandler(lineChart);
	}
	
	public ChartController(CarChartController parentController) {
		this.parentController = parentController;
		
		APPSSeries = new Series<Number, Number>();
        brakeSeries = new Series<Number, Number>();
        wheelFLSeries = new Series<Number, Number>();
        wheelFRSeries = new Series<Number, Number>();
        wheelRLSeries = new Series<Number, Number>();
        wheelRRSeries = new Series<Number, Number>();
        suspensionFLSeries = new Series<Number, Number>();
        suspensionFRSeries = new Series<Number, Number>();
        suspensionRLSeries = new Series<Number, Number>();
        suspensionRRSeries = new Series<Number, Number>();
        accXSeries = new Series<Number, Number>();
        accZSeries = new Series<Number, Number>();
        
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/it/uniroma1/fastcharge/carmonitor/app/views/car/chart/ChartView.fxml"));
    	loader.setController(this);
    	try {
			this.chartLayout = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	plotArea = (Region) lineChart.lookup("Region");
	}
    
	public Parent getChartLayout() {
		return chartLayout;
	}
	
	public void setChartSource(File source) {
		this.source = source;
		if (!lineChart.getData().isEmpty())
			lineChart.getData().remove(0, lineChart.getData().size());
	}
	
	private void showProperty(String textBinding, Series<Number, Number> serie, Callback<Car, Number> callback) {
		if (source != null) {
			Car car;
			FileInputStream fileInputStream = null;
			ObjectInputStream inputStream = null;
			
			for (Series<Number, Number> s : lineChart.getData()) {
				if (s.getName().equals(I18N.get(textBinding))) {
					lineChart.getData().remove(s);
					return;
				}
			}
			
			try {
				fileInputStream = new FileInputStream(source);
				inputStream = new ObjectInputStream(fileInputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			serie = new Series<Number, Number>();
			serie.nameProperty().bind(I18N.createStringBinding(textBinding));
			
			try {
				int i = 1;
				while ((car = (Car) inputStream.readObject()) != null) {
					Data<Number, Number> data = new Data<>(i, callback.call(car));
					data.setNode(new HoveredThresholdNode(callback.call(car).toString()));
					//Tooltip t = new Tooltip(callback.call(car).toString());
					//hackTooltipStartTiming(t);
			        //Tooltip.install(data.getNode(), t);
					serie.getData().add(data);
					i++;
				}			
			} catch (EOFException e1) {
				
			} catch (ClassNotFoundException | IOException e2) {
				e2.printStackTrace();
			} finally {
				try {
					inputStream.close();
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			
			
			lineChart.getData().add(serie);
		}
	}
	
	
	/** a node which displays a value on hover, but is otherwise empty */
	class HoveredThresholdNode extends StackPane {
		HoveredThresholdNode(String value) {
			setPrefSize(10, 10);
			getStyleClass().add("chart-data-display");
			setCache(true);
			final Rectangle r = new Rectangle(1, 1080);
			
			r.heightProperty().bind(lineChart.heightProperty().multiply(2.0));

			final Label label = createDataThresholdLabel(value);

			setOnMouseEntered(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					getChildren().setAll(r, label);
					setCursor(Cursor.NONE);
					toFront();
					getStyleClass().add("hover");
					Point2D mouseSceneCoords = new Point2D(mouseEvent.getSceneX(), mouseEvent.getSceneY());
				    double y = numberAxisY.sceneToLocal(mouseSceneCoords).getY();
				    double valY = plotArea.getHeight() - y;
				    
				   if (valY < 60)
				    	label.setTranslateY(-35);
				}
			});
			setOnMouseExited(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					getChildren().clear();
					setCursor(Cursor.CROSSHAIR);
					getStyleClass().remove("hover");
				}
			});
		}

		private Label createDataThresholdLabel(String value) {
			final Label label = new Label(value);
			label.setCache(true);
			label.setTranslateY(35);
			label.applyCss();
			
			label.getStyleClass().add("chart-data-label");
			
			label.setMinSize(Label.USE_PREF_SIZE, USE_PREF_SIZE);
			label.setPrefHeight(40.0);
			return label;
		}

	}
}
