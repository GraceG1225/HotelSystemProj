package paymentapp;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PaymentProcessor {

    public boolean isValidCardNumber(String cardNumber) {
        cardNumber = cardNumber.replaceAll("\\s+", "");
        return cardNumber.matches("\\d{16}");
    }

    public boolean isValidExpiryDate(String expiryDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
            YearMonth expDate = YearMonth.parse(expiryDate, formatter);
            YearMonth now = YearMonth.now();
            return expDate.isAfter(now) || expDate.equals(now);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public boolean isValidCCV(String ccv) {
        return ccv.matches("\\d{3}");
    }

    public boolean isValidCardHolderName(String name) {
        return name.trim().split("\\s+").length >= 2;
    }

    public void chargeCard(double amount) {
        System.out.println("Payment of $" + String.format("%.2f", amount) + " was successful. Thank you for choosing Dream Hotel!");
    }
}
