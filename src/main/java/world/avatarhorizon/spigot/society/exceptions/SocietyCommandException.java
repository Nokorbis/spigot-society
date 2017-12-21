package world.avatarhorizon.spigot.society.exceptions;

public class SocietyCommandException extends SocietyException
{
    private String message;

    public SocietyCommandException(String message)
    {
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return this.message;
    }
}
