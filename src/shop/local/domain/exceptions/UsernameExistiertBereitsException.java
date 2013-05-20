package shop.local.domain.exceptions;

@SuppressWarnings("serial")
public class UsernameExistiertBereitsException extends Exception{

	public UsernameExistiertBereitsException(String username, String zusatzMsg){
		super("Der Username '"+username+"' existiert bereits!"+zusatzMsg);
	}
}
