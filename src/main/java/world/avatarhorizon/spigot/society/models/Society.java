package world.avatarhorizon.spigot.society.models;

import java.util.*;

public class Society
{
    private UUID id;
    private String name;
    private String description;
    private Set<SocietyPlayer> members;
    private Set<SocietyPlayer> safeMembers;

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
        this.members = new HashSet<>();
        this.safeMembers = Collections.unmodifiableSet(members);
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

    public Set<SocietyPlayer> getMembers()
    {
        return this.safeMembers;
    }

    public void addMember(SocietyPlayer member)
    {
        this.members.add(member);
    }

    public void removeMember(SocietyPlayer member)
    {
        this.members.remove(member);
    }
}
