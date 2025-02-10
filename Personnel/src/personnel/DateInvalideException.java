package personnel;

/**
 * Exception levée lorsque les dates d'emploi sont invalides.
 * Par exemple, si la date de sortie est antérieure à la date d'entrée
 * ou si l'une des dates est null.
 */
public class DateInvalideException extends Exception 
{
    private static final long serialVersionUID = 1L;
    
    public DateInvalideException(String message)
    {
        super(message);
    }
}