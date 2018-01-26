package world.avatarhorizon.spigot.society.exceptions;

public enum ExceptionCause
{
    SOCIETY_NAME_USED("error.name.used"),
    INVALID_NAME("error.name.invalid"),
    ALREADY_IN_SOCIETY("error.already_in_society");
    private String key;
    ExceptionCause(String key)
    {
        this.key = key;
    }
    public String getKey()
    {
        return key;
    }
}


