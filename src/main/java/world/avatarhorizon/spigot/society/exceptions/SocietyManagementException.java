package world.avatarhorizon.spigot.society.exceptions;

public class SocietyManagementException extends SocietyException
{
    private ExceptionCause cause;

    public SocietyManagementException(ExceptionCause cause)
    {
        this.cause = cause;
    }

    public ExceptionCause getExceptionCause()
    {
        return cause;
    }
}
