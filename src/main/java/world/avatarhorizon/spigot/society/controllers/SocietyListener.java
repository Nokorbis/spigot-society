package world.avatarhorizon.spigot.society.controllers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import world.avatarhorizon.spigot.society.events.SocietyDisbandEvent;
import world.avatarhorizon.spigot.society.models.SocietyPlayer;

import java.util.ResourceBundle;

public class SocietyListener implements Listener
{
    public SocietyManager manager;
    private ResourceBundle messages;

    public SocietyListener(SocietyManager manager)
    {
        this.manager = manager;
        this.messages = ResourceBundle.getBundle("messages/events");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSocietyDisband(SocietyDisbandEvent event)
    {
        String msg = messages.getString("society.disband").replace("{NAME}", event.getSociety().getName());
        for (SocietyPlayer societyPlayer : event.getSociety().getMembers())
        {
            if (societyPlayer.getPlayer().isOnline())
            {
                Player p = (Player) societyPlayer.getPlayer();
                p.sendMessage(msg);
            }
            manager.leaveSociety(societyPlayer);
        }
    }
}
