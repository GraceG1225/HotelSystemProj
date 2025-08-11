package aboutuspage;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class AboutUsController implements Initializable {

    @FXML
    private Label aboutText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        aboutText.setText("Welcome to our Hotel System app. This About Us Page is a work of progress.");
    }

    @FXML
    private void handleBack(javafx.event.ActionEvent event) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/reservation/MainView.fxml"));
            javafx.scene.Parent mainRoot = loader.load();

            javafx.stage.Stage stage = (javafx.stage.Stage)((javafx.scene.control.Button)event.getSource()).getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(mainRoot));
            stage.setTitle("Reservation");
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}

