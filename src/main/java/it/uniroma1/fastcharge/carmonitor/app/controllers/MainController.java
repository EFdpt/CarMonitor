package it.uniroma1.fastcharge.carmonitor.app.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import it.uniroma1.fastcharge.carmonitor.app.models.activities.atomic.RadioConnectTask;
import it.uniroma1.fastcharge.carmonitor.app.models.activities.framework.TaskExecutor;
import it.uniroma1.fastcharge.carmonitor.app.models.car.Car;
import it.uniroma1.fastcharge.carmonitor.app.models.radio.SerialRadio;
import it.uniroma1.fastcharge.carmonitor.app.models.session.Session;
import it.uniroma1.fastcharge.carmonitor.app.views.i18n.I18N;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

public class MainController implements Initializable {
	
	@FXML
	private BorderPane mainPane;
	
	@FXML
	private Menu fileMenu, radioMenu, serialPortMenu;
	
	@FXML
	private MenuItem connectMenuItem, closeMenuItem;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fileMenu.textProperty().bind(I18N.createStringBinding("Menu.File"));
		connectMenuItem.setOnAction(this::handleSerialRadioConnect);
		closeMenuItem.textProperty().bind(I18N.createStringBinding("Menu.File.Close"));	
		radioMenu.showingProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> observable,
		            Boolean oldValue, Boolean newValue) {

		        if (newValue) {
		        	handleGetSerialPorts();
		        }
		    }
		});
	}
	
	public MainController() {}
	
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
			}
		});
		
		// remove serialPort menuItem if disconnected
		serialPortMenu.getItems().parallelStream().forEach(item -> {
			if (SerialRadio.getCommPorts().stream()
						.filter(port -> port.getSystemPortName().equals(item.getText()))
						.findFirst()
						.orElse(null) == null)
				serialPortMenu.getItems().remove(item);
		});
	}
	
	private void handleSetSerialPort(ActionEvent event) {
		MenuItem i = (MenuItem) event.getSource();
		SerialRadio radio = SerialRadio.getCommPorts().parallelStream()
						.filter(p -> p.getSystemPortName().equals(i.getText()))
						.findFirst()
						.orElse(null);
		if (radio == null)
			return;
		if (Session.getDefaultInstance().getRadio() == null || !Session.getDefaultInstance().getRadio().isOpen()) {
			Session.getDefaultInstance().setRadio(radio);
			System.out.println("Serial port successfully setted");
			serialPortMenu.setText("Serial port: (" + radio.getSystemPortName() + ")");
		}
	}
	
	private void handleSerialRadioConnect(ActionEvent e) {
		if (Session.getDefaultInstance().getRadio() == null || Session.getDefaultInstance().getRadio().isOpen())
			return;
		TaskExecutor.getInstance().perform(new RadioConnectTask());
		serialPortMenu.setDisable(true);
		connectMenuItem.setDisable(true);
	}

}
