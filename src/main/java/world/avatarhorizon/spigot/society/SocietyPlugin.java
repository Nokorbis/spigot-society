package world.avatarhorizon.spigot.society;

import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import world.avatarhorizon.spigot.society.controllers.SocietyManager;

import java.util.logging.Logger;

public class SocietyPlugin extends JavaPlugin
{

    @Override
    public void onEnable()
    {
        Logger logger = getLogger();
        SocietyManager manager = new SocietyManager(logger);
        getServer().getServicesManager().register(SocietyManager.class,manager, this, ServicePriority.Normal);
    }

    @Override
    public void onDisable()
    {

    }
}
