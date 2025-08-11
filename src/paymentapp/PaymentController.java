package paymentapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PaymentController {

    @FXML private Label errorLabel;
    @FXML public TextField nameField;
    @FXML public TextField cardNumberField;
    @FXML public TextField expiryField;
    @FXML public TextField cvvField;
    @FXML public Label amountLabel;

    private double totalAmount;
    private PaymentProcessor paymentProcessor = new PaymentProcessor();

    public void setBookingDetails(LocalDate checkInDate, LocalDate checkOutDate, String packageType) {
        long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        double pricePerNight = 0;

        if (packageType.equalsIgnoreCase("Basic")) {
            pricePerNight = 100;
        } else if (packageType.equalsIgnoreCase("Premium")) {
            pricePerNight = 220;
        }

        totalAmount = pricePerNight * nights;
        amountLabel.setText(String.format("Total: $%.2f", totalAmount));
    }

    @FXML
    private void handleConfirm(javafx.event.ActionEvent event) {
        if (nameField.getText().isEmpty() || cardNumberField.getText().isEmpty() ||
            expiryField.getText().isEmpty() || cvvField.getText().isEmpty()) {
            errorLabel.setText("Please fill out all payment fields.");
            return;
        }

        String name = nameField.getText().trim();
        String cardNumber = cardNumberField.getText().replaceAll("\\s+", "");
        String expiry = expiryField.getText().trim();
        String cvv = cvvField.getText().trim();

        if (!paymentProcessor.isValidCardHolderName(name)) {
            errorLabel.setText("Please enter your full name (first and last).");
            return;
        }

        if (!paymentProcessor.isValidCardNumber(cardNumber)) {
            errorLabel.setText("Invalid card number. Must be exactly 16 digits.");
            return;
        }

        if (!paymentProcessor.isValidExpiryDate(expiry)) {
            errorLabel.setText("Invalid expiry date. Enter a valid date and please use MO/YR format.");
            return;
        }

        if (!paymentProcessor.isValidCCV(cvv)) {
            errorLabel.setText("Invalid CVV. Must be exactly 3 digits.");
            return;
        }

        errorLabel.setText("");
        paymentProcessor.chargeCard(totalAmount);
        System.out.println("Payment confirmed!");

        // go to thank you for reserving page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/confirmationpage/ThankYouPage.fxml"));
            Scene thankYouScene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(thankYouScene);
            stage.setTitle("Reservation Confirmed!");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Sorry! There was an error loading our confirmation screen.");
        }
    }
}
