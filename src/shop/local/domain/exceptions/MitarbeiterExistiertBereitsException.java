package shop.local.domain.exceptions;

import shop.local.valueobjects.Mitarbeiter;

@SuppressWarnings("serial")
public class MitarbeiterExistiertBereitsException extends Exception{
	
	public MitarbeiterExistiertBereitsException(Mitarbeiter m, String zusatzMsg) {
		super("Der Mitarbeiter mit der ID '"+m.getId()+"' existiert bereits!\n"+ zusatzMsg);
	}

}
