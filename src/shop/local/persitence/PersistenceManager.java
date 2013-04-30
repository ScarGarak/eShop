package shop.local.persitence;

import java.io.IOException;

import shop.local.valueobjects.Kunde;

/**
 * 
 * @author Oliver Thummerer
 * 
 * Interface f�r den Zugriff auf ein Speichermedium
 * zum Ablegen von Kunden-, Artikel- und Mitarbeiterdaten.
 * 
 * Das Interface muss von Klassen implementiert werden wenn eine
 * Persistez-Schnittstelle realisiert werden soll 
 */

public interface PersistenceManager {
	
	/**
	 * Methoden zum �effnen einer externen Datenquelle
	 * 
	 * @param datenquelle
	 * @throws IOException
	 */
	
	public void openForReading(String datenquelle) throws IOException;
	
	public void openForWriting(String datenquelle) throws IOException;
	
	public boolean close();
	
	/**
	 * Methode zum  Einlesen der Kundendaten aus einer externen Datenquelle.
	 * 
	 * @return Kunde-Objekt, wenn einlesen erfolgreich, false null
	 * @throws IOException
	 */
	
	public Kunde ladeKunden() throws IOException;
	
	/**
	 * Methode zum schreiben der Kundendaten in eine externe Datenquelle.
	 * 
	 * @param k Kunde-Objekt, das gespeichert werden soll
	 * @return boolean true wenn der Schreibvorgang erfolgreich war, andernfalls false
	 * @throws IOException
	 */
	
	public boolean speichereKunden(Kunde k) throws IOException;
}