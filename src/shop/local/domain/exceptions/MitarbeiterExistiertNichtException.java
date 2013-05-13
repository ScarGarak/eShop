package shop.local.domain.exceptions;

import shop.local.valueobjects.Mitarbeiter;

@SuppressWarnings("serial")
public class MitarbeiterExistiertNichtException extends Exception{

	public MitarbeiterExistiertNichtException(int id, String zusatzMsg) {
		super("Der Mitarbeiter mit der ID '"+id+"' existiert nicht!\n"+ zusatzMsg);
	}
}
