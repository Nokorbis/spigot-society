package world.avatarhorizon.spigot.society;

import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import world.avatarhorizon.spigot.society.commands.SocietyCommandExecutor;
import world.avatarhorizon.spigot.society.controllers.SocietyListener;
import world.avatarhorizon.spigot.society.controllers.SocietyManager;
import world.avatarhorizon.spigot.society.persistence.ISocietyPersister;
import world.avatarhorizon.spigot.society.persistence.ISocietyPlayerPersister;
import world.avatarhorizon.spigot.society.persistence.JsonSocietyPersister;
import world.avatarhorizon.spigot.society.persistence.JsonSocietyPlayerPersister;

import java.util.logging.Logger;

public class SocietyPlugin extends JavaPlugin
{

    @Override
    public void onEnable()
    {
        Logger logger = getLogger();

        ISocietyPersister societyPersister = new JsonSocietyPersister(getDataFolder(), logger);
        ISocietyPlayerPersister playerPersister = new JsonSocietyPlayerPersister(getDataFolder(), logger);
        SocietyManager manager = new SocietyManager(societyPersister, playerPersister, logger);
        SocietyCommandExecutor executor = new SocietyCommandExecutor(manager, logger);
        SocietyListener listener = new SocietyListener(manager);

        getServer().getServicesManager().register(SocietyManager.class,manager, this, ServicePriority.Normal);
        getServer().getPluginManager().registerEvents(listener, this);
        getCommand("society").setExecutor(executor);
    }

    @Override
    public void onDisable()
    {
        getServer().getServicesManager().unregisterAll(this);
    }
}
