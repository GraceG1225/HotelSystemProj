package reservation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import paymentapp.PaymentController;
import javafx.event.EventHandler;

import java.io.IOException;
import java.time.LocalDate;

public class MainController {

    private static final long TIMEOUT_DURATION = 5 * 60 * 1000; // 5min
    private Timeline inactivityTimer;

    private final EventHandler<MouseEvent> mouseEventHandler = e -> resetInactivityTimer();
    private final EventHandler<KeyEvent> keyEventHandler = e -> resetInactivityTimer();

    @FXML private DatePicker checkInDate;
    @FXML private DatePicker checkOutDate;
    @FXML private TextField adultsField;
    @FXML private TextField childrenField;
    @FXML private TextField roomCountField;
    @FXML private RadioButton basicPackage;
    @FXML private RadioButton premiumPackage;

    private ToggleGroup packageToggleGroup = new ToggleGroup();
    private BookingLimits limits = new BookingLimits();

    @FXML
    public void initialize() {
        basicPackage.setToggleGroup(packageToggleGroup);
        premiumPackage.setToggleGroup(packageToggleGroup);

        Platform.runLater(() -> {
            Scene scene = checkInDate.getScene();
            if (scene != null) {
                scene.addEventFilter(MouseEvent.ANY, e -> resetInactivityTimer());
                scene.addEventFilter(KeyEvent.ANY, e -> resetInactivityTimer());
                startInactivityTimer();
            }
        });
    }

    private void startInactivityTimer() {
        inactivityTimer = new Timeline(new KeyFrame(Duration.millis(TIMEOUT_DURATION), e -> handleTimeout()));
        inactivityTimer.setCycleCount(1);
        inactivityTimer.play();
    }

    private void resetInactivityTimer() {
        if (inactivityTimer != null) {
            inactivityTimer.stop();
            inactivityTimer.playFromStart();
        }
    }

    private void handleTimeout() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Session Timeout!");
            alert.setHeaderText(null);
            alert.setContentText("You have been inactive for 5 minutes. The session will now end.");
            alert.showAndWait();
            Platform.exit();
        });
    }

    @FXML
    private void handleAbout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/aboutuspage/AboutUsView.fxml"));
            Parent aboutRoot = loader.load();
            
            Stage stage = (Stage) checkInDate.getScene().getWindow();
            stage.setScene(new Scene(aboutRoot));
            stage.setTitle("About Us");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSubmit() {
        System.out.println("Submit button clicked!");

        LocalDate checkIn = checkInDate.getValue();
        LocalDate checkOut = checkOutDate.getValue();

        String dateValidationMsg = limits.validateDates(checkIn, checkOut);
        if (!dateValidationMsg.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Date Error", dateValidationMsg);
            return;
        }

        int adults, children, rooms;
        try {
            adults = Integer.parseInt(adultsField.getText());
            children = Integer.parseInt(childrenField.getText());
            rooms = Integer.parseInt(roomCountField.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Oops!", "Please enter a valid amount of guests.");
            return;
        }

        if (adults < 1 || adults > 40) {
            showAlert(Alert.AlertType.ERROR, "Sorry!", "Number of adults cannot exceed 40.");
            return;
        }

        if (children < 0 || children > 40) {
            showAlert(Alert.AlertType.ERROR, "Sorry!", "Number of children cannot exceed 40.");
            return;
        }

        if (rooms < 1 || rooms > 10) {
            showAlert(Alert.AlertType.ERROR, "Sorry!", "Per our policy, the number of rooms reserved online must not exceed 10. If you would like to reserve more than 10 rooms, please reserve through the front desk. The number is 1-800-343-3928.");
            return;
        }

        String roomsWarning = limits.getRoomsWarning(rooms);
        if (!roomsWarning.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Room Warning", roomsWarning);
        }

        System.out.println("Booking validated successfully!");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/paymentapp/PaymentView.fxml"));
            Parent paymentRoot = loader.load();

        // remove event filter from scene (avoids timer passing over to payment screen)
        Scene currentScene = checkInDate.getScene();
        if (currentScene != null) {
            currentScene.removeEventFilter(MouseEvent.ANY, e -> resetInactivityTimer());
            currentScene.removeEventFilter(KeyEvent.ANY, e -> resetInactivityTimer());
        }

        // stop and null inactivity timer
        if (inactivityTimer != null) {
            inactivityTimer.stop();
            inactivityTimer = null;
        }

        // Get controller and pass booking details
        PaymentController paymentController = loader.getController();
        String selectedPackage = basicPackage.isSelected() ? "Basic" : "Premium";
        paymentController.setBookingDetails(checkIn, checkOut, selectedPackage);

        Scene paymentScene = new Scene(paymentRoot);
        Stage currentStage = (Stage) checkInDate.getScene().getWindow();
        currentStage.setScene(paymentScene);
        currentStage.setTitle("Payment");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Unable to load the payment screen.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML private void handleManage() {
        System.out.println("Manage Reservations clicked");
    }

    @FXML private void handleSettings() {
        System.out.println("Settings clicked");
    }
}
