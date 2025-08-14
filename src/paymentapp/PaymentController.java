/**
 * Class Name: PaymentController
 * Date: July 30, 2025
 * Programmer: Maliya Cockrell
 *
 * Manages the payment screen, receives reservation info from the previous screen,
 * calculates total cost based on package and length of stay, and handles payment form input.
 */

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PaymentController {
    @FXML private Label errorLabel;
    public TextField nameField;
    public TextField cardNumberField;
    public TextField expiryField;
    public TextField cvvField;
    public Label amountLabel;

    private double totalAmount;

    /**
     * Receives booking details and calculates total cost.
     *
     * @param checkInDate check-in date selected by user
     * @param checkOutDate check-out date selected by user
     * @param packageType selected package (Basic or Premium)
     */
    public void setBookingDetails(LocalDate checkInDate, LocalDate checkOutDate, String packageType) {
        long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        double pricePerNight = 0;
        if (packageType.equalsIgnoreCase("Basic")) {
            pricePerNight = 100;
        } else if (packageType.equalsIgnoreCase("Premium")) {
            pricePerNight = 220;
        }

        double total = pricePerNight * nights;
        amountLabel.setText(String.format("Total: $%.2f", total));
    }

    /**
     * Validates form input and confirms payment.
     */
    @FXML
    private void handleConfirm() {
        if (nameField.getText().isEmpty() || cardNumberField.getText().isEmpty() || expiryField.getText().isEmpty() || cvvField.getText().isEmpty()) {
           errorLabel.setText("Please fill out all payment fields.");
            return;
        }
        else {
            errorLabel.setText("");  // clear if valid
        }

        System.out.println("Payment Confirmed!");
    }
}
