package edu.fpt.swp391.g2.imageexp;

import edu.fpt.swp391.g2.imageexp.config.MainConfig;
import edu.fpt.swp391.g2.imageexp.database.DatabaseConnector;
import edu.fpt.swp391.g2.imageexp.server.ImageExpServer;
import edu.fpt.swp391.g2.imageexp.terminal.ImageExpTerminal;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    public void enable(String[] args) {
        mainConfig.setup();
        if (args.length > 0) {
            String port = args[0];
            try {
                int parsedPort = Integer.parseInt(port);
                MainConfig.SERVER_PORT.setValue(parsedPort);
                mainConfig.save();
            } catch (NumberFormatException e) {
                logger.warn("The port is not a valid number");
            }
        }
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
            logger.info(() -> "Init the server at " + MainConfig.SERVER_IP.getValue() + ":" + MainConfig.SERVER_PORT.getValue());
            return true;
        } catch (Exception e) {
            logger.error("Cannot create the server", e);
            return false;
        }
    }
}
