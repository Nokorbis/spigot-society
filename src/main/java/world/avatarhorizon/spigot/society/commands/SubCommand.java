package world.avatarhorizon.spigot.society.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.avatarhorizon.spigot.society.exceptions.SocietyCommandException;

import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public abstract class SubCommand
{
    private final String name;
    protected List<String> aliases;

    protected final Logger logger;
    protected final ResourceBundle messages;

    public SubCommand(String name, Logger logger, ResourceBundle messages)
    {
        this.name = name;
        this.logger = logger;
        this.messages = messages;
    }

    /**
     * Check if the given label matches the command.
     * @param label A string, supposed to be lowercased
     * @return <code>true</code> if the label matches the command. <code>false</code> otherwise
     */
    public boolean isCommand(String label)
    {
        if (name.equals(label))
        {
            return true;
        }
        if (aliases != null)
        {
            for (String alias : aliases)
            {
                if (alias.equals(label))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public abstract void execute(CommandSender sender, List<String> args) throws SocietyCommandException;

    public void sendHelp(CommandSender sender)
    {
        sender.sendMessage(messages.getString(getHelpKey()));
    }

    protected abstract String getHelpKey();
    protected void validatePlayer(CommandSender sender) throws SocietyCommandException
    {
        if (!(sender instanceof Player))
        {
            throw new SocietyCommandException(messages.getString("error.player_requirement"));
        }
    }

    protected void validatePermission(CommandSender sender, String perm) throws SocietyCommandException
    {
        if (!sender.hasPermission(perm))
        {
            throw new SocietyCommandException(messages.getString("error.no_permission"));
        }
    }
}
