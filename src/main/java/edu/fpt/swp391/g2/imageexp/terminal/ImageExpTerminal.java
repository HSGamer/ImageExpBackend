package edu.fpt.swp391.g2.imageexp.terminal;

import lombok.extern.log4j.Log4j2;
import net.minecrell.terminalconsole.SimpleTerminalConsole;

import static edu.fpt.swp391.g2.imageexp.ImageExpBoostrap.INSTANCE;

@Log4j2
public class ImageExpTerminal extends SimpleTerminalConsole {

    @Override
    protected boolean isRunning() {
        return !INSTANCE.isShuttingDown();
    }

    @Override
    protected void runCommand(String s) {
        String[] split = s.split(" ", 2);
        if (!INSTANCE.getCommandManager().handleCommand(split[0], split.length > 1 ? split[1] : "")) {
            INSTANCE.getLogger().warn("No command was found");
        }
    }

    @Override
    protected void shutdown() {
        INSTANCE.getLogger().info("Shutting down!");
        INSTANCE.shutdown();
    }
}
