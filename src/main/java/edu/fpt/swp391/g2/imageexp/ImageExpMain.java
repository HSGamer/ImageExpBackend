package edu.fpt.swp391.g2.imageexp;

import edu.fpt.swp391.g2.imageexp.config.MainConfig;
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
        logger.info("Start the server");
        imageExpServer.enable();
        terminal.start();
    }

    public void shutdown() {
        imageExpServer.disable();
        commandManager.disable();
    }

    public void reload() {
        imageExpServer.disable();
        mainConfig.reload();
        if (!loadServer()) {
            shuttingDown = true;
            System.exit(1);
            return;
        }
        imageExpServer.enable();
    }

    private boolean loadServer() {
        try {
            imageExpServer.init();
            return true;
        } catch (Exception e) {
            logger.error("Cannot create the server", e);
            return false;
        }
    }
}
