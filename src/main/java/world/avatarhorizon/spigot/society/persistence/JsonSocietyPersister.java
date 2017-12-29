package world.avatarhorizon.spigot.society.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import world.avatarhorizon.spigot.society.models.Society;
import world.avatarhorizon.spigot.society.persistence.serializer.SocietySerializer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class JsonSocietyPersister implements ISocietyPersister
{
    private static final String DATA_FOLDER_NAME = "data";
    private static final String SOCIETY_FOLDER_NAME = "societies";

    private final Logger logger;

    private final File dataFolder;
    private final File societiesFolder;

    private Gson gson;

    public JsonSocietyPersister(File pluginFolder, Logger logger)
    {
        this.logger = logger;
        this.dataFolder = new File(pluginFolder, DATA_FOLDER_NAME);
        this.societiesFolder = new File(this.dataFolder, SOCIETY_FOLDER_NAME);
        if (!societiesFolder.exists())
        {
            societiesFolder.mkdirs();
        }

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Society.class, new SocietySerializer(logger));
        builder.setPrettyPrinting();

        this.gson = builder.create();
    }

    @Override
    public boolean save(Society society)
    {
        File societyFile = new File(societiesFolder, society.getId().toString()+".json");
        if (!societyFile.exists())
        {
            try
            {
                societyFile.createNewFile();
            }
            catch (IOException e)
            {
                logger.severe("Unable to create save file for " + society.getName());
                logger.severe(e.getMessage());
                return false;
            }
        }

        try (OutputStream fos = new FileOutputStream(societyFile))
        {
            OutputStreamWriter writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            String json = gson.toJson(society);
            writer.write(json);
            writer.close();
        }
        catch (IOException e)
        {
            logger.severe("IOException while writing society file");
            logger.severe(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<Society> loadAll()
    {
        List<Society> societies = new LinkedList<>();

        File[] files = societiesFolder.listFiles();
        if (files != null)
        {
            for (File file : files)
            {
                if (file.isFile())
                {
                    try (InputStream is = new FileInputStream(file))
                    {
                        InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
                        Society soc = gson.fromJson(reader, Society.class);
                        societies.add(soc);
                    }
                    catch (IOException e)
                    {
                        logger.warning(e.getMessage());
                    }
                }
            }
        }

        return societies;
    }

    @Override
    public void delete(Society society)
    {
        File file = new File(societiesFolder, society.getId().toString() + ".json");
        if (file.exists())
        {
            file.delete();
        }
    }
}
