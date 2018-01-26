package world.avatarhorizon.spigot.society.controllers;


import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import world.avatarhorizon.spigot.society.events.SocietyCreateEvent;
import world.avatarhorizon.spigot.society.events.SocietyDisbandEvent;
import world.avatarhorizon.spigot.society.exceptions.ExceptionCause;
import world.avatarhorizon.spigot.society.exceptions.SocietyManagementException;
import world.avatarhorizon.spigot.society.models.Ranks;
import world.avatarhorizon.spigot.society.models.Society;
import world.avatarhorizon.spigot.society.models.SocietyPlayer;
import world.avatarhorizon.spigot.society.persistence.ISocietyPersister;
import world.avatarhorizon.spigot.society.persistence.ISocietyPlayerPersister;

import java.util.*;
import java.util.logging.Logger;

public class SocietyManager
{
    private Logger logger;
    private ISocietyPersister societyPersister;
    private ISocietyPlayerPersister playerPersister;

    private List<Society> societies;
    private Map<UUID, SocietyPlayer> players;

    //TODO: on login : read file and update societyPlayer with proper constitution

    public SocietyManager(ISocietyPersister societyPersister, ISocietyPlayerPersister playerPersister, Logger logger)
    {
        this.logger = logger;
        this.societyPersister = societyPersister;
        this.playerPersister = playerPersister;
        this.loadData();
    }

    private void loadData()
    {
        this.societies = this.societyPersister.loadAll();
        this.societies.sort(Comparator.comparing(Society::getName));
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

    /**
     * Get the society matching this name (ignoring case)
     * @param societyName The name of the society
     * @return a <code>Society</code> if one was one. <br> <code>null</code> otherwise
     */
    public Society getSociety(String societyName)
    {
        societyName = societyName.trim().toLowerCase();
        for (Society society : societies)
        {
            if (society.getName().toLowerCase().equals(societyName))
            {
                return society;
            }
        }
        return null;
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

        SocietyPlayer socCreator = getSocietyPlayer(creator);
        if (socCreator.getSociety() != null)
        {
            throw new SocietyManagementException(ExceptionCause.ALREADY_IN_SOCIETY);
        }

        if (containsName(societyName))
        {
            throw new SocietyManagementException(ExceptionCause.SOCIETY_NAME_USED);
        }

        Society soc = new Society();
        soc.setName(societyName);
        soc.addMember(socCreator);

        socCreator.setSociety(soc);
        socCreator.setRank(Ranks.LEADER);

        this.societies.add(soc);
        this.societies.sort(Comparator.comparing(Society::getName));
        this.societyPersister.save(soc);
        Bukkit.getPluginManager().callEvent(new SocietyCreateEvent(soc, socCreator));
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
        societyName = societyName.trim().toLowerCase();
        for (Society society : societies)
        {
            if (society.getName().toLowerCase().equals(societyName))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the society data linked to that player.
     * @param player A Player of the server
     * @return a SocietyPlayer containing the society data of a player.
     */
    public SocietyPlayer getSocietyPlayer(OfflinePlayer player)
    {
        SocietyPlayer socPlayer = players.get(player.getUniqueId());
        if (socPlayer == null)
        {
            socPlayer = new SocietyPlayer(player);
            socPlayer.setConstitution(100.0f);
            socPlayer.setRank(Ranks.RECRUIT);
            players.put(player.getUniqueId(), socPlayer);
            this.playerPersister.save(socPlayer);
        }
        return socPlayer;
    }

    public void disbandSociety(Society soc, SocietyPlayer socPlayer)
    {
        if (soc != null)
        {
            this.societies.remove(soc);
            this.societyPersister.delete(soc);
            Bukkit.getPluginManager().callEvent(new SocietyDisbandEvent(soc, socPlayer));
        }
    }

    public void leaveSociety(SocietyPlayer societyPlayer)
    {
        societyPlayer.setRank(Ranks.RECRUIT);
        societyPlayer.setSociety(null);
        this.playerPersister.save(societyPlayer);
    }
}
