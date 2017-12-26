package world.avatarhorizon.spigot.society.models;

import org.bukkit.OfflinePlayer;

public class SocietyPlayer
{
    private OfflinePlayer player;
    private Society society;
    private float constitution;
    private Ranks rank;

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

    public Ranks getRank()
    {
        return rank;
    }

    public void setRank(Ranks rank)
    {
        this.rank = rank;
    }
}
