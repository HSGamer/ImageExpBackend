package edu.fpt.swp391.g2.imageexp;

import edu.fpt.swp391.g2.imageexp.command.Command;
import edu.fpt.swp391.g2.imageexp.command.HelpCommand;
import edu.fpt.swp391.g2.imageexp.command.category.AddCategoryCommand;
import edu.fpt.swp391.g2.imageexp.command.category.GetAllCategoriesCommand;
import edu.fpt.swp391.g2.imageexp.command.misc.ChangeTextCommand;
import edu.fpt.swp391.g2.imageexp.command.misc.EchoCommand;
import edu.fpt.swp391.g2.imageexp.command.post.DeletePostCommand;
import edu.fpt.swp391.g2.imageexp.command.post.GetAllPostsCommand;
import edu.fpt.swp391.g2.imageexp.command.post.GetPostsByCategoryIdCommand;
import edu.fpt.swp391.g2.imageexp.command.post.GetPostsByUserIdCommand;
import edu.fpt.swp391.g2.imageexp.command.system.ReloadCommand;
import edu.fpt.swp391.g2.imageexp.command.system.StopCommand;
import edu.fpt.swp391.g2.imageexp.command.user.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * The command manager, which stores all terminal commands
 */
public class ImageExpCommandManager {
    private final Map<String, Command> commands = new HashMap<>();

    public ImageExpCommandManager() {
        // Help
        addCommand(new HelpCommand());

        // System
        addCommand(new StopCommand());
        addCommand(new ReloadCommand());

        // Misc
        addCommand(new EchoCommand());
        addCommand(new ChangeTextCommand());

        // User
        addCommand(new GetUserByIdCommand());
        addCommand(new LoginUserCommand());
        addCommand(new RegisterUserCommand());
        addCommand(new GetAllUsersCommand());
        addCommand(new GetUserByEmailCommand());

        // Category
        addCommand(new GetAllCategoriesCommand());
        addCommand(new AddCategoryCommand());

        // Post
        addCommand(new DeletePostCommand());
        addCommand(new GetAllPostsCommand());
        addCommand(new GetPostsByUserIdCommand());
        addCommand(new GetPostsByCategoryIdCommand());
    }

    /**
     * Add a command to the manager
     *
     * @param command the command
     */
    public void addCommand(Command command) {
        commands.put(command.getName(), command);
        if (command.getAliases() != null) {
            command.getAliases().forEach(s -> commands.put(s, command));
        }
    }

    /**
     * Call when disabling the service
     */
    public void disable() {
        commands.values().forEach(Command::disable);
        commands.clear();
    }

    /**
     * Call when a command is typed
     *
     * @param command  the command
     * @param argument the argument of the command, guaranteed to be not null
     * @return whether the command was found and run
     */
    public boolean handleCommand(String command, String argument) {
        if (commands.containsKey(command)) {
            commands.get(command).runCommand(argument);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get the list of commands
     *
     * @return the commands
     */
    public Collection<Command> getCommands() {
        return commands.values();
    }
}
