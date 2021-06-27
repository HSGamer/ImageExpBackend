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
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The email verification handler
 */
public class VerifyEmailHandler {
    private static final Logger logger = LogManager.getLogger(VerifyEmailHandler.class);
    private static final Properties properties = new Properties();

    private VerifyEmailHandler() {
        // EMPTY
    }

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
        try (InputStream inputStream = VerifyEmailHandler.class.getResourceAsStream("/email-host.properties")) {
            properties.load(inputStream);
        } catch (Exception e) {
            logger.error("Error when loading email properties", e);
        }
    }

    /**
     * Get the random verify code
     *
     * @return the verify code
     */
    public static String getRandom() {
        int number = ThreadLocalRandom.current().nextInt(999999);
        return String.format("%06d", number);
    }

    /**
     * Send the verification email
     *
     * @param toEmail the email
     * @param code    the verify code
     * @throws MessagingException if there is an error when sending the email
     */
    public static void sendEmail(String toEmail, String code) throws MessagingException {
        String fromEmail = MainConfig.EMAIL_USERNAME.getValue();
        String password = MainConfig.EMAIL_PASSWORD.getValue();

        String title = MainConfig.EMAIL_CONTENT_TITLE.getValue();
        List<String> content = MainConfig.EMAIL_CONTENT_BODY.getValue();
        content.replaceAll(s -> s.replace("{code}", code));
        String contentString = String.join("", content);

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
        mimeMessage.setContent(contentString, "text/html");
        Transport.send(mimeMessage);
    }

    /**
     * Send the verification email asynchronously
     *
     * @param toEmail the email
     * @param code    the verify code
     * @return the sending task
     */
    public static CompletableFuture<Void> sendEmailAsync(String toEmail, String code) {
        return CompletableFuture.runAsync(() -> {
            try {
                sendEmail(toEmail, code);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }).exceptionally(throwable -> {
            if (throwable != null) {
                logger.log(Level.WARN, () -> "Error when sending email to " + toEmail, throwable);
            }
            return null;
        });
    }
}
