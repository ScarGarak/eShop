package shop.local.domain.exceptions;

import shop.local.valueobjects.Artikel;

/**
 * Exception zur Signalisierung, dass der Bestand eines Artikel zu klein oder leer ist.
 * (z.B. beim ändern der Stückzahl eines Artikel in einem Warenkorb).
 * 
 * @author Christof Ferreira Torres
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class ArtikelBestandIstZuKleinException extends Exception {

	/**
	 * Konstruktor
	 * 
	 * @param artikel Der Artikel mit dem zu kleinen oder leeren Bestand
	 * @param zusatzMsg zusätzlicher Text für die Fehlermeldung
	 */
	public ArtikelBestandIstZuKleinException(Artikel artikel, String zusatzMsg) {
		super("Der Bestand des Artikels mit der Bezeichnung " + artikel.getBezeichnung() + " und der Artikelnummer " + artikel.getArtikelnummer() 
				+ " ist zu klein oder leer" + zusatzMsg);
	}
	
}
