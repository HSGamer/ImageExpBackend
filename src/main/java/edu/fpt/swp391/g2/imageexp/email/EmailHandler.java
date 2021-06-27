package edu.fpt.swp391.g2.imageexp.email;

import edu.fpt.swp391.g2.imageexp.ImageExpBoostrap;
import edu.fpt.swp391.g2.imageexp.config.MainConfig;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

/**
 * The email handler
 */
public class EmailHandler {
    private static final Logger logger = LogManager.getLogger(EmailHandler.class);
    private static final Properties properties = new Properties();

    private EmailHandler() {
        // EMPTY
    }

    /**
     * Init the settings
     */
    public static void init() {
        properties.clear();
        if (MainConfig.EMAIL_CHECK_ENV.getValue()) {
            String username = System.getenv("EMAIL_USERNAME");
            String password = System.getenv("EMAIL_PASSWORD");
            if (username != null) {
                MainConfig.EMAIL_USERNAME.setValue(username);
            }
            if (password != null) {
                MainConfig.EMAIL_PASSWORD.setValue(password);
            }
            ImageExpBoostrap.INSTANCE.getMainConfig().save();
        }
        try (InputStream inputStream = EmailHandler.class.getResourceAsStream("/email-host.properties")) {
            properties.load(inputStream);
        } catch (Exception e) {
            logger.error("Error when loading email properties", e);
        }
    }

    /**
     * Send the content to the email
     *
     * @param toEmail the email
     * @param title   the title
     * @param content the content
     * @throws MessagingException if there is an error when sending the email
     */
    public static void sendEmail(String toEmail, String title, String content) throws MessagingException {
        String fromEmail = MainConfig.EMAIL_USERNAME.getValue();
        String password = MainConfig.EMAIL_PASSWORD.getValue();

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        Message mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress(fromEmail));
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        mimeMessage.setSubject(title);
        mimeMessage.setContent(content, "text/html");
        Transport.send(mimeMessage);
    }

    /**
     * Send the content to the email asynchronously
     *
     * @param toEmail the email
     * @param title   the title
     * @param content the content
     * @return the sending task, with the value is the status message that tells whether the task was successful
     */
    public static CompletableFuture<String> sendEmailAsync(String toEmail, String title, String content) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                sendEmail(toEmail, title, content);
                return "Successfully sent to " + toEmail;
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }).exceptionally(throwable -> {
            if (throwable != null) {
                logger.log(Level.WARN, () -> "Error when sending email to " + toEmail, throwable);
            }
            return "Failed to sent to " + toEmail;
        });
    }
}
