package world.avatarhorizon.spigot.society;

import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import world.avatarhorizon.spigot.society.commands.SocietyCommandExecutor;
import world.avatarhorizon.spigot.society.controllers.SocietyManager;
import world.avatarhorizon.spigot.society.persistence.ISocietyPersister;
import world.avatarhorizon.spigot.society.persistence.JsonSocietyPersister;

import java.util.logging.Logger;

public class SocietyPlugin extends JavaPlugin
{

    @Override
    public void onEnable()
    {
        Logger logger = getLogger();

        ISocietyPersister societyPersister = new JsonSocietyPersister(getDataFolder(), logger);
        SocietyManager manager = new SocietyManager(societyPersister, logger);
        SocietyCommandExecutor executor = new SocietyCommandExecutor(manager, logger);

        getServer().getServicesManager().register(SocietyManager.class,manager, this, ServicePriority.Normal);
        getCommand("society").setExecutor(executor);
    }

    @Override
    public void onDisable()
    {
        getServer().getServicesManager().unregisterAll(this);
    }
}
