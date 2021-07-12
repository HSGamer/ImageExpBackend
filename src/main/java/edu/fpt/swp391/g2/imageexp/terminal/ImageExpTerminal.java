package edu.fpt.swp391.g2.imageexp.terminal;

import lombok.extern.log4j.Log4j2;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

import static edu.fpt.swp391.g2.imageexp.ImageExpBoostrap.INSTANCE;

/**
 * The terminal console, which also handles the incoming terminal commands
 */
@Log4j2
public class ImageExpTerminal {
    private Terminal terminal;
    private LineReader lineReader;

    public void init() throws IOException {
        terminal = TerminalBuilder.builder()
                .system(true)
                .jansi(true)
                .dumb(System.getProperty("java.class.path").contains("idea_rt.jar"))
                .build();
        lineReader = LineReaderBuilder.builder()
                .terminal(terminal)
                .build();
        lineReader.setOpt(LineReader.Option.DISABLE_EVENT_EXPANSION);
        lineReader.unsetOpt(LineReader.Option.INSERT_TAB);
    }

    public void start() {
        while (isRunning()) {
            try {
                String command = lineReader.readLine("> ");
                if (command == null) {
                    break;
                }
                runCommand(command);
            } catch (UserInterruptException e) {
                shutdown();
            } catch (EndOfFileException e) {
                // IGNORED
            }
        }
    }

    private boolean isRunning() {
        return !INSTANCE.isShuttingDown();
    }

    private void runCommand(String command) {
        String[] split = command.split(" ", 2);
        if (!INSTANCE.getCommandManager().handleCommand(split[0], split.length > 1 ? split[1] : "")) {
            INSTANCE.getLogger().warn("No command was found");
        }
    }

    private void shutdown() {
        INSTANCE.getLogger().info("Shutting down!");
        INSTANCE.shutdown();
    }
}
