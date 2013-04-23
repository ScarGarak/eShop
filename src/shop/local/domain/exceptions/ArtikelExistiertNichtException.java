package shop.local.domain.exceptions;


/**
 * Exception zur Signalisierung, dass ein Artikel nicht existiert (z.B. bei einem L�schvorgang).
 * 
 * @author Christof Ferreira Torres
 */
@SuppressWarnings("serial")
public class ArtikelExistiertNichtException extends Exception {

	/**
	 * Konstruktor
	 * 
	 * @param artikelnummer Die Artikelnummer des nicht existierenden Artikels
	 * @param zusatzMsg zus�tzlicher Text f�r die Fehlermeldung
	 */
	public ArtikelExistiertNichtException(int artikelnummer, String zusatzMsg) {
		super("Artikelnummer " + artikelnummer + " existiert nicht" + zusatzMsg);
	}
	
	/**
	 * Konstruktor
	 * 
	 * @param bezeichnung Die Bezeichnung des nicht existierenden Artikels
	 * @param zusatzMsg zus�tzlicher Text f�r die Fehlermeldung
	 */
	public ArtikelExistiertNichtException(String bezeichnung, String zusatzMsg) {
		super("Artikel mit der Bezeichnung " + bezeichnung + " existiert nicht" + zusatzMsg);
	}
	
}
