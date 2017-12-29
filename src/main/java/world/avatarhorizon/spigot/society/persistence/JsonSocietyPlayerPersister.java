package world.avatarhorizon.spigot.society.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.entity.Player;
import world.avatarhorizon.spigot.society.models.Society;
import world.avatarhorizon.spigot.society.models.SocietyPlayer;
import world.avatarhorizon.spigot.society.persistence.serializer.SocietyPlayerSerializer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class JsonSocietyPlayerPersister implements ISocietyPlayerPersister
{
    private static final String DATA_FOLDER_NAME = "data";
    private static final String PLAYERS_FOLDER_NAME = "PLAYERS";

    private final Logger logger;

    private final File dataFolder;
    private final File playersFolder;

    private Gson gson;

    public JsonSocietyPlayerPersister(File pluginFolder, Logger logger)
    {
        this.logger = logger;
        this.dataFolder = new File(pluginFolder, DATA_FOLDER_NAME);
        this.playersFolder = new File(this.dataFolder, PLAYERS_FOLDER_NAME);
        if (!playersFolder.exists())
        {
            playersFolder.mkdirs();
        }

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Society.class, new SocietyPlayerSerializer());
        builder.setPrettyPrinting();

        this.gson = builder.create();
    }

    @Override
    public boolean save(SocietyPlayer player)
    {
        File playerFile = new File(playersFolder, player.getPlayer().getUniqueId().toString()+".json");
        if (!playerFile.exists())
        {
            try
            {
                playerFile.createNewFile();
            }
            catch (IOException e)
            {
                logger.severe("Unable to create save file for " + player.getPlayer().getName());
                logger.severe(e.getMessage());
                return false;
            }
        }

        try (OutputStream fos = new FileOutputStream(playerFile))
        {
            OutputStreamWriter writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            String json = gson.toJson(player);
            writer.write(json);
            writer.close();
        }
        catch (IOException e)
        {
            logger.severe("IOException while writing player file");
            logger.severe(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public void delete(SocietyPlayer player)
    {
        File file = new File(playersFolder, player.getPlayer().getUniqueId().toString() + ".json");
        if (file.exists())
        {
            file.delete();
        }
    }

    @Override
    public List<SocietyPlayer> loadAll()
    {
        List<SocietyPlayer> players = new LinkedList<>();

        File[] files = playersFolder.listFiles();
        if (files != null)
        {
            for (File file : files)
            {
                if (file.isFile())
                {
                    try (InputStream is = new FileInputStream(file))
                    {
                        InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
                        SocietyPlayer soc = gson.fromJson(reader, SocietyPlayer.class);
                        players.add(soc);
                    }
                    catch (IOException e)
                    {
                        logger.warning(e.getMessage());
                    }
                }
            }
        }

        return players;
    }

    @Override
    public SocietyPlayer load(Player player)
    {
        File file = new File(playersFolder, player.getUniqueId().toString() + ".json");
        if (file.exists() && file.isFile())
        {
            try (InputStream is = new FileInputStream(file))
            {
                InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
                SocietyPlayer p = gson.fromJson(reader, SocietyPlayer.class);
                return p;
            }
            catch (IOException e)
            {
                logger.warning(e.getMessage());
            }
        }
        return null;
    }
}
