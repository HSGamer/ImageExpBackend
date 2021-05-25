package edu.fpt.swp391.g2.imageexp;

import edu.fpt.swp391.g2.imageexp.server.ImageExpServer;
import edu.fpt.swp391.g2.imageexp.terminal.ImageExpTerminal;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Getter
public class ImageExpMain {
    private final ImageExpCommandManager commandManager = new ImageExpCommandManager();
    private final ImageExpTerminal terminal = new ImageExpTerminal();
    private final Logger logger = LogManager.getLogger(this.getClass());
    private final ImageExpServer imageExpServer = new ImageExpServer();
    @Setter
    private boolean shuttingDown = false;

    public void enable() {
        try {
            imageExpServer.init();
            logger.info("Init the server");
        } catch (Exception e) {
            logger.error("Cannot create the server", e);
            shuttingDown = true;
            return;
        }
        logger.info("Start the server");
        imageExpServer.enable();
        terminal.start();
    }

    public void shutdown() {
        imageExpServer.disable();
        commandManager.disable();
    }
}
