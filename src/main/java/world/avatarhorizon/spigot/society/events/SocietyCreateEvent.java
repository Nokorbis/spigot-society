package world.avatarhorizon.spigot.society.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import world.avatarhorizon.spigot.society.models.Society;
import world.avatarhorizon.spigot.society.models.SocietyPlayer;


public class SocietyCreateEvent extends Event
{
    private static final HandlerList handlers = new HandlerList();

    private Society society;
    private SocietyPlayer creator;

    public SocietyCreateEvent(Society society, SocietyPlayer creator)
    {
        this.society = society;
        this.creator = creator;
    }

    @Override
    public HandlerList getHandlers()
    {
        return handlers;
    }

    public Society getSociety()
    {
        return society;
    }

    public SocietyPlayer getCreator()
    {
        return creator;
    }
}
