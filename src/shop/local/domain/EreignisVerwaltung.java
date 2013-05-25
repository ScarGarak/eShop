package shop.local.domain;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import java.util.ListIterator;

import shop.local.persitence.log.FileLogPersistenceManager;
import shop.local.persitence.log.LogPersistenceManager;
import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.Ereignis;
import shop.local.valueobjects.Mitarbeiter;

public class EreignisVerwaltung {
	
	private Vector<Ereignis> ereignisListe = new Vector<Ereignis>();
	private LogPersistenceManager lpm = new FileLogPersistenceManager();
	
	private Hashtable<Integer, Vector<String[]>> bestandsHistorieListe;	// Wenn man eine Artikel ID angibt, bekommt man dessen Bestandshistorie
	
	public void schreibeDaten(String dateiname) throws IOException{
		lpm.openForWriting(dateiname);

		Iterator<Ereignis> it = ereignisListe.iterator();

		while(it.hasNext()){
			Ereignis e = it.next();
			if(e.getAnzahl() < 0){
				lpm.speichereAuslagerung(e.getPerson(), Math.abs(e.getAnzahl()), e.getArtikel().getArtikelnummer(), e.getDatum());
			}else if (e.getAnzahl() > 0){
				lpm.speichereEinlagerung((Mitarbeiter)e.getPerson(), e.getAnzahl(), e.getArtikel().getArtikelnummer(), e.getDatum());
			}
		}

		lpm.close();
	}
	
	public String gibBestandsHistorie(Artikel artikel, String dateiname) throws IOException{
		int artikelID = artikel.getArtikelnummer();
		if(bestandsHistorieListe == null){
			bestandsHistorieListe = new Hashtable<Integer, Vector<String[]>>();
		}
		
		
		if(!bestandsHistorieListe.containsKey(artikelID)){
			erstelleBestandsHistorie(artikel, dateiname);
		}
		
		String result = "";
		
		// Die Bestandshistorie für den Artikel mit artikelID wurde bereits berechnet
		Iterator<String[]> it = bestandsHistorieListe.get(artikelID).iterator();
		while(it.hasNext()){
			String[] bestandHistorie = it.next();
			result += bestandHistorie[0]+"\t"+bestandHistorie[1]+"\n";
		}
		return result;
	}

	private void erstelleBestandsHistorie(Artikel artikel, String dateiname) throws IOException {
		int artikelID = artikel.getArtikelnummer();
		lpm.openForReading(dateiname);
		
		// Bilde den Eintrag in der Hashtable
		Vector<String[]> bestandsHistorie = new Vector<String[]>();
		bestandsHistorieListe.put(artikelID, bestandsHistorie);
		
		String datum = "";
		String linie = "";
		StringTokenizer sTok = null;

		// Gehe von Linie zur Linie, und kontrolliere, ob das Datum nicht älter als 30 Tage ist.
		// Wenn die Linie nicht älter als 30 Tage ist, gehe weiter.
		do{
			linie = lpm.ladeEinAuslagerung();
			sTok = new StringTokenizer(linie);
			datum = sTok.nextToken()+" "+sTok.nextToken();
		}while(!istDatumGueltig(datum));

		String[] eintrag = new String[2];
		int bestandsVeraenderung = 0;
		boolean ersterDurchlauf = true;
		// Gehe von Linie zur Linie, bis zum Schluss der Datei
		do{
			// Solange wie die eingelesene Linie nicht das Pattern 'Artikel artikelID'
			// beinhaltet, gehen wir zur nächsten Linie.
			while(!linie.contains("Artikel "+artikelID) && !linie.equals("")){
				linie = lpm.ladeEinAuslagerung();
			}
			
			if(!linie.equals("")){
				///////////////////////// Eintrag gefunden /////////////////////////

				// Speichere in newDatum das Datum (YYYY-MM-DD) der eingelesenen Zeile
				sTok = new StringTokenizer(linie);
				String newDatum = sTok.nextToken();


				// Neuer Tag
				if(!newDatum.equals(datum)){	
					// Das eingelesene Datum ist nicht das gleiche Datum wie die Zeile vorher
					// d.h. jetzt kommt eine Zeile mit Ein- oder Auslagerungen von einem
					// anderen Tag
					if(!ersterDurchlauf){ // Beim ersten Mal wird nicht abgespeichert
						// Die Summe der Bestands-Veraenderungen an diesem Tag und datum abspeichern
						eintrag[0] = datum;
						eintrag[1] = bestandsVeraenderung+"";
						bestandsHistorie.add(eintrag);
					}else{
						ersterDurchlauf = false;
					}
					// Das neue Datum wird in 'datum' gespeichert
					datum = newDatum;

					// Re-intialisierung der Variablen
					eintrag = new String[2];
					bestandsVeraenderung = 0;
					
				}


				// Entnehmen der Bestands-Veraenderung in der Zeile
				int bVZeile = 0;	// Die Bestands Veränderung in dieser Zeile
				for(int i = 1; sTok.hasMoreTokens(); i++){		// i=1, weil 0 die Kolonne mit Datum war
					if(i == 4){	// Kolonne mit der anzahl der Ein- oder Auslagerung
						bVZeile = Integer.parseInt(sTok.nextToken());
					}else if(i == 8){	// Kolonne mit 'eingelagert', 'verkauft' oder 'ausgelagert'
						String tmp = sTok.nextToken();
						if(tmp.equals("eingelagert")){	
							bestandsVeraenderung -= bVZeile;	// Wenn das Artikel eingelagert wurde, müssen wir die anzahl
							// wieder subtrahieren, um den Bestand vom aktuellem Bestand
							// aus zu berechnen.
						}else{
							bestandsVeraenderung += bVZeile;
						}
					}else{
						sTok.nextToken();
					}
				}


				linie = lpm.ladeEinAuslagerung();
			}
		}while(!linie.equals(""));
		
		int bestand = artikel.getBestand();
		
		// Wenn das Datum der letzten Zeile das heutige Datum ist, wird es nicht abgespeichert
		if(!new SimpleDateFormat("yyyy-MM-dd").format(new Date()).equals(datum)){
			// Abspeicheren der zwischengelagerten Daten:
			eintrag[0] = datum;
			eintrag[1] = bestandsVeraenderung+"";
			bestandsHistorie.add(eintrag);
		}else{ 
			// Um die Bestandshistorie zurueck rechnen zu können
			// müssen die Ein- und Auslagerungen von Heute
			// beachtet werden
			bestand += bestandsVeraenderung;
		}
		
		// Jetzt müssen nur noch die Ereignisse in der Ereignissliste
		// überprüft werden
		
		if(!ereignisListe.isEmpty()){
			Iterator<Ereignis> iter = ereignisListe.iterator();
			while(iter.hasNext()){
				Ereignis e = iter.next();
				if(e.getArtikel().equals(artikel)){
					bestand += -e.getAnzahl();
				}
			}
		}
		
		lpm.close();
		
		rechneBestand(artikel.getArtikelnummer(), bestand);
	}
	
	private void rechneBestand(int artikelID, int bestand){
		Vector<String[]> bestandsHistorie = bestandsHistorieListe.get(artikelID);
		ListIterator<String[]> lIter = bestandsHistorie.listIterator(bestandsHistorie.size());
		
		while(lIter.hasPrevious()){
			String[] eintrag = lIter.previous();
			int bestandsVeraenderung = Integer.parseInt(eintrag[1]);
			eintrag[1] = bestand+"";
			bestand += bestandsVeraenderung;
		}
	}
	
	private boolean istDatumGueltig(String datum){
		boolean gueltig = false;
		try {
			// Gib die Calendar Instanz
			Calendar aktuellesDatum = Calendar.getInstance();
			Calendar eintragDatum = Calendar.getInstance();
			
			// Setze das aktuelle Datum
			aktuellesDatum.setTime(new Date());
			// Parse die Parametervariable zum Typ 'Date' und setze diese dann als Calendar Datum
			eintragDatum.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datum));
			
			// Gehe vom aktuellem Datum 30 Tage zurueck
			aktuellesDatum.add(Calendar.DAY_OF_MONTH, -30);
			
			// Wenn das (verringerte) aktuelleDatum vor der Zeit des Eintrag-Datums liegt, ist der Eintrag gueltig
			if(aktuellesDatum.before(eintragDatum)){
				gueltig = true;
			}
		} catch (ParseException e) {
			System.err.println("Fehler beim parsen des Datums!");
			System.err.println("Fehler ausgelöst durch: '"+datum+"'");
			System.err.println(e.getMessage());
		}
		return gueltig;
	}
	
	public void hinzufuegen(Ereignis e){
		ereignisListe.add(e);
	}
	
	public Vector<Ereignis> getEreignisListe(){
		return ereignisListe;
	}

}
