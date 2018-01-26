package world.avatarhorizon.spigot.society.persistence.serializer;

import com.google.gson.*;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import world.avatarhorizon.spigot.society.models.SocietyPlayer;

import java.lang.reflect.Type;
import java.util.UUID;

public class SocietyPlayerSerializer implements JsonSerializer<SocietyPlayer>, JsonDeserializer<SocietyPlayer>
{
    @Override
    public SocietyPlayer deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException
    {
        JsonObject root = (JsonObject) element;

        UUID id = UUID.fromString(root.get("id").getAsString());
        OfflinePlayer player = Bukkit.getOfflinePlayer(id);
        SocietyPlayer socPlayer = new SocietyPlayer(player);
        socPlayer.setConstitution(root.get("constitution").getAsFloat());

        return socPlayer;
    }

    @Override
    public JsonElement serialize(SocietyPlayer player, Type type, JsonSerializationContext context)
    {
        JsonObject root = new JsonObject();

        root.addProperty("id", player.getPlayer().getUniqueId().toString());
        root.addProperty("name", player.getPlayer().getName());
        root.addProperty("constitution", player.getConstitution());
        if (player.getSociety() != null)
        {
            root.addProperty("society", player.getSociety().getId().toString());
        }

        return root;
    }
}
