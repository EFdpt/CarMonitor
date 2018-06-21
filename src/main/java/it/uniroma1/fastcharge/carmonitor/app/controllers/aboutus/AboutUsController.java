package it.uniroma1.fastcharge.carmonitor.app.controllers.aboutus;

import java.net.URL;
import java.util.ResourceBundle;

import it.uniroma1.fastcharge.carmonitor.app.views.i18n.I18N;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.stage.Stage;

public class AboutUsController implements Initializable {
	
	private Stage parentStage, stage;
	
	@FXML
	private Label aboutUsTextLabel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GaussianBlur blur = new GaussianBlur(10);
		parentStage.getScene().getRoot().setEffect(blur);
		
		aboutUsTextLabel.textProperty().bind(I18N.createStringBinding("AboutUs.Text"));
	}
	
	public AboutUsController(Stage parentStage, Stage stage) {
		this.parentStage = parentStage;
		this.stage = stage;
	}
	
	public void shutdown() {
		parentStage.getScene().getRoot().setEffect(null);
    }
}
