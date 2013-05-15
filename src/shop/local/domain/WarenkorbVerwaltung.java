package shop.local.domain;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import shop.local.domain.exceptions.ArtikelBestandIstZuKleinException;
import shop.local.domain.exceptions.ArtikelExistiertNichtException;
import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.WarenkorbArtikel;

/**
 * Klasse zur Verwaltung vom Warenkorb.
 * 
 * @author Christof Ferreira Torres
 */
public class WarenkorbVerwaltung {
	
	// Verwaltung des Warenkorbes in einem Vector
	private List<WarenkorbArtikel> warenkorb = new Vector<WarenkorbArtikel>();
	
	/**
	 * Synchronisierte methode zum hinzufügen eines Warenkorb Artikels in den Warenkorb.
	 * 
	 * @param warenkorbArtikel Der Warenkorb Artikel der in den Warenkorb hinzugefügt werden soll.
	 * @throws ArtikelBestandIstZuKleinException 
	 * @throws ArtikelExistiertNichtException 
	 */
	public synchronized void hinzufuegen(WarenkorbArtikel warenkorbArtikel) throws ArtikelBestandIstZuKleinException, ArtikelExistiertNichtException {
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
	 * Synchronisierte methode zum ändern der Stückzahl eines Artikels im Warenkorb.
	 * 
	 * @param warenkorbArtikel Der Warenkorb Artikel dessen Stückzahl verändert werden soll.
	 * @param neueStueckzahl Die neue Stückzahl des Warenkorb Artikels.
	 * @throws ArtikelBestandIstZuKleinException 
	 * @throws ArtikelExistiertNichtException 
	 */
	public synchronized void stueckzahlAendern(WarenkorbArtikel warenkorbArtikel, int neueStueckzahl) throws ArtikelBestandIstZuKleinException, ArtikelExistiertNichtException {
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
	 */
	public synchronized void entfernen(WarenkorbArtikel warenkorbArtikel) throws ArtikelExistiertNichtException {
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
	 */
	public List<WarenkorbArtikel> kaufen() {
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
	 */
	public synchronized void leeren() {
		Iterator<WarenkorbArtikel> iter = warenkorb.iterator();
		while (iter.hasNext()) {
			WarenkorbArtikel warenkorbArtikel = iter.next();
			warenkorbArtikel.getArtikel().setBestand(warenkorbArtikel.getArtikel().getBestand() + warenkorbArtikel.getStueckzahl());
		}
		warenkorb.clear();
	}
	
	/**
	 * Methode zum zurückgeben des Warenkorbes.
	 * 
	 * @erturn List<WarenkorbArtikel> Eine Referenz auf eine liste von Warenkorb Artikeln die im Warenkorb enthalten sind.
	 */
	public List<WarenkorbArtikel> getWarenkorb() {
		return warenkorb;
	}
	
	/**
	 * Methode zum zurückgeben eines Warenkorb Artikels.
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
