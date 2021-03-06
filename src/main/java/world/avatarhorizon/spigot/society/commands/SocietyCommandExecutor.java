package world.avatarhorizon.spigot.society.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import world.avatarhorizon.spigot.society.commands.implementations.CmdCreate;
import world.avatarhorizon.spigot.society.commands.implementations.CmdDisband;
import world.avatarhorizon.spigot.society.commands.implementations.CmdHelp;
import world.avatarhorizon.spigot.society.commands.implementations.CmdList;
import world.avatarhorizon.spigot.society.controllers.SocietyManager;
import world.avatarhorizon.spigot.society.exceptions.SocietyCommandException;

import java.util.*;
import java.util.logging.Logger;

public class SocietyCommandExecutor implements CommandExecutor
{
    private final List<SubCommand> subCommands;

    public SocietyCommandExecutor(SocietyManager manager, Logger logger)
    {
        this.subCommands = new ArrayList<>(8);

        ResourceBundle messages = ResourceBundle.getBundle("messages/commands");

        this.subCommands.add(new CmdCreate(manager, logger, messages));
        this.subCommands.add(new CmdDisband(manager, logger, messages));
        this.subCommands.add(new CmdList(manager, logger, messages));
        this.subCommands.add(new CmdHelp(logger, messages, subCommands));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length < 1)
        {
            return false;
        }

        List<String> argsList = new LinkedList<>(Arrays.asList(args));

        String sub = argsList.remove(0).toLowerCase();

        for (SubCommand subCommand : subCommands)
        {
            if (subCommand.isCommand(sub))
            {
                try
                {
                    subCommand.execute(sender, argsList);
                }
                catch (SocietyCommandException ex)
                {
                    sender.sendMessage(ex.getMessage());
                }
                return true;
            }
        }
        return false;
    }
}
