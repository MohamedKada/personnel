package personnel;

public class Erreurdate  extends Exception {
	
	
public String getMessage(){
	return "La date de départ est antérieur à la date d'arrivée.";
	
}
}
