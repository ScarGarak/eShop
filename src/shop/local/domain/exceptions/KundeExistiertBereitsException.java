package shop.local.domain.exceptions;

import shop.local.valueobjects.Kunde;

@SuppressWarnings("serial")
public class KundeExistiertBereitsException extends Exception {

	public KundeExistiertBereitsException(Kunde k, String zusatzMsg) {
		super("Der Kunde mit der ID '"+k.getId()+"' existiert bereits!\n"+ zusatzMsg);
	}
	
}
