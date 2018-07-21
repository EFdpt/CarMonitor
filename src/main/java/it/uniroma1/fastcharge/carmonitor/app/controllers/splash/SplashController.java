package it.uniroma1.fastcharge.carmonitor.app.controllers.splash;

import java.net.URL;
import java.util.ResourceBundle;

import it.uniroma1.fastcharge.carmonitor.app.views.i18n.I18N;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class SplashController implements Initializable {

    @FXML
    private StackPane rootPane;
    
    @FXML
    private Text splashMessage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	splashMessage.textProperty().bind(I18N.createStringBinding("appTitle"));
    }

}