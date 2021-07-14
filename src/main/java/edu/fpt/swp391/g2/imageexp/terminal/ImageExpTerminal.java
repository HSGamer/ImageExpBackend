package edu.fpt.swp391.g2.imageexp.terminal;

import lombok.extern.log4j.Log4j2;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.terminal.impl.DumbTerminal;

import java.io.IOException;

import static edu.fpt.swp391.g2.imageexp.ImageExpBoostrap.INSTANCE;

/**
 * The terminal console, which also handles the incoming terminal commands
 */
@Log4j2
public class ImageExpTerminal {
    private Terminal terminal;
    private LineReader lineReader;

    /**
     * Init the terminal
     *
     * @throws IOException if there is an I/O error
     */
    public void init() throws IOException {
        terminal = TerminalBuilder.builder()
                .system(true)
                .jansi(true)
                .build();
        if (!(terminal instanceof DumbTerminal)) {
            lineReader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .build();
            lineReader.setOpt(LineReader.Option.DISABLE_EVENT_EXPANSION);
            lineReader.unsetOpt(LineReader.Option.INSERT_TAB);
        }
    }

    /**
     * Start the line reader
     */
    public void start() {
        while (isRunning()) {
            try {
                if (lineReader != null) {
                    String command = lineReader.readLine("> ");
                    if (command == null) {
                        break;
                    }
                    runCommand(command);
                }
            } catch (UserInterruptException e) {
                shutdown();
            } catch (EndOfFileException e) {
                // IGNORED
            }
        }
    }

    /**
     * Check if the server is running
     *
     * @return true if it is
     */
    private boolean isRunning() {
        return !INSTANCE.isShuttingDown();
    }

    /**
     * Execute the command
     *
     * @param command the command
     */
    private void runCommand(String command) {
        String[] split = command.split(" ", 2);
        if (!INSTANCE.getCommandManager().handleCommand(split[0], split.length > 1 ? split[1] : "")) {
            INSTANCE.getLogger().warn("No command was found");
        }
    }

    /**
     * Shut down the server
     */
    private void shutdown() {
        INSTANCE.getLogger().info("Shutting down!");
        INSTANCE.shutdown();
    }
}
