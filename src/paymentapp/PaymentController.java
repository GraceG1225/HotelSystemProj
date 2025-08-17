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
import confirmationemail.EmailService;

public class PaymentController {

    @FXML private Label errorLabel;
    @FXML private Label emailStatusLabel; // show email sending status
    @FXML public TextField nameField;
    @FXML public TextField cardNumberField;
    @FXML public TextField expiryField;
    @FXML public TextField cvvField;
    @FXML public Label amountLabel;

    private double totalAmount;
    private PaymentProcessor paymentProcessor = new PaymentProcessor();
    private EmailService emailService = new EmailService();

    private String recipientEmail;
    private String reservationDetails = "";

    public void setRecipientEmail(String email) {
        this.recipientEmail = email;
    }

    public void setBookingDetails(LocalDate checkInDate, LocalDate checkOutDate, String packageType) {
        long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        double pricePerNight = packageType.equalsIgnoreCase("Premium") ? 220 : 100;

        totalAmount = pricePerNight * nights;
        amountLabel.setText(String.format("Total: $%.2f", totalAmount));

        // formatted reservation details
        reservationDetails = String.format(
            "Package: %s\nCheck-in: %s\nCheck-out: %s\nNights: %d\nTotal: $%.2f",
            packageType, checkInDate, checkOutDate, nights, totalAmount
        );
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

        if (!paymentProcessor.isValidCardHolderName(name) || !paymentProcessor.isValidCardNumber(cardNumber) ||
            !paymentProcessor.isValidExpiryDate(expiry) || !paymentProcessor.isValidCCV(cvv)) {
            errorLabel.setText("Please enter valid payment details.");
            return;
        }

        errorLabel.setText("");
        paymentProcessor.chargeCard(totalAmount);
        System.out.println("Payment confirmed!");

        // show confirmation screen immediately
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/confirmationpage/ThankYouPage.fxml"));
            Scene thankYouScene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(thankYouScene);
            stage.setTitle("Reservation Confirmed!");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Error loading confirmation screen.");
        }

        // send email to user in background
        if (recipientEmail != null && !recipientEmail.isEmpty()) {
            if (emailStatusLabel != null) {
                emailStatusLabel.setText("Sending confirmation email...");
            }

            javafx.concurrent.Task<Void> emailTask = new javafx.concurrent.Task<>() {
                @Override
                protected Void call() {
                    // include guest's name in email
                    emailService.sendConfirmation(recipientEmail, name, reservationDetails);

                    // update label on JavaFX thread
                    if (emailStatusLabel != null) {
                        javafx.application.Platform.runLater(() -> emailStatusLabel.setText("Confirmation email sent!"));
                    }
                    return null;
                }
            };
            new Thread(emailTask).start();
        }
    }
}