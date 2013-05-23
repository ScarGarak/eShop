package shop.local.domain;

import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import shop.local.persitence.log.FileLogPersistenceManager;
import shop.local.persitence.log.LogPersistenceManager;
import shop.local.valueobjects.Ereignis;
import shop.local.valueobjects.Mitarbeiter;

public class EreignisVerwaltung {
	
	private Vector<Ereignis> ereignisListe = new Vector<Ereignis>();
	private LogPersistenceManager lpm = new FileLogPersistenceManager();
	
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
	
	public String gibBestandsHistorie(int artikelID){
		//TODO
		return null;
	}
	
	public void hinzufuegen(Ereignis e){
		ereignisListe.add(e);
	}
	
	public Vector<Ereignis> getEreignisListe(){
		return ereignisListe;
	}

}
