/*  Class name: ManageReservationsController
*   Date: August 11, 2025
*   Programmer: Maliya Cockrell
*
 * Displays the user's upcoming reservation and allows the user to cancel it.
 * Later will be connected to the backend to display and manage real reservation data.
 */
package managereservation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageReservationsController implements Initializable {

    @FXML private Label reservationDetails;
    @FXML private Label statusLabel;

    /**
     * Loads placeholder reservation info at runtime.
     *
     * @param location unused
     * @param resources unused
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: Replace this placeholder text with actual reservation data from the database
        reservationDetails.setText("Room: Premium\nDates: July 15–17\nGuests: 2 Adults");
    }

    /**
     * Handles reservation cancellation and updates the UI.
     *
     * @param event the button click event
     */
    @FXML
    private void handleCancel(ActionEvent event) {
        // TODO: cancellation logic
        reservationDetails.setText("No upcoming reservations.");
        statusLabel.setText("Reservation canceled.");
    }

    /**
     * Returns the user to the main reservation screen.
     *
     * @param event the button click event
     */
    @FXML
    private void handleBack(ActionEvent event) {
        try {
            // Navigate back to the main booking screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reservation/MainView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Reservation");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

