package world.avatarhorizon.spigot.society.persistence.serializer;

import com.google.gson.*;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import world.avatarhorizon.spigot.society.models.Society;
import world.avatarhorizon.spigot.society.models.SocietyPlayer;

import java.lang.reflect.Type;
import java.util.UUID;

public class SocietySerializer implements JsonSerializer<Society>, JsonDeserializer<Society>
{
    @Override
    public Society deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException
    {
        JsonObject root = (JsonObject) jsonElement;

        String id = root.get("id").getAsString();
        Society society = new Society(UUID.fromString(id));
        String name = root.get("name").getAsString();
        society.setName(name);

        JsonElement description = root.get("description");
        if (description != null)
        {
            society.setDescription(description.getAsString());
        }

        JsonArray members = root.get("members").getAsJsonArray();
        for (JsonElement member : members)
        {
            JsonObject object = (JsonObject) member;
            String pId = object.get("uuid").getAsString();
            OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(pId));
            SocietyPlayer socPlayer = new SocietyPlayer(player);
            socPlayer.setSociety(society);
            society.addMember(socPlayer);
        }

        return society;
    }

    @Override
    public JsonElement serialize(Society society, Type type, JsonSerializationContext context)
    {
        JsonObject root = new JsonObject();
        root.addProperty("id", society.getId().toString());
        root.addProperty("name", society.getName());
        root.addProperty("description", society.getDescription());
        JsonArray members = new JsonArray();
        for (SocietyPlayer member : society.getMembers())
        {
            JsonObject pl = new JsonObject();
            pl.addProperty("uuid", member.getPlayer().getUniqueId().toString());
            pl.addProperty("name", member.getPlayer().getName()); //To make it human readable
            members.add(pl);
        }
        root.add("members", members);
        return root;
    }
}
