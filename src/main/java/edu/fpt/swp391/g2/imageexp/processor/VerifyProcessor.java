package edu.fpt.swp391.g2.imageexp.processor;

import com.sun.org.apache.bcel.internal.classfile.Code;
import edu.fpt.swp391.g2.imageexp.config.MainConfig;
import edu.fpt.swp391.g2.imageexp.database.DatabaseConnector;
import edu.fpt.swp391.g2.imageexp.email.EmailHandler;
import edu.fpt.swp391.g2.imageexp.entity.User;
import edu.fpt.swp391.g2.imageexp.utils.Utils;
import me.hsgamer.hscore.database.client.sql.PreparedStatementContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * The processor for working with verification
 */
public class VerifyProcessor {
    private static final Logger logger = LogManager.getLogger(VerifyProcessor.class);

    private VerifyProcessor() {
        // EMPTY
    }

    public static void sendVerifyCodeAsync(String email, String code) {
        String title = MainConfig.EMAIL_VERIFICATION_TITLE.getValue();
        List<String> content = MainConfig.EMAIL_VERIFICATION_BODY.getValue();
        content.replaceAll(s -> s.replace("{code}", code));
        EmailHandler.sendEmailAsync(email, title, String.join("", content))
                .thenAccept(logger::info);
    }

    public static void setVerifyState(String email) throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "UPDATE user SET verified = ? WHERE email = ?",
                        0, email
                )
        ) {
            container.update();
        }
    }
    public static String checkVerifyCode(int id) throws SQLException {
        String code="";
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "select code from code where userID = ?  limit 1",
                        id
                );
                ResultSet resultSet = container.query()
        ) {
            if (!resultSet.next()) {
                return null;
            }
            return code;
        }
    }



}
