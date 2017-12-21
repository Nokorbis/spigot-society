package world.avatarhorizon.spigot.society.models;

import org.bukkit.entity.Player;

public class SocietyPlayer
{
    private Player player;
    private Society society;

    public SocietyPlayer(Player player)
    {
        this.player = player;
    }

    public Player getPlayer()
    {
        return this.player;
    }

    public Society getSociety()
    {
        return society;
    }

    public void setSociety(Society society)
    {
        this.society = society;
    }
}
