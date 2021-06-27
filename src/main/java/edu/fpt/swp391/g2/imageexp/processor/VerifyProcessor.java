package edu.fpt.swp391.g2.imageexp.processor;

import edu.fpt.swp391.g2.imageexp.config.MainConfig;
import edu.fpt.swp391.g2.imageexp.email.EmailHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * The processor for working with verification
 */
public class VerifyProcessor {
    private static final Logger logger = LogManager.getLogger(VerifyProcessor.class);

    public static void sendVerifyCodeAsync(String email, String code) {
        String title = MainConfig.EMAIL_VERIFICATION_TITLE.getValue();
        List<String> content = MainConfig.EMAIL_VERIFICATION_BODY.getValue();
        content.replaceAll(s -> s.replace("{code}", code));
        EmailHandler.sendEmailAsync(email, title, String.join("", content))
                .thenAccept(unused -> logger.info("Finished sending verify code to " + email));
    }
}
