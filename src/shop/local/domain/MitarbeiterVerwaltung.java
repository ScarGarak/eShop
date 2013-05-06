package shop.local.domain;

import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import shop.local.domain.exceptions.MitarbeiterExistiertBereitsException;

import shop.local.persitence.ObjectPersistenceManager;
import shop.local.persitence.PersistenceManager;
import shop.local.valueobjects.Mitarbeiter;

/**
 * Diese Klasse verwaltet eine Mitarbeiterliste. Sie ermöglicht
 * es Mitarbeiter zu suchen, hinzuzufügen und zu löschen.
 * 
 * @author Angelo
 * @version 1
 * 
 * Zuletzt editiert: 10.04.2013
 */

public class MitarbeiterVerwaltung {
	
	private Vector<Mitarbeiter> mitarbeiterListe = new Vector<Mitarbeiter>();
	
	private PersistenceManager pm = new ObjectPersistenceManager();
	
	/**
	 * Methode zum lesen der Mitarbeiterdaten aus einer externen Datenquelle
	 * @param dateiName Dateiname der externen Datenquelle
	 * @throws IOException
	 */
	public void liesDaten(String dateiName) throws IOException{
		pm.openForReading(dateiName);
		
		Mitarbeiter m;
		
		do{
			m = pm.ladeMitarbeiter();
			if(m != null){
				try{
					einfuegen(m);
				}catch(MitarbeiterExistiertBereitsException e){
					System.err.println(e.getMessage());
					e.printStackTrace();
				}
			}
		}while(m != null);
		
		pm.close();
	}
	
	/**
	 * Methode zum schreiben der Mitarbeiterdaten in einer externe Datenquelle
	 * @param dateiName	Dateiname der externen Datenquelle
	 * @throws IOException
	 */
	public void schreibeDaten(String dateiName) throws IOException{
		pm.openForWriting(dateiName);
		
		if(mitarbeiterListe != null){
			Iterator<Mitarbeiter> it = mitarbeiterListe.iterator();

			while(it.hasNext()){
				pm.speichereMitarbeiter(it.next());
			}
		}
		pm.close();
	}
	
	
	/**
	 * Diese Methode dient zum einfuegen von Mitarbeitern in die Mitarbeiter-Liste.
	 * Wenn ein Mitarbeiter eingefuegt wird, dessen ID schon ein anderer Mitarbeiter
	 * besitzt, wird eine Exception geworfen!
	 * @param m Die Mitarbeiter-Instanz die zur Liste hinzugefügt werden soll.
	 * @throws MitarbeiterExistiertBereitsException	Wenn die ID der hinzuzufügenden Mitarbeiter-Instanz schon einmal in der Liste existiert. 
	 */
	public void einfuegen(Mitarbeiter m) throws MitarbeiterExistiertBereitsException{
		if(!mitarbeiterListe.contains(m)){
			mitarbeiterListe.add(m);
		}else{
			throw new MitarbeiterExistiertBereitsException(m, "Fehler beim einfuegen!");
		}
	}
	
	/**
	 * Diese Methode löscht die angegebene Mitarbeiter Instanz.
	 * @param m Mitarbeiter Instanz zum löschen
	 */
	public void loeschen(Mitarbeiter m){
		this.mitarbeiterListe.remove(m);
	}
	
	/**
	 * Diese Methode sucht ein Mitarbeiter mittels einer angegebenen ID Nummer.
	 * Es sei zu bemerken, dass, nach der ersten erfolgreichen Suche, die Instanz
	 * zurückgegeben wird, und die Suche abgebrochen wird.
	 * @param id ID Nummer des zu suchenden Mitarbeiters.
	 * @return Die Mitarbeiter-Instanz mit der angegebenen ID Nummer, oder "null" wenn keine Instanz gefunden wurde.
	 */
	public Mitarbeiter sucheMitarbeiter(int id){
		Mitarbeiter m = null;
		
		Iterator<Mitarbeiter> it = mitarbeiterListe.iterator();
		
		while(it.hasNext()){
			Mitarbeiter tmp = it.next();
			if(tmp.getId() == id){
				m = tmp;
				break;
			}
		}
		
		return m;
	}
	
	/**
	 * Diese Methode gibt eine Kopie der aktuellen Mitarbeiterliste zurück.
	 * @return Kopie der Mitarbeiterliste.
	 */
	public Vector<Mitarbeiter> getMitarbeiterListe(){
		
		//Erschaffen einer kopie
		Vector<Mitarbeiter> kopie = new Vector<Mitarbeiter>();
		
		Iterator<Mitarbeiter> it = mitarbeiterListe.iterator();
		
		while(it.hasNext()){
			kopie.add(it.next());
		}
		
		return kopie;
		
	}
}
