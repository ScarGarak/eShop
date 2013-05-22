package shop.local.domain;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import shop.local.domain.exceptions.ArtikelBestandIstKeineVielfacheDerPackungsgroesseException;
import shop.local.domain.exceptions.ArtikelBestandIstZuKleinException;
import shop.local.domain.exceptions.ArtikelExistiertNichtException;
import shop.local.domain.exceptions.WarenkorbIstLeerException;
import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.WarenkorbArtikel;

/**
 * Klasse zur Verwaltung vom Warenkorb.
 * 
 * @author Christof Ferreira Torres
 * @version 1.0.0
 */
public class WarenkorbVerwaltung {
	
	// Verwaltung des Warenkorbes in einem Vector
	private List<WarenkorbArtikel> warenkorb = new Vector<WarenkorbArtikel>();
	
	/**
	 * Synchronisierte methode zum hinzuf�gen eines Warenkorb Artikels in den Warenkorb.
	 * 
	 * @param warenkorbArtikel Der Warenkorb Artikel der in den Warenkorb hinzugef�gt werden soll.
	 * @throws ArtikelBestandIstZuKleinException 
	 * @throws ArtikelExistiertNichtException 
	 * @throws ArtikelBestandIstKeineVielfacheDerPackungsgroesseException 
	 */
	public synchronized void hinzufuegen(WarenkorbArtikel warenkorbArtikel) throws ArtikelBestandIstZuKleinException, ArtikelExistiertNichtException, ArtikelBestandIstKeineVielfacheDerPackungsgroesseException {	
		if (warenkorb.contains(warenkorbArtikel)) {
			this.stueckzahlAendern(warenkorbArtikel, warenkorb.get(warenkorb.indexOf(warenkorbArtikel)).getStueckzahl() + warenkorbArtikel.getStueckzahl());
		} else {
			int alterBestand = warenkorbArtikel.getArtikel().getBestand();
			if (alterBestand - warenkorbArtikel.getStueckzahl() >= 0) {
				warenkorbArtikel.getArtikel().setBestand(alterBestand - warenkorbArtikel.getStueckzahl());
				warenkorb.add(warenkorbArtikel);
			} else {
				throw new ArtikelBestandIstZuKleinException(warenkorb.get(warenkorb.indexOf(warenkorbArtikel)).getArtikel(), " - in 'stueckzahlAendern()'");
			}
		}
	}
	
	/**
	 * Synchronisierte methode zum �ndern der St�ckzahl eines Artikels im Warenkorb.
	 * 
	 * @param warenkorbArtikel Der Warenkorb Artikel dessen St�ckzahl ver�ndert werden soll.
	 * @param neueStueckzahl Die neue St�ckzahl des Warenkorb Artikels.
	 * @throws ArtikelBestandIstZuKleinException 
	 * @throws ArtikelExistiertNichtException 
	 * @throws ArtikelBestandIstKeineVielfacheDerPackungsgroesseException 
	 */
	public synchronized void stueckzahlAendern(WarenkorbArtikel warenkorbArtikel, int neueStueckzahl) throws ArtikelBestandIstZuKleinException, ArtikelExistiertNichtException, ArtikelBestandIstKeineVielfacheDerPackungsgroesseException {
		if (warenkorb.contains(warenkorbArtikel)) {
			int alterBestand = warenkorb.get(warenkorb.indexOf(warenkorbArtikel)).getArtikel().getBestand();
			int alteStueckzahl = warenkorb.get(warenkorb.indexOf(warenkorbArtikel)).getStueckzahl();
			if (alteStueckzahl < neueStueckzahl) {
				if (alterBestand - (neueStueckzahl - alteStueckzahl) >= 0) {
					warenkorb.get(warenkorb.indexOf(warenkorbArtikel)).getArtikel().setBestand(alterBestand - (neueStueckzahl - alteStueckzahl));
					warenkorb.get(warenkorb.indexOf(warenkorbArtikel)).setStueckzahl(neueStueckzahl);
				} else {
					throw new ArtikelBestandIstZuKleinException(warenkorb.get(warenkorb.indexOf(warenkorbArtikel)).getArtikel(), " - in 'stueckzahlAendern()'");
				}
			} else {
				warenkorb.get(warenkorb.indexOf(warenkorbArtikel)).getArtikel().setBestand(alterBestand + (alteStueckzahl - neueStueckzahl));
				warenkorb.get(warenkorb.indexOf(warenkorbArtikel)).setStueckzahl(neueStueckzahl);
			}
		} else {
			throw new ArtikelExistiertNichtException(warenkorbArtikel.getArtikel(), " - in 'stueckzahlAendern()'");
		}
	}
	
	/**
	 * Synchronisierte methode zum entfernen eines Artikels im Warenkorb.
	 * 
	 * @param warenkorbArtikel Der Warenkorb Artikel der entfernt werden soll.
	 * @throws ArtikelExistiertNichtException 
	 * @throws ArtikelBestandIstKeineVielfacheDerPackungsgroesseException 
	 */
	public synchronized void entfernen(WarenkorbArtikel warenkorbArtikel) throws ArtikelExistiertNichtException, ArtikelBestandIstKeineVielfacheDerPackungsgroesseException {
		if (warenkorb.contains(warenkorbArtikel)) {
			warenkorb.get(warenkorb.indexOf(warenkorbArtikel)).getArtikel().setBestand(warenkorbArtikel.getArtikel().getBestand() + warenkorbArtikel.getStueckzahl());
			warenkorb.remove(warenkorbArtikel);
		} else {
			throw new ArtikelExistiertNichtException(warenkorbArtikel.getArtikel(), " - in 'entfernen()'");
		}
	}
	
	/**
	 * Methode zum kaufen aller im Warenkorb enthaltenen Artikel.
	 * 
	 * @return List<WarenkorbArtikel> Eine liste von den gekauften Warenkorb Artikeln.
	 * @throws WarenkorbIstLeerException 
	 */
	public List<WarenkorbArtikel> kaufen() throws WarenkorbIstLeerException {
		if (warenkorb.isEmpty())
			throw new WarenkorbIstLeerException(" - in 'kaufen()'");
		List<WarenkorbArtikel> ergebnis = new Vector<WarenkorbArtikel>();
		Iterator<WarenkorbArtikel> iter = warenkorb.iterator();
		while (iter.hasNext()) {
			ergebnis.add(iter.next());
		}
		warenkorb.clear();
		return ergebnis;
	}
	
	/**
	 * Synchronisierte methode zum leeren des Warenkorbes.
	 * @throws ArtikelBestandIstKeineVielfacheDerPackungsgroesseException 
	 */
	public synchronized void leeren() throws ArtikelBestandIstKeineVielfacheDerPackungsgroesseException {
		Iterator<WarenkorbArtikel> iter = warenkorb.iterator();
		while (iter.hasNext()) {
			WarenkorbArtikel warenkorbArtikel = iter.next();
			warenkorbArtikel.getArtikel().setBestand(warenkorbArtikel.getArtikel().getBestand() + warenkorbArtikel.getStueckzahl());
		}
		warenkorb.clear();
	}
	
	/**
	 * Methode zum zur�ckgeben des Warenkorbes.
	 * 
	 * @erturn List<WarenkorbArtikel> Eine Referenz auf eine liste von Warenkorb Artikeln die im Warenkorb enthalten sind.
	 */
	public List<WarenkorbArtikel> getWarenkorb() {
		return warenkorb;
	}
	
	/**
	 * Methode zum zur�ckgeben eines Warenkorb Artikels.
	 * 
	 * @return WarenkorbArtikel Eine Referenz auf einen Warenkorb Artikel.
	 * @throws ArtikelExistiertNichtException 
	 */ 
	public WarenkorbArtikel getWarenkorbArtikel(Artikel artikel) throws ArtikelExistiertNichtException {
		int index = -1;

		Iterator<WarenkorbArtikel> iter = warenkorb.iterator();
		while (iter.hasNext()) {
			WarenkorbArtikel warenkorbArtikel = iter.next();
			if (warenkorbArtikel.getArtikel().equals(artikel))
				index = warenkorb.indexOf(warenkorbArtikel);
		}
		
		if (index != -1)
			return warenkorb.get(index);
		else
			throw new ArtikelExistiertNichtException(artikel, " - in 'getWarenkorbArtikel()'");
	}
	
}
