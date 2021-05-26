package edu.fpt.swp391.g2.imageexp.command;

import edu.fpt.swp391.g2.imageexp.ImageExpBoostrap;
import edu.fpt.swp391.g2.imageexp.ImageExpMain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public abstract class Command {
    private final String name;
    private List<String> aliases;

    public abstract void runCommand(String argument);

    public void disable() {
        // EMPTY
    }

    public Logger getLogger() {
        return ImageExpBoostrap.INSTANCE.getLogger();
    }

    public ImageExpMain getInstance() {
        return ImageExpBoostrap.INSTANCE;
    }

    public String getUsage() {
        return name;
    }

    public String getDescription() {
        return "";
    }
}
