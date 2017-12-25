package world.avatarhorizon.spigot.society.persistence;

import world.avatarhorizon.spigot.society.models.Society;

import java.util.List;

public interface ISocietyPersister
{
    public boolean save(Society society);
    public List<Society> loadAll();
    public void delete(Society society);
}
