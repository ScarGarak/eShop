package shop.local.domain.exceptions;

import shop.local.valueobjects.Artikel;

/**
 * Exception zur Signalisierung, dass ein Artikel nicht existiert. 
 * (z.B. bei einem L�schvorgang).
 * 
 * @author Christof Ferreira Torres
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class ArtikelExistiertNichtException extends Exception {

	/**
	 * Konstruktor
	 * 
	 * @param artikel Der nicht existierende Artikel
	 * @param zusatzMsg zus�tzlicher Text f�r die Fehlermeldung
	 */
	public ArtikelExistiertNichtException(Artikel artikel, String zusatzMsg) {
		super("Der Artikel mit der Artikelnummer " + artikel.getArtikelnummer() + " und der Bezeichnung " + 
				artikel.getBezeichnung() + " existiert nicht" + zusatzMsg);
	}
	
	/**
	 * Konstruktor
	 * 
	 * @param artikelnummer Die Artikelnummer des nicht existierenden Artikels
	 * @param zusatzMsg zus�tzlicher Text f�r die Fehlermeldung
	 */
	public ArtikelExistiertNichtException(int artikelnummer, String zusatzMsg) {
		super("Der Artikel mit der Artikelnummer " + artikelnummer + " existiert nicht" + zusatzMsg);
	}
	
	/**
	 * Konstruktor
	 * 
	 * @param bezeichnung Die Bezeichnung des nicht existierenden Artikels
	 * @param zusatzMsg zus�tzlicher Text f�r die Fehlermeldung
	 */
	public ArtikelExistiertNichtException(String bezeichnung, String zusatzMsg) {
		super("Der Artikel mit der Bezeichnung " + bezeichnung + " existiert nicht" + zusatzMsg);
	}
	
}
