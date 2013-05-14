package shop.local.domain.exceptions;

@SuppressWarnings("serial")
public class KundeExistiertNichtException extends Exception {
	
	public KundeExistiertNichtException(int id, String zusatzMsg) {
		super("Der Kunde mit der ID '"+id+"' existiert nicht!\n"+ zusatzMsg);
	}
}
