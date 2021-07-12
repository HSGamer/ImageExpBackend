package edu.fpt.swp391.g2.imageexp;

import edu.fpt.swp391.g2.imageexp.config.MainConfig;
import edu.fpt.swp391.g2.imageexp.database.DatabaseConnector;
import edu.fpt.swp391.g2.imageexp.server.ImageExpServer;
import edu.fpt.swp391.g2.imageexp.terminal.ImageExpTerminal;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Optional;

/**
 * The main instance of the project, where all services run.
 * You can get all the services with the get...() method
 */
@Getter
public class ImageExpMain {
    private final ImageExpCommandManager commandManager = new ImageExpCommandManager();
    private final ImageExpTerminal terminal = new ImageExpTerminal();
    private final Logger logger = LogManager.getLogger(this.getClass());
    private final ImageExpServer imageExpServer = new ImageExpServer();
    private final MainConfig mainConfig = new MainConfig();
    @Setter
    private boolean shuttingDown = false;

    /**
     * Enable the services
     */
    public void enable() {
        try {
            terminal.init();
        } catch (IOException e) {
            logger.error("Cannot load the terminal", e);
            shuttingDown = true;
            System.exit(1);
            return;
        }
        mainConfig.setup();
        checkEnv();
        if (!loadServer()) {
            shuttingDown = true;
            System.exit(1);
            return;
        }
        logger.info("Connect to the database");
        DatabaseConnector.init();
        logger.info("Start the server");
        imageExpServer.enable();
        logger.info("For help, please type 'help'");
        terminal.start();
    }

    /**
     * Shut down the services
     */
    public void shutdown() {
        imageExpServer.disable();
        commandManager.disable();
        DatabaseConnector.disable();
        System.exit(0);
    }

    /**
     * Reload the services (Mainly used when changing in the config file)
     */
    public void reload() {
        imageExpServer.disable();
        DatabaseConnector.disable();
        mainConfig.reload();
        if (!loadServer()) {
            shuttingDown = true;
            System.exit(1);
            return;
        }
        DatabaseConnector.init();
        imageExpServer.enable();
    }

    /**
     * Load the HTTP server
     *
     * @return whether the server can be enabled
     */
    private boolean loadServer() {
        try {
            imageExpServer.init();
            logger.info(() -> "Init the server at port " + MainConfig.SERVER_PORT.getValue());
            return true;
        } catch (Exception e) {
            logger.error("Cannot create the server", e);
            return false;
        }
    }

    private void checkEnv() {
        // MYSQL
        String mysql = System.getenv("MYSQL");
        if (mysql != null) {
            MainConfig.DATABASE_FIRST_LOAD.setValue(false);
            MainConfig.DATABASE_MYSQL.setValue(true);
            String[] mysqlSplit = mysql.split("\\|", 5);
            if (mysqlSplit.length > 0) {
                MainConfig.DATABASE_HOST.setValue(mysqlSplit[0]);
            }
            if (mysqlSplit.length > 1) {
                MainConfig.DATABASE_PORT.setValue(mysqlSplit[1]);
            }
            if (mysqlSplit.length > 2) {
                MainConfig.DATABASE_DB_NAME.setValue(mysqlSplit[2]);
            }
            if (mysqlSplit.length > 3) {
                MainConfig.DATABASE_USERNAME.setValue(mysqlSplit[3]);
            }
            if (mysqlSplit.length > 4) {
                MainConfig.DATABASE_PASSWORD.setValue(mysqlSplit[4]);
            }
        }

        // PORT
        String port = System.getenv("PORT");
        if (port != null) {
            try {
                int parsedPort = Integer.parseInt(port);
                MainConfig.SERVER_PORT.setValue(parsedPort);
            } catch (NumberFormatException e) {
                logger.warn("The port is not a valid number");
            }
        }

        // EMAIL
        String[] email = Optional.ofNullable(System.getenv("EMAIL")).map(s -> s.split("\\|", 2)).orElse(new String[0]);
        if (email.length > 0) {
            MainConfig.EMAIL_USERNAME.setValue(email[0]);
        }
        if (email.length > 1) {
            MainConfig.EMAIL_PASSWORD.setValue(email[1]);
        }

        mainConfig.save();
    }
}
