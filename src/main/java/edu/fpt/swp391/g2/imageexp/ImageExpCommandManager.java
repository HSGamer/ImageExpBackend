package edu.fpt.swp391.g2.imageexp;

import edu.fpt.swp391.g2.imageexp.command.Command;
import edu.fpt.swp391.g2.imageexp.command.HelpCommand;
import edu.fpt.swp391.g2.imageexp.command.category.AddCategoryCommand;
import edu.fpt.swp391.g2.imageexp.command.category.GetAllCategoriesCommand;
import edu.fpt.swp391.g2.imageexp.command.comment.DeleteCommentCommand;
import edu.fpt.swp391.g2.imageexp.command.comment.GetCommentByIdCommand;
import edu.fpt.swp391.g2.imageexp.command.comment.GetCommentByPostIdCommand;
import edu.fpt.swp391.g2.imageexp.command.comment.GetCommentByUserIdCommand;
import edu.fpt.swp391.g2.imageexp.command.misc.ChangeTextCommand;
import edu.fpt.swp391.g2.imageexp.command.misc.EchoCommand;
import edu.fpt.swp391.g2.imageexp.command.misc.TestEmailCommand;
import edu.fpt.swp391.g2.imageexp.command.picture.DeletePictureCommand;
import edu.fpt.swp391.g2.imageexp.command.picture.GetAllPictureCommand;
import edu.fpt.swp391.g2.imageexp.command.picture.GetPictureByUserIdCommand;
import edu.fpt.swp391.g2.imageexp.command.post.*;
import edu.fpt.swp391.g2.imageexp.command.system.ReloadCommand;
import edu.fpt.swp391.g2.imageexp.command.system.StopCommand;
import edu.fpt.swp391.g2.imageexp.command.user.*;
import me.hsgamer.hscore.collections.map.CaseInsensitiveStringHashMap;

import java.util.Collection;
import java.util.Map;

/**
 * The command manager, which stores all terminal commands
 */
public class ImageExpCommandManager {
    private final Map<String, Command> commands = new CaseInsensitiveStringHashMap<>();

    public ImageExpCommandManager() {
        // Help
        addCommand(new HelpCommand());

        // System
        addCommand(new StopCommand());
        addCommand(new ReloadCommand());

        // Misc
        addCommand(new EchoCommand());
        addCommand(new ChangeTextCommand());
        addCommand(new TestEmailCommand());

        // User
        addCommand(new GetUserByIdCommand());
        addCommand(new LoginUserCommand());
        addCommand(new RegisterUserCommand());
        addCommand(new GetAllUsersCommand());
        addCommand(new GetUserByEmailCommand());
        addCommand(new GetStatusCommand());
        addCommand(new GetVerifyStateCommand());

        // Category
        addCommand(new GetAllCategoriesCommand());
        addCommand(new AddCategoryCommand());

        // Post
        addCommand(new DeletePostCommand());
        addCommand(new GetAllPostsCommand());
        addCommand(new GetPostsByUserIdCommand());
        addCommand(new GetPostsByCategoryIdCommand());
        addCommand(new GetPostByIdCommand());

        // Picture
        addCommand(new DeletePictureCommand());
        addCommand(new GetAllPictureCommand());
        addCommand(new GetPictureByUserIdCommand());

        //Comment
        addCommand(new DeleteCommentCommand());
        addCommand(new GetCommentByIdCommand());
        addCommand(new GetCommentByPostIdCommand());
        addCommand(new GetCommentByUserIdCommand());
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
