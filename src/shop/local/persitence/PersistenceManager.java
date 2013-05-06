package shop.local.persitence;

import java.io.IOException;

import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.Kunde;

/**
 * 
 * @author Oliver Thummerer
 * 
 * Interface für den Zugriff auf ein Speichermedium
 * zum Ablegen von Kunden-, Artikel- und Mitarbeiterdaten.
 * 
 * Das Interface muss von Klassen implementiert werden wenn eine
 * Persistez-Schnittstelle realisiert werden soll 
 */
public interface PersistenceManager {
	
	/**
	 * Methoden zum öffnen und schließen einer externen Datenquelle
	 * 
	 * @param datenquelle
	 * @throws IOException
	 */
	
	public void openForReading(String datenquelle) throws IOException;
	
	public void openForWriting(String datenquelle) throws IOException;
	
	public boolean close();
	
	/**
	 * Methode zum Einlesen der Artikeldaten aus einer externen Datenquelle.
	 * 
	 * @return Artikel-Objekt, wenn Einlesen erfolgreich, false null
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	public Artikel ladeArtikel() throws IOException, ClassNotFoundException;

	/**
	 * Methode zum Schreiben der Buchdaten in eine externe Datenquelle.
	 * 
	 * @param a Artikel-Objekt, das gespeichert werden soll
	 * @throws IOException
	 */
	public void speichereArtikel(Artikel a) throws IOException;
	
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
	 * @throws IOException
	 */
	public void speichereKunden(Kunde k) throws IOException;
	
}
