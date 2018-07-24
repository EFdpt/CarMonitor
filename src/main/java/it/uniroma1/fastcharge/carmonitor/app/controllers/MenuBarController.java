package it.uniroma1.fastcharge.carmonitor.app.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDecorator;

import it.uniroma1.fastcharge.carmonitor.app.MainApp;
import it.uniroma1.fastcharge.carmonitor.app.controllers.aboutus.AboutUsController;
import it.uniroma1.fastcharge.carmonitor.app.controllers.car.chart.CarChartController;
import it.uniroma1.fastcharge.carmonitor.app.controllers.preferences.PreferencesController;
import it.uniroma1.fastcharge.carmonitor.app.models.activities.atomic.ExportCsvTask;
import it.uniroma1.fastcharge.carmonitor.app.models.activities.atomic.RadioConnectTask;
import it.uniroma1.fastcharge.carmonitor.app.models.activities.atomic.RadioDisconnectTask;
import it.uniroma1.fastcharge.carmonitor.app.models.activities.atomic.SetRadioTask;
import it.uniroma1.fastcharge.carmonitor.app.models.activities.framework.TaskExecutor;
import it.uniroma1.fastcharge.carmonitor.app.models.radio.SerialRadio;
import it.uniroma1.fastcharge.carmonitor.app.views.i18n.I18N;
import it.uniroma1.fastcharge.carmonitor.config.ApplicationPreferences;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MenuBarController implements Initializable {
	
	private Stage primaryStage;
	private MainController rootController;
	
	@FXML
	private Menu fileMenu, exportMenu, radioMenu, serialPortMenu, windowMenu, helpMenu;
	
	@FXML
	private MenuItem exportCsvMenuItem, connectMenuItem, disconnectMenuItem, closeMenuItem, newWindowMenuItem, preferencesMenuItem, aboutMenuItem;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fileMenu.textProperty().bind(I18N.createStringBinding("Menu.File"));
		exportMenu.textProperty().bind(I18N.createStringBinding("Menu.File.Export"));
		exportCsvMenuItem.textProperty().bind(I18N.createStringBinding("Menu.File.Export.Prev"));
		closeMenuItem.textProperty().bind(I18N.createStringBinding("Menu.File.Close"));	
		radioMenu.textProperty().bind(I18N.createStringBinding("Menu.Radio"));
		serialPortMenu.textProperty().bind(I18N.createStringBinding("Menu.Radio.Port"));
		connectMenuItem.textProperty().bind(I18N.createStringBinding("Menu.Radio.Connect"));
		disconnectMenuItem.textProperty().bind(I18N.createStringBinding("Menu.Radio.Disconnect"));
		windowMenu.textProperty().bind(I18N.createStringBinding("Menu.Window"));
		newWindowMenuItem.textProperty().bind(I18N.createStringBinding("Menu.Window.New"));
		preferencesMenuItem.textProperty().bind(I18N.createStringBinding("Menu.Window.Preferences"));
		helpMenu.textProperty().bind(I18N.createStringBinding("Menu.Help"));
		aboutMenuItem.textProperty().bind(I18N.createStringBinding("Menu.Help.About"));
		
		// bind actions
		connectMenuItem.setOnAction(this::handleSerialRadioConnect);
		disconnectMenuItem.setOnAction(this::handleSerialRadioDisconnect);
		newWindowMenuItem.setOnAction(this::handleNewWindow);
		preferencesMenuItem.setOnAction(this::handleShowPreferences);
		radioMenu.showingProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> observable,
		            Boolean oldValue, Boolean newValue) {

		        if (newValue) {
		        	handleGetSerialPorts();
		        }
		    }
		});
		
		closeMenuItem.setOnAction(this::handleCloseWindow);
		exportCsvMenuItem.setOnAction(this::exportCsvPreviousSession);
		aboutMenuItem.setOnAction(this::handleShowAboutUs);
	}
	
	public MenuBarController(MainController controller, Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.rootController = controller;
	}
	
	public void handleNewWindow(ActionEvent event) {
		Parent root;
		Stage stage = new Stage();
		
		stage.titleProperty().bind(I18N.createStringBinding("ChartView.StageTitle"));
		
		stage.setResizable(true);
        stage.setMaximized(true);
        stage.setMinHeight(300.0);
        stage.setMinWidth(800.0);
        
        stage.getIcons().add(new Image(MainApp.class.getResourceAsStream("/it/uniroma1/fastcharge/carmonitor/app/assets/resources/images/fast_charge_icon.png")));
		
        try {
        	CarChartController carChartController = new CarChartController(stage);
        	FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/it/uniroma1/fastcharge/carmonitor/app/views/car/chart/ChartsGeneralView.fxml"));
        	loader.setController(carChartController);
        	root = loader.load();
        	
            JFXDecorator decorator = new JFXDecorator(stage, root);
            
            Scene scene = new Scene(decorator);
            scene.getStylesheets().add(MainApp.class.getResource("/it/uniroma1/fastcharge/carmonitor/app/assets/stylesheets/application.css").toExternalForm());
            scene.getStylesheets().add(MainApp.class.getResource("/it/uniroma1/fastcharge/carmonitor/app/assets/stylesheets/chart/chartGeneralView.css").toExternalForm());
            scene.getStylesheets().add(MainApp.class.getResource("/it/uniroma1/fastcharge/carmonitor/app/assets/stylesheets/chart/chart.css").toExternalForm());
            stage.setScene(scene);
            
            stage.centerOnScreen();

            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	private void handleShowAboutUs(ActionEvent event) {
		Parent root;
		Scene scene;
		
		Stage stage = new Stage();
		stage.titleProperty().bind(I18N.createStringBinding("AboutUs.StageTitle"));
		stage.maximizedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue)
                stage.setMaximized(false);
        });
		
		stage.getIcons().add(new Image(MainApp.class.getResourceAsStream("/it/uniroma1/fastcharge/carmonitor/app/assets/resources/images/fast_charge_icon.png")));
		
        try {
        	AboutUsController aboutUsController = new AboutUsController(primaryStage, stage);
        	FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/it/uniroma1/fastcharge/carmonitor/app/views/aboutus/AboutUsView.fxml"));
    		loader.setController(aboutUsController);
    		root = loader.load();
        	
            JFXDecorator decorator = new JFXDecorator(stage, root);
            
            scene = new Scene(decorator, 400, 300);
            scene.getStylesheets().add(MainApp.class.getResource("/it/uniroma1/fastcharge/carmonitor/app/assets/stylesheets/application.css").toExternalForm());
            scene.getStylesheets().add(MainApp.class.getResource("/it/uniroma1/fastcharge/carmonitor/app/assets/stylesheets/aboutus/aboutus.css").toExternalForm());
            stage.setScene(scene);
            
            stage.setResizable(false);
            
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            
            stage.centerOnScreen();
            stage.setOnHidden(e -> aboutUsController.shutdown());

            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	private void handleGetSerialPorts() {
		
		// add serialPort menuItem if not already present
		SerialRadio.getCommPorts().forEach((port) -> {
			MenuItem i = serialPortMenu.getItems().stream()
										.filter(item -> item.getText().equals(port.getSystemPortName()))
										.findFirst()
										.orElse(null);
			if (i == null) {
				MenuItem item = new MenuItem(port.getSystemPortName());
				item.setOnAction(this::handleSetSerialPort);
				serialPortMenu.getItems().add(item);
				if (serialPortMenu.getItems().get(0) != null)
					serialPortMenu.getItems().get(0).getStyleClass().add("menu-item-first");
			}
		});
		
		// remove serialPort menuItem if disconnected
		serialPortMenu.getItems().parallelStream().forEach(item -> {
			if (SerialRadio.getCommPorts().stream()
						.filter(port -> port.getSystemPortName().equals(item.getText()))
						.findFirst()
						.orElse(null) == null)
				serialPortMenu.getItems().remove(item);
			if (serialPortMenu.getItems().get(0) != null)
				serialPortMenu.getItems().get(0).getStyleClass().add("menu-item-first");
		});
	}
	
	private void handleSetSerialPort(ActionEvent event) {
		MenuItem i = (MenuItem) event.getSource();
		SerialRadio radio = SerialRadio.getCommPorts().stream()
						.filter(p -> p.getSystemPortName().equals(i.getText()))
						.findFirst()
						.orElse(null);
		if (radio == null)
			return;
		
		SetRadioTask task = new SetRadioTask(radio);
		TaskExecutor.getInstance().perform(task);
		rootController.showUserNotice("Notice.PortSetted");
	}
	
	private void handleSerialRadioConnect(ActionEvent e) {
		RadioConnectTask connect = new RadioConnectTask();
		TaskExecutor.getInstance().perform(connect);
		if (!connect.isConnected())
			return;
		serialPortMenu.setDisable(true);
		connectMenuItem.setDisable(true);
		rootController.connectView();
		rootController.showUserNotice("Notice.RadioConnected");
	}
	
	private void handleSerialRadioDisconnect(ActionEvent e) {
		RadioDisconnectTask disconnect = new RadioDisconnectTask();
		TaskExecutor.getInstance().perform(disconnect);
		if (!disconnect.isDisconnected())
			return;
		serialPortMenu.setDisable(false);
		connectMenuItem.setDisable(false);
		rootController.disconnectView();
		rootController.showUserNotice("Notice.RadioDisconnected");
	}
	
	private void handleShowPreferences(ActionEvent event) {
		Parent root;
		Scene scene;
		
		Stage stage = new Stage();
		stage.titleProperty().bind(I18N.createStringBinding("Preferences.StageTitle"));
		stage.maximizedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue)
                stage.setMaximized(false);
        });
		stage.iconifiedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue)
                stage.setIconified(false);
        });
		
		stage.getIcons().add(new Image(MainApp.class.getResourceAsStream("/it/uniroma1/fastcharge/carmonitor/app/assets/resources/images/fast_charge_icon.png")));
		
        try {
        	PreferencesController preferencesController = new PreferencesController(primaryStage, stage, rootController);
        	FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/it/uniroma1/fastcharge/carmonitor/app/views/preferences/PreferencesView.fxml"));
    		loader.setController(preferencesController);
    		root = loader.load();
        	
            JFXDecorator decorator = new JFXDecorator(stage, root);
            
            scene = new Scene(decorator, 400, 600);
            scene.getStylesheets().add(MainApp.class.getResource("/it/uniroma1/fastcharge/carmonitor/app/assets/stylesheets/application.css").toExternalForm());
            scene.getStylesheets().add(MainApp.class.getResource("/it/uniroma1/fastcharge/carmonitor/app/assets/stylesheets/preferences.css").toExternalForm());
            stage.setScene(scene);
            
            stage.setResizable(false);
            
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            
            stage.centerOnScreen();
            stage.setOnHidden(e -> preferencesController.shutdown());

            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	private void handleCloseWindow(ActionEvent event) {
		primaryStage.close();
	}
	
	private void exportCsvPreviousSession(ActionEvent event) {
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
		FileChooser logChooser = new FileChooser();
		logChooser.setTitle("Open log file");
		logChooser.setInitialDirectory(new File(ApplicationPreferences.getConfiguration().getLogDir()));
        FileChooser csvChooser = new FileChooser();
        csvChooser.setTitle("Choose csv file");
        csvChooser.getExtensionFilters().add(extFilter);
        File selectedFile = 
                logChooser.showOpenDialog(primaryStage);
        File csvFile = 
                csvChooser.showSaveDialog(primaryStage);
         
        if (selectedFile != null && csvFile != null) {
        	System.out.println("Exporting log to csv file...");
        	ExportCsvTask export = new ExportCsvTask(selectedFile, csvFile);
        	TaskExecutor.getInstance().perform(export);
        	System.out.println("Export done!");
        }
	}
}
