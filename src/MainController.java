import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainController {
    @FXML private DatePicker checkInDate;
    @FXML private DatePicker checkOutDate;
    @FXML private TextField adultsField;
    @FXML private TextField childrenField;
    @FXML private TextField roomCountField;
    @FXML private RadioButton basicPackage;
    @FXML private RadioButton premiumPackage;

    @FXML
    private void handleSubmit() {
        System.out.println("Submit button clicked!");
    }

    @FXML
    private void handleAbout() {
        System.out.println("About Us clicked");
    }

    @FXML
    private void handleManage() {
        System.out.println("Manage Reservations clicked");
    }

    @FXML
    private void handleSettings() {
        System.out.println("Settings clicked");
    }
}
