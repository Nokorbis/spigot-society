package world.avatarhorizon.spigot.society.models;

public enum Ranks
{
    RECRUIT("ranks.recruit"),
    MEMBER("ranks.member"),
    VETERAN("ranks.veteran"),
    OFFICER("ranks.officer"),
    LEADER("ranks.leader");

    private String key;

    Ranks(String key)
    {
        this.key = key;
    }

    public String getKey()
    {
        return this.key;
    }
}
