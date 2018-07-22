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
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

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
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.CheckBox;
import javafx.scene.control.SplitPane;
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
	private Menu	fileMenu, chartMenu;
	
	@FXML
	private MenuItem closeMenuItem, viewPrevSessionMenuItem, realChartMenuItem;
	
	@FXML
	private CheckBox showGraphOne, showGraphTwo, showGraphThree, showGraphFour;
	
	@FXML
	private SplitPane chartsSplitPaneContainer;
	
	private File prevSessionFile = null;
	
	private ConcurrentHashMap<Integer, ChartController> charts;

	public CarChartController(Stage stage) {
		this.stage = stage;
		
		this.charts = new ConcurrentHashMap<Integer, ChartController>(4);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {        
        fileMenu.textProperty().bind(I18N.createStringBinding("Menu.File"));
        chartMenu.textProperty().bind(I18N.createStringBinding("ChartView.Menu.Chart"));
        
        closeMenuItem.textProperty().bind(I18N.createStringBinding("Menu.File.Close"));
        
        viewPrevSessionMenuItem.textProperty().bind(I18N.createStringBinding("ChartView.Menu.Chart.ShowPrev"));
        
        realChartMenuItem.setText("Real-Time");
        
        showGraphOne.setSelected(true);
        showGraphTwo.setSelected(true);
        showGraphThree.setSelected(true);
        showGraphFour.setSelected(true);
        
        showGraph(0);
        showGraph(1);
        showGraph(2);
        showGraph(3);
        
        // set actions
        closeMenuItem.setOnAction(this::handleCloseWindow);
        viewPrevSessionMenuItem.setOnAction(this::handleShowPrevSession);
        
        showGraphOne.setOnAction((event) -> {
        	if (((CheckBox) event.getSource()).isSelected())
        		showGraph(0);
        	else
        		hideGraph(0);
        });
        showGraphTwo.setOnAction((event) -> {
        	if (((CheckBox) event.getSource()).isSelected())
        		showGraph(1);
        	else
        		hideGraph(1);
        });
        showGraphThree.setOnAction((event) -> {
        	if (((CheckBox) event.getSource()).isSelected())
        		showGraph(2);
        	else
        		hideGraph(2);
        });
        showGraphFour.setOnAction((event) -> {
        	if (((CheckBox) event.getSource()).isSelected())
        		showGraph(3);
        	else
        		hideGraph(3);
        });
	}
	
	public void handleCloseWindow(ActionEvent e) {
		stage.close();
	}

	public void handleShowPrevSession(ActionEvent e) {
		
		FileChooser logChooser = new FileChooser();
		logChooser.setTitle("Open log file");
		logChooser.setInitialDirectory(new File(ApplicationPreferences.getConfiguration().getLogDir()));
		
		prevSessionFile = logChooser.showOpenDialog(stage);
		
		if (prevSessionFile != null)
			charts.values().forEach((c) -> c.setChartSource(prevSessionFile));
	}
	
	private void showGraph(int index) {
		ChartController chartController = new ChartController(this); // create and initialize graph
		Parent chartLayout = chartController.getChartLayout();
		this.charts.put(index, chartController);
		
		if (prevSessionFile != null)
			chartController.setChartSource(prevSessionFile);
		
		chartsSplitPaneContainer.getItems().add(index, chartLayout);
	}
	
	private void hideGraph(int index) {
		charts.remove(index);
		chartsSplitPaneContainer.getItems().remove(index);
	}
}
