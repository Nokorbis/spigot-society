package world.avatarhorizon.spigot.society.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import world.avatarhorizon.spigot.society.models.Society;
import world.avatarhorizon.spigot.society.models.SocietyPlayer;

public class SocietyDisbandEvent extends Event
{
    private static final HandlerList handlers = new HandlerList();

    private Society society;
    private SocietyPlayer disbander;

    public SocietyDisbandEvent(Society society, SocietyPlayer disbander)
    {
        this.society = society;
        this.disbander = disbander;
    }

    public Society getSociety()
    {
        return society;
    }

    public SocietyPlayer getDisbander()
    {
        return disbander;
    }

    @Override
    public HandlerList getHandlers()
    {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
