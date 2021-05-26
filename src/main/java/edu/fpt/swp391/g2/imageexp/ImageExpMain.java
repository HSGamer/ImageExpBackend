package edu.fpt.swp391.g2.imageexp;

import edu.fpt.swp391.g2.imageexp.config.MainConfig;
import edu.fpt.swp391.g2.imageexp.database.DatabaseConnector;
import edu.fpt.swp391.g2.imageexp.server.ImageExpServer;
import edu.fpt.swp391.g2.imageexp.terminal.ImageExpTerminal;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

@Getter
public class ImageExpMain {
    private final ImageExpCommandManager commandManager = new ImageExpCommandManager();
    private final ImageExpTerminal terminal = new ImageExpTerminal();
    private final Logger logger = LogManager.getLogger(this.getClass());
    private final ImageExpServer imageExpServer = new ImageExpServer();
    private final MainConfig mainConfig = new MainConfig();
    @Setter
    private boolean shuttingDown = false;

    public void enable() {
        mainConfig.setup();
        if (!loadServer()) {
            shuttingDown = true;
            System.exit(1);
            return;
        }
        logger.info("Connect to the database");
        connectDatabase();
        logger.info("Start the server");
        imageExpServer.enable();
        logger.info("For help, please type 'help'");
        terminal.start();
    }

    public void shutdown() {
        imageExpServer.disable();
        commandManager.disable();
        closeDatabase();
    }

    public void reload() {
        imageExpServer.disable();
        closeDatabase();
        mainConfig.reload();
        if (!loadServer()) {
            shuttingDown = true;
            System.exit(1);
            return;
        }
        connectDatabase();
        imageExpServer.enable();
    }

    private boolean loadServer() {
        try {
            imageExpServer.init();
            logger.info(() -> "Init the server at " + MainConfig.SERVER_IP.getValue() + ":" + MainConfig.SERVER_PORT.getValue());
            return true;
        } catch (Exception e) {
            logger.error("Cannot create the server", e);
            return false;
        }
    }

    private void connectDatabase() {
        try {
            DatabaseConnector.init();
        } catch (ClassNotFoundException | SQLException e) {
            logger.error("Cannot connect to the database", e);
        }
    }

    private void closeDatabase() {
        try {
            DatabaseConnector.disable();
        } catch (SQLException e) {
            logger.error("Cannot close the database", e);
        }
    }
}
