package world.avatarhorizon.spigot.society.persistence;

import org.bukkit.entity.Player;
import world.avatarhorizon.spigot.society.models.SocietyPlayer;

import java.util.List;

public interface ISocietyPlayerPersister
{
    public boolean save(SocietyPlayer player);
    public void delete(SocietyPlayer player);
    public List<SocietyPlayer> loadAll();
    public SocietyPlayer load(Player player);
}
