package shop.local.persitence;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import shop.local.valueobjects.Kunde;


	/**
 	* @author Oliver Thummerer
 	* 
 	* Schnittstelle zur persistenten Speicherung von
 	* DAten in .JSON DAteien
 	* @see shop.local.persistence.PersistenceManager
 	*/

public class FilePersistenceManager {
	
	private BufferedReader reader = null;
	private PrintWriter writer = null;
	
	public void openForReading(String datei) throws FileNotFoundException {
		reader = new BufferedReader(new FileReader(datei));
	}
	
	public void openForWriting(String datei) throws IOException {
		writer = new PrintWriter(new FileWriter(datei));
	}
	
	public boolean close() {
		if (writer != null)
			writer.close();
		
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
				
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Methode zum  Einlesen der Kundendaten aus einer externen Datenquelle.
	 * 
	 * @return Kunde-Objekt, wenn einlesen erfolgreich, false null
	 */
	public Kunde ladeKunden() throws IOException {
		// Namen einlesen
		String name = liesZeile();
		if (name == null) {
			// keine Daten mehr vorhanden
			return null;
		}
		// ID einlesen
		String strId = liesZeile();
		int id = Integer.parseInt(strId);
		
		// strasse, plz, wohnort einlesen
		String strasse = liesZeile();
		String strPlz = liesZeile();
		int plz = Integer.parseInt(strPlz);
		String wohnort = liesZeile();
		
		return new Kunde(name, id, strasse, plz, wohnort);
	}
	
	/**
	 * Methode zum schreiben der Kundendaten in eine externe Datenquelle.
	 * 
	 * @param k Kunde-Objekt, das gespeichert werden soll
	 * @return boolean true wenn der Schreibvorgang erfolgreich war, andernfalls false
	 * @throws IOException
	 */
	
	public boolean speichereKunden(Kunde k) throws IOException {
		// Name, Id, Strasse, Plz, Wohnort schreiben
		schreibeZeile(k.getName());
		schreibeZeile(Integer.valueOf(k.getId()).toString());
		schreibeZeile(k.getStrasse());
		schreibeZeile(Integer.valueOf(k.getPlz()).toString());
		schreibeZeile(k.getWohnort());
		return true;
	}
	
	/*
	 * Private Hilfsmethoden
	 */
	
	private String liesZeile() throws IOException {
		if (reader != null)
			return reader.readLine();
		else
			return "";
	}

	private void schreibeZeile(String daten) {
		if (writer != null)
			writer.println(daten);
	}
	
}
