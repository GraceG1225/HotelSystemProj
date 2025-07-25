package paymentapp;

import java.util.Scanner;

public class PaymentTest {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PaymentProcessor processor = new PaymentProcessor();

        String cardNumber;
        do {
            System.out.print("Please enter your card number: ");
            cardNumber = scanner.nextLine();
            if (!processor.isValidCardNumber(cardNumber)) {
                System.out.println("Please enter anvalid card number.");
            }
        } while (!processor.isValidCardNumber(cardNumber));

        String expiryDate;
        do {
            System.out.print("Please enter the expiry date (MO/YR): ");
            expiryDate = scanner.nextLine();
            if (!processor.isValidExpiryDate(expiryDate)) {
                System.out.println("The expiry date is invalid or the card has already expired.");
            }
        } while (!processor.isValidExpiryDate(expiryDate));

        String ccv;
        do {
            System.out.print("Please enter the CCV: ");
            ccv = scanner.nextLine();
            if (!processor.isValidCCV(ccv)) {
                System.out.println("Please enter a valid CCV.");
            }
        } while (!processor.isValidCCV(ccv));

        String cardHolderName;
        do {
            System.out.print("Please enter the cardholder's full name: ");
            cardHolderName = scanner.nextLine();
            if (!processor.isValidCardHolderName(cardHolderName)) {
                System.out.println("Please enter a valid first and last name.");
            }
        } while (!processor.isValidCardHolderName(cardHolderName));

        double totalAmount = 145.87; // placeholder amt
        processor.chargeCard(totalAmount);

        scanner.close();
    }
}
