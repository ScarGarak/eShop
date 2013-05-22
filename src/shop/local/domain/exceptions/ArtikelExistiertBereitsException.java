package shop.local.domain.exceptions;

import shop.local.valueobjects.Artikel;

/**
 * Exception zur Signalisierung, dass ein Artikel bereits existiert. 
 * (z.B. bei einem Einfügevorgang).
 * 
 * @author Christof Ferreira Torres
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class ArtikelExistiertBereitsException extends Exception {

	/**
	 * Konstruktor
	 * 
	 * @param artikel Der bereits existierende Artikel
	 * @param zusatzMsg zusätzlicher Text für die Fehlermeldung
	 */
	public ArtikelExistiertBereitsException(Artikel artikel, String zusatzMsg) {
		super("Artikel mit Bezeichnung " + artikel.getBezeichnung() + " und Artikelnummer " + artikel.getArtikelnummer() 
				+ " existiert bereits" + zusatzMsg);
	}
	
}
