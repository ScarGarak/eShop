package shop.local.domain.exceptions;

@SuppressWarnings("serial")
public class MitarbeiterExistiertNichtException extends Exception{

	public MitarbeiterExistiertNichtException(int id, String zusatzMsg) {
		super("Der Mitarbeiter mit der ID '"+id+"' existiert nicht!\n"+ zusatzMsg);
	}
}
