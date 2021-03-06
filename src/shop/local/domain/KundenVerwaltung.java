package shop.local.domain;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import shop.local.domain.exceptions.ArtikelBestandIstKeineVielfacheDerPackungsgroesseException;
import shop.local.domain.exceptions.ArtikelBestandIstZuKleinException;
import shop.local.domain.exceptions.ArtikelExistiertNichtException;
import shop.local.domain.exceptions.KundeExistiertBereitsException;
import shop.local.domain.exceptions.KundeExistiertNichtException;
import shop.local.domain.exceptions.WarenkorbIstLeerException;
import shop.local.persitence.data.DataPersistenceManager;
import shop.local.persitence.data.ObjectDataPersistenceManager;
import shop.local.valueobjects.Kunde;
import shop.local.valueobjects.Rechnung;
import shop.local.valueobjects.WarenkorbArtikel;

/** 
 * This class manages a costumer list and provides the methods to add,
 * delete and search for costumers
 * 
 * @author Thummerer, Oliver
 * @version 1.0.0
 *  
 * last edited 23.04.12
 */
public class KundenVerwaltung {
	
	private Vector<Kunde> kundenListe = new Vector<Kunde>();
	
	private DataPersistenceManager pm = new ObjectDataPersistenceManager();
	
	public void liesDaten(String dateiName) throws IOException{
		pm.openForReading(dateiName);
		
		Kunde k;
		
		do{
			k = pm.ladeKunden();
			if(k != null){
				try{
					einfuegen(k);
				}catch(KundeExistiertBereitsException e){
					System.err.println(e.getMessage());
					e.printStackTrace();
				}
			}
		}while(k != null);
		
		pm.close();
	}
	
	public void schreibeDaten(String dateiName) throws IOException{
		pm.openForWriting(dateiName);
		
		if(kundenListe != null){
			Iterator<Kunde> it = kundenListe.iterator();

			while(it.hasNext()){
				pm.speichereKunden(it.next());
			}
		}
		pm.close();
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
	public Kunde sucheKunde(int id) throws KundeExistiertNichtException{
		Kunde k = null;
		
		Iterator<Kunde> it = kundenListe.iterator();
		
		while(it.hasNext()){
			Kunde tmp = it.next();
			if(tmp.getId() == id){
				k = tmp;
				break;
			}
		}
		
		if(k == null){
			throw new KundeExistiertNichtException(id, "");
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
	
	public void inDenWarenkorbLegen(Kunde kunde, WarenkorbArtikel warenkorbArtikel) throws ArtikelBestandIstZuKleinException, ArtikelExistiertNichtException, ArtikelBestandIstKeineVielfacheDerPackungsgroesseException {
		kunde.getWarenkorbVerwaltung().hinzufuegen(warenkorbArtikel);
	}

	public void ausDemWarenkorbHerausnehmen(Kunde kunde, WarenkorbArtikel warenkorbArtikel) throws ArtikelExistiertNichtException, ArtikelBestandIstKeineVielfacheDerPackungsgroesseException {
		kunde.getWarenkorbVerwaltung().entfernen(warenkorbArtikel);
	}
	
	public Rechnung kaufen(Kunde kunde) throws WarenkorbIstLeerException {
		return new Rechnung(kunde, new Date(), kunde.getWarenkorbVerwaltung().kaufen());
	}
	
	public void leeren(Kunde kunde) throws ArtikelBestandIstKeineVielfacheDerPackungsgroesseException {
		kunde.getWarenkorbVerwaltung().leeren();
	}
	
}
