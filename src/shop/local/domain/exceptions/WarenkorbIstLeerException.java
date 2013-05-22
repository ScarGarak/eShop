package shop.local.domain.exceptions;

/**
 * Exception zur Signalisierung, dass der Warenkorb eines Kunden leer ist. 
 * (z.B. beim Kaufen).
 * 
 * @author Christof Ferreira Torres
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class WarenkorbIstLeerException extends Exception {

	/**
	 * Konstruktor
	 * 
	 * @param artikel Der Artikel mit dem zu kleinen oder leeren Bestand
	 * @param zusatzMsg zusätzlicher Text für die Fehlermeldung
	 */
	public WarenkorbIstLeerException(String zusatzMsg) {
		super("Der Warenkorb ist leer" + zusatzMsg);
	}
	
}
