package edu.fpt.swp391.g2.imageexp.processor;

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

/**
 * The processor for working with verification
 */
public class VerifyProcessor {
    private static final Logger logger = LogManager.getLogger(VerifyProcessor.class);

    private VerifyProcessor() {
        // EMPTY
    }

    /**
     * Create the verify code and send it to the user's email
     *
     * @param user the user
     * @throws SQLException if there is an SQL error
     */
    public static void createAndSendVerifyCode(User user) throws SQLException {
        String code = Utils.getRandomDigitString();
        insertVerifyCode(user.getUserId(), code);
        sendVerifyCodeAsync(user.getEmail(), code);
    }

    /**
     * Send the verify code to the email
     *
     * @param email the email
     * @param code  the code
     */
    public static void sendVerifyCodeAsync(String email, String code) {
        String title = MainConfig.EMAIL_VERIFICATION_TITLE.getValue();
        List<String> content = MainConfig.EMAIL_VERIFICATION_BODY.getValue();
        content.replaceAll(s -> s.replace("{code}", code));
        EmailHandler.sendEmailAsync(email, title, String.join("", content))
                .thenAccept(logger::info);
    }

    /**
     * Set the verify state of the user
     *
     * @param email the email's user
     * @param state the state
     * @throws SQLException if there is an SQL error
     */
    public static void setVerifyState(String email, boolean state) throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "UPDATE user SET verified = ? WHERE email = ?",
                        state, email
                )
        ) {
            container.update();
        }
    }

    /**
     * Check the verify code with the available code from the database
     *
     * @param id   the user's id
     * @param code the verify code
     * @return true if there is a matched code from the database
     * @throws SQLException if there is an SQL error
     */
    public static boolean checkVerifyCode(int id, String code) throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "select * from code where userID = ? and code = ? limit 1",
                        id, code
                );
                ResultSet resultSet = container.query()
        ) {
            return resultSet.next();
        }
    }

    /**
     * Insert the verify code to the database
     *
     * @param id   the user's id
     * @param code the code
     * @throws SQLException if there is an SQL error
     */
    public static void insertVerifyCode(int id, String code) throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "replace into code(userID, code) values (?, ?)",
                        id, code
                )
        ) {
            container.update();
        }
    }
}
