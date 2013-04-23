package shop.local.domain;

import java.util.Iterator;
import java.util.Vector;

import shop.local.valueobjects.Kunde;



/** This class manages a costumer list and provides the methods to add,
 *  delete and search for costumers
 * @author Thummerer, Oliver
 *  date: 23.04.12
 *  version 1.0.0
 */

public class KundenVerwaltung {
	
	private Vector<Kunde> kundenListe = new Vector<Kunde>();
	
	/**
	 * lesen und schreiben in Datei
	 * Platzhalter
	 */
	
	/**
	 * Method to insert a new costumer
	 */
	
	public void einfuegen(Kunde k) /*throws MitarbeiterExistiertBereitsException*/ {
		if(!kundenListe.contains(k)){
			kundenListe.add(k);
		}else{
			//throw new KundeExistiertBereitsException(k, "Fehler beim einfuegen!");
		}
	}
	
	/**
	 * Method deletes the costumer instance.
	 * @param k Kunden instance to delete
	 */
	public void loeschen(Kunde k){
		this.kundenListe.remove(k);
	}
	
	/**
	 * searching for costumer by ID number
	 * the search stops if the instance was given back
	 * @param id search for the costumer ID number.
	 * @return the costumer instance with specified ID number, or "null" if no instance Number was found.
	 */
	public Kunde sucheKunde(int id){
		Kunde k = null;
		
		Iterator<Kunde> it = kundenListe.iterator();
		
		while(it.hasNext()){
			Kunde tmp = it.next();
			if(tmp.getId() == id){
				k = tmp;
				break;
			}
		}
		
		return k;
	}
	
	/**
	 * this method returns a copy of the current costumer list
	 * @return copy of costumer list.
	 */
	public Vector<Kunde> getKundenListe(){
		
		//creates a copy
		Vector<Kunde> kopie = new Vector<Kunde>();
		
		Iterator<Kunde> it = kundenListe.iterator();
		
		while(it.hasNext()){
			kopie.add(it.next());
		}
		
		return kopie;
		
	}	
	
	

}
