package world.avatarhorizon.spigot.society.models;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Society
{
    private UUID id;
    private String name;
    private String description;
    private List<SocietyPlayer> members;
    private List<SocietyPlayer> safeMembers;

    public Society()
    {
        id = UUID.randomUUID();
        this.initialize();
    }

    public Society(UUID id)
    {
        this.id = id;
        this.initialize();
    }

    private void initialize()
    {
        this.members = new LinkedList<>();
        this.safeMembers = Collections.unmodifiableList(members);
    }

    public UUID getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public List<SocietyPlayer> getMembers()
    {
        return this.safeMembers;
    }

}
