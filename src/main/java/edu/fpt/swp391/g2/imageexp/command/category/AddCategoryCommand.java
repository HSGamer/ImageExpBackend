package edu.fpt.swp391.g2.imageexp.command.category;

import edu.fpt.swp391.g2.imageexp.command.Command;
import edu.fpt.swp391.g2.imageexp.processor.CategoryProcessor;
import org.apache.logging.log4j.Level;

import java.sql.SQLException;

/**
 * The command to add new category
 */
public class AddCategoryCommand extends Command {
    public AddCategoryCommand() {
        super("add-category");
    }

    @Override
    public void runCommand(String argument) {
        if (argument.isEmpty()) {
            getLogger().warn("The category name should not be empty");
            return;
        }
        try {
            if (CategoryProcessor.checkCategoryExists(argument)) {
                getLogger().warn("The category name already exists");
                return;
            }
            CategoryProcessor.addCategory(argument);
            getLogger().info("Successfully added");
        } catch (SQLException e) {
            getLogger().log(Level.WARN, "There is an SQL exception when getting data", e);
        }
    }

    @Override
    public String getUsage() {
        return super.getUsage() + " <category_name>";
    }

    @Override
    public String getDescription() {
        return "Add new category";
    }
}
