package adminpage;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import authenticatorapp.AuthenticatorApp;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageReservationsController implements Initializable {

    @FXML private Label reservationDetails;
    @FXML private Label statusLabel;

    private AuthenticatorApp app;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Placeholder text for demo
        reservationDetails.setText("Room: Premium\nDates: July 15–17\nGuests: 2 Adults");
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
