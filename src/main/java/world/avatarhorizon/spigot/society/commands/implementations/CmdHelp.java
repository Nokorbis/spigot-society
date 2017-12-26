package world.avatarhorizon.spigot.society.commands.implementations;

import org.bukkit.command.CommandSender;
import world.avatarhorizon.spigot.society.commands.SubCommand;
import world.avatarhorizon.spigot.society.exceptions.SocietyCommandException;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class CmdHelp extends SubCommand
{
    private final List<SubCommand> commands;

    public CmdHelp(Logger logger, ResourceBundle messages, List<SubCommand> commands)
    {
        super("help", logger, messages);
        this.commands = commands;
        this.aliases = new ArrayList<>(2);
        this.aliases.add("?");
        this.aliases.add("h");
    }

    @Override
    public void execute(CommandSender sender, List<String> args) throws SocietyCommandException
    {
        validatePermission(sender, "society.commands.help");
        validatePlayer(sender);

        if (args.isEmpty())
        {
            for (SubCommand command : commands)
            {
                command.sendHelp(sender);
            }
        }
        else
        {
            boolean found = false;
            String action = args.remove(0);
            for (SubCommand command : commands)
            {
                if (command.isCommand(action))
                {
                    command.sendHelp(sender);
                    found = true;
                    break;
                }
            }
            if (!found)
            {
                sender.sendMessage(messages.getString("error.help.not_found"));
            }
        }
    }

    @Override
    protected String getHelpKey()
    {
        return "help.help";
    }
}
