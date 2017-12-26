package world.avatarhorizon.spigot.society.commands.implementations;

import org.bukkit.command.CommandSender;
import world.avatarhorizon.spigot.society.commands.SubCommand;
import world.avatarhorizon.spigot.society.controllers.SocietyManager;
import world.avatarhorizon.spigot.society.exceptions.SocietyCommandException;
import world.avatarhorizon.spigot.society.models.Society;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class CmdList extends SubCommand
{
    private static final int PAGE_SIZE = 5;

    private final SocietyManager manager;

    public CmdList(SocietyManager manager, Logger logger, ResourceBundle messages)
    {
        super("list", logger, messages);
        this.aliases = new ArrayList<>(1);
        this.aliases.add("l");
        this.manager = manager;
    }

    @Override
    public void execute(CommandSender sender, List<String> args) throws SocietyCommandException
    {
        validatePermission(sender, "society.commands.list");
        validatePlayer(sender);

        int page = getPageNumber(args);
        sender.sendMessage(messages.getString("success.list").replace("{NUMBER}", String.valueOf(page+1)));
        manager.getSocieties().stream()
                    .filter(Society::isVisibleInList)
                    .sorted(Comparator.comparing(Society::getName))
                    .skip(page * PAGE_SIZE)
                    .limit(PAGE_SIZE)
                    .forEach((s) ->
                    {
                        sender.sendMessage("- " + s.getName());
                    });
    }

    private int getPageNumber(List<String> args) throws SocietyCommandException
    {
        if (!args.isEmpty())
        {
            int page;
            try
            {
                page = Integer.parseInt(args.remove(0)) - 1;
            }
            catch (NumberFormatException e)
            {
                throw new SocietyCommandException(messages.getString("error.list.page_number"));
            }
            if (page < 0)
            {
                page = 0;
            }
            return page;
        }
        else
        {
            return 0;
        }
    }

    @Override
    protected String getHelpKey()
    {
        return "help.list";
    }
}
