package adminpage;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import authenticatorapp.AuthenticatorApp;
import authenticatorapp.SQLite;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;

public class ManageReservationsController implements Initializable {

    @FXML private Label reservationDetails;
    @FXML private Label statusLabel;

    private AuthenticatorApp app;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadReservations();
    }

    private void loadReservations() {
        List<String> reservations = SQLite.getAllReservations();
        if (reservations.isEmpty()) {
            reservationDetails.setText("No reservations yet.");
        } else {
            // show latest reservation (or concatenate all)
            reservationDetails.setText(String.join("\n\n", reservations));
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        // TODO: actual cancellation logic
        reservationDetails.setText("No upcoming reservations.");
        statusLabel.setText("Reservation canceled.");
    }

    @FXML
    private void handleBack(ActionEvent event) {
        if (app != null) {
            app.showAdminLoginWindow();   // goes back to admin login instead of reservation page
        }
    }

    public void setApp(AuthenticatorApp app) {
        this.app = app;
    } 
}
