package world.avatarhorizon.spigot.society.commands.implementations;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.avatarhorizon.spigot.society.commands.SubCommand;
import world.avatarhorizon.spigot.society.controllers.SocietyManager;
import world.avatarhorizon.spigot.society.exceptions.SocietyCommandException;
import world.avatarhorizon.spigot.society.exceptions.SocietyManagementException;
import world.avatarhorizon.spigot.society.models.SocietyPlayer;

import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class CmdCreate extends SubCommand
{
    private SocietyManager manager;

    public CmdCreate(SocietyManager manager, Logger logger, ResourceBundle messages)
    {
        super("create", logger, messages);
        this.manager = manager;
    }

    @Override
    public void execute(CommandSender sender, List<String> args) throws SocietyCommandException
    {
        validatePermission(sender, "society.commands.create");
        validatePlayer(sender);

        if (args.size() < 1)
        {
            throw new SocietyCommandException(messages.getString("error.create.name_required"));
        }

        String name = args.remove(0);
        try
        {
            manager.createSociety(name, (Player) sender);
            sender.sendMessage(messages.getString("success.create"));
            logger.info("Society " + name + " created.");
        }
        catch (SocietyManagementException e)
        {
            throw new SocietyCommandException(messages.getString(e.getExceptionCause().getKey()));
        }
    }

    @Override
    protected String getHelpKey()
    {
        return "help.create";
    }
}
