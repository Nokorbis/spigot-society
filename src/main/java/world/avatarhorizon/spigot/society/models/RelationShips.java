package world.avatarhorizon.spigot.society.models;

public enum RelationShips
{
    ENEMY("relationships.enemy"),
    NEUTRAL("relationships.neutral"),
    TRUCE("relationships.truce"),
    ALLY("relationships.ally");

    private String key;
    RelationShips(String key)
    {
        this.key = key;
    }

    public String getKey()
    {
        return this.key;
    }
}
