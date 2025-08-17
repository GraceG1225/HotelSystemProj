package confirmationemail;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EmailService {
    private final String senderEmail = "gracegallardo1225@gmail.com";
    private final String senderPassword = "mazu pcvj uybq ttqz";  // app password for gmail

    public void sendConfirmation(String recipientEmail, String guestName, String reservationDetails) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Hotel Reservation Confirmation");

            // email content
            String htmlContent = "<html>"
                    + "<body>"
                    + "<h2>Hotel Reservation Confirmation</h2>"
                    + "<p>Dear " + guestName + ",</p>"
                    + "<p>Thank you for booking with us! Below are your reservation details:</p>"
                    + "<pre>" + reservationDetails + "</pre>"
                    + "<p>Please feel free to call or email us any questions you may have regarding your booking!</p>"
                    + "<p>We look forward to hosting you!</p>"
                    + "<br>"
                    + "<p>-Dream Hotel</p>"
                    + "</body>"
                    + "</html>";

            message.setContent(htmlContent, "text/html");

            Transport.send(message);
            System.out.println("Confirmation email sent to " + recipientEmail);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
