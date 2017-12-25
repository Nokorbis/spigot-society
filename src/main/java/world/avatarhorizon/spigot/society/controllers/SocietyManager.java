package world.avatarhorizon.spigot.society.controllers;


import world.avatarhorizon.spigot.society.exceptions.ExceptionCause;
import world.avatarhorizon.spigot.society.exceptions.SocietyManagementException;
import world.avatarhorizon.spigot.society.models.Society;
import world.avatarhorizon.spigot.society.persistence.ISocietyPersister;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class SocietyManager
{
    private Logger logger;
    private ISocietyPersister societyPersister;

    private List<Society> societies;

    public SocietyManager(ISocietyPersister societyPersister, Logger logger)
    {
        this.logger = logger;
        this.societyPersister = societyPersister;
        this.societies = new LinkedList<>();
    }

    private void loadData()
    {
        this.societies = this.societyPersister.loadAll();
    }

    public void createSociety(String societyName) throws SocietyManagementException
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
}
