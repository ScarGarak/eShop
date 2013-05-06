package shop.local.domain;

import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import shop.local.domain.exceptions.ArtikelBestandException;
import shop.local.domain.exceptions.ArtikelExistiertNichtException;
import shop.local.domain.exceptions.KundeExistiertBereitsException;
import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.Kunde;
import shop.local.valueobjects.WarenkorbArtikel;



/** This class manages a costumer list and provides the methods to add,
 *  delete and search for costumers
 * @author Thummerer, Oliver
 *  
 *  version 1.0.0
 *  
 *  last edited 23.04.12
 */

public class KundenVerwaltung {
	
	private Vector<Kunde> kundenListe = new Vector<Kunde>();
	
	
	public void liesDaten(String dateiName) throws IOException{
		//	TODO	Wenn wir uns um die Persistence kuemmern
	}
	
	public void schreibeDaten(String dateiName) throws IOException{
		//	TODO	Wenn wir uns um die Persistence kuemmern
	}
	
	
	/**
	 * Method to insert a new costumer
	 */
	
	public void einfuegen(Kunde k) throws KundeExistiertBereitsException {
		if(!kundenListe.contains(k)){
			kundenListe.add(k);
		}else{
			throw new KundeExistiertBereitsException(k, "Fehler beim einfuegen!");
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
	
	public void inDenWarenkorbLegen(Kunde kunde, WarenkorbArtikel warenkorbArtikel) throws ArtikelBestandException, ArtikelExistiertNichtException {
		kunde.getWarenkorb().hinzufuegen(warenkorbArtikel);
	}

}
