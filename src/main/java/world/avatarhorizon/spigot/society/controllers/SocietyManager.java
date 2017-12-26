package world.avatarhorizon.spigot.society.controllers;


import org.bukkit.entity.Player;
import world.avatarhorizon.spigot.society.exceptions.ExceptionCause;
import world.avatarhorizon.spigot.society.exceptions.SocietyManagementException;
import world.avatarhorizon.spigot.society.models.Ranks;
import world.avatarhorizon.spigot.society.models.Society;
import world.avatarhorizon.spigot.society.models.SocietyPlayer;
import world.avatarhorizon.spigot.society.persistence.ISocietyPersister;

import java.util.*;
import java.util.logging.Logger;

public class SocietyManager
{
    private Logger logger;
    private ISocietyPersister societyPersister;

    private List<Society> societies;
    private Map<UUID, SocietyPlayer> players;

    //TODO: on login : read file and update societyPlayer with proper constitution

    public SocietyManager(ISocietyPersister societyPersister, Logger logger)
    {
        this.logger = logger;
        this.societyPersister = societyPersister;
        this.loadData();
    }

    private void loadData()
    {
        this.societies = this.societyPersister.loadAll();
        this.players = new HashMap<>();
        for (Society society : this.societies)
        {
            for (SocietyPlayer player : society.getMembers())
            {
                this.players.put(player.getPlayer().getUniqueId(), player);
            }
        }
    }

    /**
     * Get the societies of the server
     * @return a unmodifiable collection of the societies
     */
    public List<Society> getSocieties()
    {
        return Collections.unmodifiableList(this.societies);
    }

    public void createSociety(String societyName, Player creator) throws SocietyManagementException
    {
        if (societyName == null)
        {
            throw new SocietyManagementException(ExceptionCause.INVALID_NAME);
        }

        societyName = societyName.trim();
        if (societyName.equals(""))
        {
            throw new SocietyManagementException(ExceptionCause.INVALID_NAME);
        }

        if (containsName(societyName))
        {
            throw new SocietyManagementException(ExceptionCause.SOCIETY_NAME_USED);
        }

        Society soc = new Society();
        soc.setName(societyName);
        SocietyPlayer socCreator = getSocietyPlayer(creator);
        socCreator.setRank(Ranks.LEADER);
        soc.addMember(socCreator);
        societies.add(soc);
        societyPersister.save(soc);
    }

    /**
     * Check if there is an existing society with the given name. The check ignore the case
     * @param societyName The name to be tested
     * @return <code>true</code> if there is an existing society with the name. <br><code>false</code> otherwise.
     */
    public boolean containsName(String societyName)
    {
        if (societyName == null)
        {
            return false;
        }
        societyName = societyName.toLowerCase();
        for (Society society : societies)
        {
            if (society.getName().toLowerCase().equals(societyName))
            {
                return true;
            }
        }
        return false;
    }

    public SocietyPlayer getSocietyPlayer(Player player)
    {
        SocietyPlayer socPlayer = players.get(player.getUniqueId());
        if (socPlayer == null)
        {
            socPlayer = new SocietyPlayer(player);
            socPlayer.setConstitution(100.0f);
            players.put(player.getUniqueId(), socPlayer);
        }
        return socPlayer;
    }
}
