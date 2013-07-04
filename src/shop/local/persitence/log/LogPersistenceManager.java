package shop.local.persitence.log;

import java.io.IOException;
import java.util.Date;

import shop.local.valueobjects.Mitarbeiter;
import shop.local.valueobjects.Person;

/**
 * 
 * @author Christof Ferreira Torres
 * 
 * Interface f�r den Zugriff auf ein Speichermedium
 * zum Ablegen von Ein- und Auslagerungsdaten.
 * 
 * Das Interface muss von Klassen implementiert werden wenn eine
 * Persistez-Schnittstelle realisiert werden soll 
 */
public interface LogPersistenceManager {

	
	/**
	 * Methoden zum �ffnen und schlie�en einer externen Datenquelle
	 * 
	 * @param datenquelle
	 * @throws IOException
	 */
	public void openForReading(String datenquelle) throws IOException;
	
	public void openForWriting(String datenquelle) throws IOException;
	
	public boolean close();
	
	
	public String ladeEinAuslagerung() throws IOException;

	public void speichereEinlagerung(Mitarbeiter m, int anzahl, int artikelnummer, Date datum) throws IOException;
	
	public void speichereAuslagerung(Person p, int anzahl, int artikelnummer, Date datum) throws IOException;
	
	public boolean cleanLogdatei(String zeile, String dateiname) throws IOException;
	
	public boolean entferneArtikelAusLog(String id, String dateiname) throws IOException;
	
}
