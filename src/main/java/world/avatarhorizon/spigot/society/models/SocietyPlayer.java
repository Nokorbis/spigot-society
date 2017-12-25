package world.avatarhorizon.spigot.society.models;

import org.bukkit.OfflinePlayer;

public class SocietyPlayer
{
    private OfflinePlayer player;
    private Society society;
    private float constitution;
    //TODO: Ranks

    public SocietyPlayer(OfflinePlayer player)
    {
        this.player = player;
    }

    public OfflinePlayer getPlayer()
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

    public float getConstitution()
    {
        return constitution;
    }

    public void setConstitution(float constitution)
    {
        this.constitution = constitution;
    }
}
