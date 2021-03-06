package shop.local.domain;

import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import shop.local.domain.exceptions.MitarbeiterExistiertBereitsException;
import shop.local.domain.exceptions.MitarbeiterExistiertNichtException;

import shop.local.persitence.data.DataPersistenceManager;
import shop.local.persitence.data.ObjectDataPersistenceManager;
import shop.local.valueobjects.Mitarbeiter;

/**
 * Diese Klasse verwaltet eine Mitarbeiterliste. Sie erm�glicht
 * es Mitarbeiter zu suchen, hinzuzuf�gen und zu l�schen.
 * 
 * @author Angelo
 * @version 1
 * 
 * Zuletzt editiert: 11.05.2013
 */

public class MitarbeiterVerwaltung {
	
	private Vector<Mitarbeiter> mitarbeiterListe = new Vector<Mitarbeiter>();
	
	private DataPersistenceManager pm = new ObjectDataPersistenceManager();
	
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
	 * Methode zum schreiben der Mitarbeiterdaten in eine externe Datenquelle
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
	 * @param m Die Mitarbeiter-Instanz die zur Liste hinzugef�gt werden soll.
	 * @throws MitarbeiterExistiertBereitsException	Wenn die ID der hinzuzuf�genden Mitarbeiter-Instanz schon einmal in der Liste existiert. 
	 */
	public void einfuegen(Mitarbeiter m) throws MitarbeiterExistiertBereitsException{
		if(!mitarbeiterListe.contains(m)){
			mitarbeiterListe.add(m);
		}else{
			throw new MitarbeiterExistiertBereitsException(m, "Fehler beim einfuegen!");
		}
	}
	
	/**
	 * Diese Methode l�scht die angegebene Mitarbeiter Instanz aus der Liste.
	 * @param m Mitarbeiter Instanz zum l�schen
	 */
	public void loeschen(Mitarbeiter m){
		this.mitarbeiterListe.remove(m);
	}
	
	/**
	 * Diese Methode sucht ein Mitarbeiter mittels einer angegebenen ID Nummer.
	 * @param id ID Nummer des zu suchenden Mitarbeiters.
	 * @return Die Mitarbeiter-Instanz mit der angegebenen ID Nummer, oder "null" wenn keine Instanz gefunden wurde.
	 */
	public Mitarbeiter sucheMitarbeiter(int id) throws MitarbeiterExistiertNichtException{
		Mitarbeiter m = null;
		
		Iterator<Mitarbeiter> it = mitarbeiterListe.iterator();
		
		while(it.hasNext()){
			Mitarbeiter tmp = it.next();
			if(tmp.getId() == id){
				m = tmp;
				break;
			}
		}
		
		if(m == null){
			throw new MitarbeiterExistiertNichtException(id, "");
		}
		return m;
	}
	
	/**
	 * Diese Methode gibt eine Kopie der aktuellen Mitarbeiterliste zur�ck.
	 * @return Kopie der Mitarbeiterliste.
	 */
	public Vector<Mitarbeiter> getMitarbeiterListe(){
		
		//Erschaffen einer kopie
		Vector<Mitarbeiter> kopie = new Vector<Mitarbeiter>();
		kopie.addAll(mitarbeiterListe);
		
		return kopie;
		
	}
}
