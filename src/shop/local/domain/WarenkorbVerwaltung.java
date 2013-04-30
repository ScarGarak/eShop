package shop.local.domain;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import shop.local.domain.exceptions.ArtikelBestandException;
import shop.local.domain.exceptions.ArtikelExistiertNichtException;
import shop.local.valueobjects.WarenkorbArtikel;

/**
 * Klasse zur Verwaltung vom Warenkorb.
 * 
 * @author Christof Ferreira Torres
 */
public class WarenkorbVerwaltung {

	// Verwaltung des Warenkorbes in einem Vector
	private List<WarenkorbArtikel> warenkorb = new Vector<WarenkorbArtikel>();
	
	public synchronized void hinzufuegen(WarenkorbArtikel warenkorbArtikel) throws ArtikelBestandException, ArtikelExistiertNichtException {
		if (warenkorb.contains(warenkorbArtikel)) {
			this.stueckzahlAendern(warenkorbArtikel, warenkorb.get(warenkorb.indexOf(warenkorbArtikel)).getStueckzahl() + warenkorbArtikel.getStueckzahl());
		} else {
			int alterBestand = warenkorbArtikel.getArtikel().getBestand();
			if (alterBestand - warenkorbArtikel.getStueckzahl() >= 0) {
				warenkorbArtikel.getArtikel().setBestand(alterBestand - warenkorbArtikel.getStueckzahl());
				warenkorb.add(warenkorbArtikel);
			} else {
				throw new ArtikelBestandException(warenkorb.get(warenkorb.indexOf(warenkorbArtikel)).getArtikel(), " - in 'stueckzahlAendern()'");
			}
		}
	}
	
	public synchronized void stueckzahlAendern(WarenkorbArtikel warenkorbArtikel, int neueStueckzahl) throws ArtikelBestandException, ArtikelExistiertNichtException {
		if (warenkorb.contains(warenkorbArtikel)) {
			int alterBestand = warenkorb.get(warenkorb.indexOf(warenkorbArtikel)).getArtikel().getBestand();
			int alteStueckzahl = warenkorb.get(warenkorb.indexOf(warenkorbArtikel)).getStueckzahl();
			if (alteStueckzahl < neueStueckzahl) {
				if (alterBestand - (neueStueckzahl - alteStueckzahl) >= 0) {
					warenkorb.get(warenkorb.indexOf(warenkorbArtikel)).getArtikel().setBestand(alterBestand - (neueStueckzahl - alteStueckzahl));
					warenkorb.get(warenkorb.indexOf(warenkorbArtikel)).setStueckzahl(neueStueckzahl);
				} else {
					throw new ArtikelBestandException(warenkorb.get(warenkorb.indexOf(warenkorbArtikel)).getArtikel(), " - in 'stueckzahlAendern()'");
				}
			} else {
				warenkorb.get(warenkorb.indexOf(warenkorbArtikel)).getArtikel().setBestand(alterBestand + (alteStueckzahl - neueStueckzahl));
				warenkorb.get(warenkorb.indexOf(warenkorbArtikel)).setStueckzahl(neueStueckzahl);
			}
		} else {
			throw new ArtikelExistiertNichtException(warenkorb.get(warenkorb.indexOf(warenkorbArtikel)).getArtikel(), " - in 'stueckzahlAendern()'");
		}
	}
	
	public synchronized void leeren() {
		Iterator<WarenkorbArtikel> iter = warenkorb.iterator();
		while (iter.hasNext()) {
			WarenkorbArtikel warenkorbArtikel = iter.next();
			warenkorbArtikel.getArtikel().setBestand(warenkorbArtikel.getArtikel().getBestand() + warenkorbArtikel.getStueckzahl());
		}
		warenkorb.clear();
	}
	
}
