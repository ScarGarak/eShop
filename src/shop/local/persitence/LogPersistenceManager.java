package shop.local.persitence;

import java.io.IOException;

import shop.local.valueobjects.Kunde;
import shop.local.valueobjects.Mitarbeiter;
import shop.local.valueobjects.WarenkorbArtikel;

/**
 * 
 * @author Christof Ferreira Torres
 * 
 * Interface für den Zugriff auf ein Speichermedium
 * zum Ablegen von Ein- und Auslagerungsdaten.
 * 
 * Das Interface muss von Klassen implementiert werden wenn eine
 * Persistez-Schnittstelle realisiert werden soll 
 */
public interface LogPersistenceManager {

	/**
	 * Methoden zum öffnen und schließen einer externen Datenquelle
	 * 
	 * @param datenquelle
	 * @throws IOException
	 */
	public void openForReading(String datenquelle) throws IOException;
	
	public void openForWriting(String datenquelle) throws IOException;
	
	public boolean close();
	
	
	public String ladeEinAuslagerung() throws IOException;

	public void speichereEinlagerung(Mitarbeiter m, int anzahl, int artikelnummer) throws IOException;
	
	public void speichereAuslagerung(Kunde k, WarenkorbArtikel wa) throws IOException;
	
}
