package world.avatarhorizon.spigot.society.commands.implementations;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.avatarhorizon.spigot.society.commands.SubCommand;
import world.avatarhorizon.spigot.society.controllers.SocietyManager;
import world.avatarhorizon.spigot.society.exceptions.SocietyCommandException;
import world.avatarhorizon.spigot.society.models.Ranks;
import world.avatarhorizon.spigot.society.models.Society;
import world.avatarhorizon.spigot.society.models.SocietyPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class CmdDisband extends SubCommand
{
    private SocietyManager manager;

    public CmdDisband(SocietyManager manager, Logger logger, ResourceBundle messages)
    {
        super("disband", logger, messages);
        this.manager = manager;
        this.aliases = new ArrayList<>(1);
        this.aliases.add("dis");
    }

    @Override
    public void execute(CommandSender sender, List<String> args) throws SocietyCommandException
    {
        validatePermission(sender, "society.commands.disband");
        validatePlayer(sender);

        SocietyPlayer player = manager.getSocietyPlayer((Player) sender);
        Society soc;
        if (!args.isEmpty())
        {
            String name = args.remove(0);
            soc = manager.getSociety(name);
            if (soc == null)
            {
                throw new SocietyCommandException(messages.getString("error.society.not_found"));
            }
        }
        else
        {
            soc = player.getSociety();
            if (soc == null)
            {
                throw new SocietyCommandException(messages.getString("error.society.not_found"));
            }
        }
        if ((soc.equals(player.getSociety()) && player.getRank() == Ranks.LEADER) || sender.hasPermission("society.admin.disband"))
        {
            manager.disbandSociety(soc, player);
        }
        else
        {
            throw new SocietyCommandException(messages.getString("error.no_permission"));
        }
    }

    @Override
    protected String getHelpKey()
    {
        return "help.disband";
    }
}
