package it.uniroma1.fastcharge.carmonitor.app.controllers.car.chart;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

import it.uniroma1.fastcharge.carmonitor.app.models.activities.atomic.ExportCsvTask;
import it.uniroma1.fastcharge.carmonitor.app.models.activities.framework.TaskExecutor;
import it.uniroma1.fastcharge.carmonitor.app.models.car.Car;
import it.uniroma1.fastcharge.carmonitor.app.views.i18n.I18N;
import it.uniroma1.fastcharge.carmonitor.config.ApplicationPreferences;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CarChartController implements Initializable {
	
	private Stage stage;
	
	@FXML
	private AnchorPane chartMainPane;
	
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
	private Menu	fileMenu, chartMenu;
	
	@FXML
	private MenuItem closeMenuItem, viewPrevSessionMenuItem;
	
	private File prevSessionFile = null;

	public CarChartController(Stage stage) {
		this.stage = stage;
		
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
	}

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
        
        fileMenu.textProperty().bind(I18N.createStringBinding("Menu.File"));
        chartMenu.textProperty().bind(I18N.createStringBinding("ChartView.Menu.Chart"));
        
        closeMenuItem.textProperty().bind(I18N.createStringBinding("Menu.File.Close"));
        
        viewPrevSessionMenuItem.textProperty().bind(I18N.createStringBinding("ChartView.Menu.Chart.ShowPrev"));
        
        lineChart.setCreateSymbols(false);
        lineChart.setAnimated(false);
        
        // set actions
        closeMenuItem.setOnAction(this::handleCloseWindow);
        viewPrevSessionMenuItem.setOnAction(this::handleShowPrevSession);
        
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
	}
	
	public void handleCloseWindow(ActionEvent e) {
		stage.close();
	}
	
	private void showProperty(String textBinding, Series<Number, Number> serie, Callback<Car, Number> callback) {
		if (prevSessionFile != null) {
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
				fileInputStream = new FileInputStream(prevSessionFile);
				inputStream = new ObjectInputStream(fileInputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			serie = new Series<Number, Number>();
			serie.nameProperty().bind(I18N.createStringBinding(textBinding));
			lineChart.getData().add(serie);
			try {
				int i = 1;
				while ((car = (Car) inputStream.readObject()) != null) {
					serie.getData().add(new Data<Number, Number>(i, callback.call(car)));
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
		}
	}

	public void handleShowPrevSession(ActionEvent e) {
		
		FileChooser logChooser = new FileChooser();
		logChooser.setTitle("Open log file");
		logChooser.setInitialDirectory(new File(ApplicationPreferences.getConfiguration().getLogDir()));
		
		prevSessionFile = logChooser.showOpenDialog(stage);
		
		if (prevSessionFile != null) {
			if (!lineChart.getData().isEmpty())
					lineChart.getData().remove(0, lineChart.getData().size());

        }
	}
}
