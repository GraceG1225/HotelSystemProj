import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    public Label welcomeLabel;
    @FXML private DatePicker checkInDate;
    @FXML private DatePicker checkOutDate;
    @FXML private TextField adultsField;
    @FXML private TextField childrenField;
    @FXML private TextField roomCountField;
    @FXML private RadioButton basicPackage;
    @FXML private RadioButton premiumPackage;

    @FXML
    private void handleSubmit() throws IOException {
        String packageType;

        if (basicPackage.isSelected()) {
            packageType = "Basic";
        } else if (premiumPackage.isSelected()) {
            packageType = "Premium";
        } else {
            packageType = "None";
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PaymentView.fxml"));
        Parent root = loader.load();

        PaymentController controller = loader.getController();
        controller.setBookingDetails(checkInDate.getValue(), checkOutDate.getValue(), packageType);

        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        Stage stage = (Stage) checkInDate.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
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
