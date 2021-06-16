package edu.fpt.swp391.g2.imageexp.command;

import edu.fpt.swp391.g2.imageexp.ImageExpBoostrap;
import edu.fpt.swp391.g2.imageexp.ImageExpMain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * The base terminal command
 */
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public abstract class Command {
    /**
     * The name of the command
     */
    private final String name;

    /**
     * The aliases (or alternative names)
     */
    private List<String> aliases;

    /**
     * Call when running the command
     *
     * @param argument the argument
     */
    public abstract void runCommand(String argument);

    public void disable() {
        // EMPTY
    }

    /**
     * Get the logger
     *
     * @return the logger
     */
    public Logger getLogger() {
        return ImageExpBoostrap.INSTANCE.getLogger();
    }

    /**
     * Get the main instance
     *
     * @return the instance
     */
    public ImageExpMain getInstance() {
        return ImageExpBoostrap.INSTANCE;
    }


    /**
     * Get the format string which tells how to use the command
     *
     * @return the format string
     */
    public String getUsage() {
        return name;
    }

    /**
     * Get the description of the command
     *
     * @return the description
     */
    public String getDescription() {
        return "";
    }
}
