package edu.fpt.swp391.g2.imageexp.email;

import edu.fpt.swp391.g2.imageexp.entity.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The email verification handler
 */
public class VerifyEmailHandler {

    /**
     * Get the random verify code
     *
     * @return the verify code
     */
    public String getRandom() {
        int number = ThreadLocalRandom.current().nextInt(999999);
        return String.format("%06d", number);
    }

    /**
     * Send the verification email to the user
     *
     * @param user the user
     * @param code the verify code
     * @throws MessagingException if there is an error when sending the email
     */
    public void sendEmail(User user, String code) throws MessagingException {
        String toEmail = user.getEmail();
        String fromEmail = "LATER@gmail.com";
        String password = "LATER";

        Properties pr = new Properties();
        pr.setProperty("mail.smtp.host", "smtp.gmail.com");
        pr.setProperty("mail.smtp.port", "587");
        pr.setProperty("mail.smtp.auth", "true");
        pr.setProperty("mail.smtp.starttls.enable", "true");
        pr.put("mail.smtp.socketFactory.port", "587");
        pr.put("mail.smtp.ssl.checkserveridentity", "true");
        pr.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        //get session to authenticate the host email address and password
        Session session = Session.getInstance(pr, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        //set email message details
        Message mess = new MimeMessage(session);

        //set from email address
        mess.setFrom(new InternetAddress(fromEmail));
        //set to email address or destination email address
        mess.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

        //set email subject
        mess.setSubject("User Email Verification");

        //set message text
        mess.setText("Registered successfully.Please verify your account using this code: " + code);
        //send the message
        Transport.send(mess);
    }

}
