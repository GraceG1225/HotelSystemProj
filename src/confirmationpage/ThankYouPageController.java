package confirmationpage;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

public class ThankYouPageController {

    @FXML
    private Button closeButton;

    @FXML
    private void closeConfirmation(ActionEvent event) {
        // get current window from button and close it
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
