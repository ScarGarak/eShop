package shop.local.persitence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import shop.local.valueobjects.Kunde;
import shop.local.valueobjects.Mitarbeiter;
import shop.local.valueobjects.WarenkorbArtikel;

/**
 * @author Christof Ferreira Torres
 * 
 * Schnittstelle zur persistenten Speicherung von
 * Ein- und AuslagerungsDaten in .txt Dateien
 * @see shop.local.persistence.LogPersistenceManager
 */
public class FilePersistenceManager implements LogPersistenceManager {
	
	private BufferedReader reader = null;
	private PrintWriter writer = null;
	
	public void openForReading(String datei) throws FileNotFoundException {
		reader = new BufferedReader(new FileReader(datei));
	}
	
	public void openForWriting(String datei) throws IOException {
		writer = new PrintWriter(new BufferedWriter(new FileWriter(datei)));
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
	
	public String ladeEinAuslagerung() throws IOException {
		return liesZeile();
	}

	public void speichereEinlagerung(Mitarbeiter m, int anzahl, int artikelnummer) throws IOException {
		schreibeZeile(new Date() + " Mitarbeiter " + m.getId() + " " + anzahl + " Stück Artikel " + artikelnummer + " eingelagert");
	}
	
	public void speichereAuslagerung(Kunde k, WarenkorbArtikel wa) throws IOException {
		schreibeZeile(new Date() + " Kunde " + k.getId() + " " + wa.getStueckzahl() + " Stück Artikel " + wa.getArtikel().getArtikelnummer() + " verkauft");
	}
	
	/*
	 * Private Hilfsmethoden zum lesen bzw. schreiben einer Zeile
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
