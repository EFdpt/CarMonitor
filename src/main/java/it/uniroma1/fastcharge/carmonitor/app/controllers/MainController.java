package it.uniroma1.fastcharge.carmonitor.app.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import it.uniroma1.fastcharge.carmonitor.app.views.i18n.I18N;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

public class MainController implements Initializable {
	
	@FXML
	private BorderPane mainPane;
	
	@FXML
	private Menu fileMenu;
	
	@FXML
	private MenuItem closeMenuItem;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fileMenu.textProperty().bind(I18N.createStringBinding("Menu.File"));
		closeMenuItem.textProperty().bind(I18N.createStringBinding("Menu.File.Close"));
	}
	
	public MainController() {}

}
