package edu.fpt.swp391.g2.imageexp.command.category;

import edu.fpt.swp391.g2.imageexp.command.Command;
import edu.fpt.swp391.g2.imageexp.entity.Category;
import edu.fpt.swp391.g2.imageexp.processor.CategoryProcessor;
import org.apache.logging.log4j.Level;

import java.sql.SQLException;
import java.util.List;

public class GetAllCategoriesCommand extends Command {
    public GetAllCategoriesCommand() {
        super("get-all-categories");
    }

    @Override
    public void runCommand(String argument) {
        try {
            List<Category> categoryList = CategoryProcessor.getAllCategories();
            categoryList.forEach(getLogger()::info);
        } catch (SQLException e) {
            getLogger().log(Level.WARN, "There is an SQL exception when getting data", e);
        }
    }
}
