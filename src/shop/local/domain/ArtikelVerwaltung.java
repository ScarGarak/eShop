package shop.local.domain;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import shop.local.domain.exceptions.ArtikelExistiertBereitsException;
import shop.local.domain.exceptions.ArtikelExistiertNichtException;
import shop.local.valueobjects.Artikel;

/**
 * Klasse zur Verwaltung von Artikeln.
 * 
 * @author Christof Ferreira Torres
 */
public class ArtikelVerwaltung {

	// Verwaltung des Artikelbestands in einem Vector
	private List<Artikel> artikelBestand = new Vector<Artikel>();
	
	public void einfuegen(Artikel artikel) throws ArtikelExistiertBereitsException {
		if (!artikelBestand.contains(artikel))
			artikelBestand.add(artikel);
		else
			throw new ArtikelExistiertBereitsException(artikel, " - in 'einfuegen()'");
	}
	
	public List<Artikel> sucheArtikel(int artikelnummer) {
		List<Artikel> ergebnis = new Vector<Artikel>();
		
		Iterator<Artikel> iter = artikelBestand.iterator();
		while (iter.hasNext()) {
			Artikel artikel = iter.next();
			if (artikel.getArtikelnummer() == artikelnummer) {
				ergebnis.add(artikel);
			}
		}
		
		return ergebnis;
	}
	
	public List<Artikel> sucheArtikel(String bezeichnung) {
		List<Artikel> ergebnis = new Vector<Artikel>();
		
		Iterator<Artikel> iter = artikelBestand.iterator();
		while (iter.hasNext()) {
			Artikel artikel = iter.next();
			if (artikel.getBezeichnung().toLowerCase().contains(bezeichnung.toLowerCase())) {
				ergebnis.add(artikel);
			}
		}
		
		return ergebnis;
	}
	
	public void entfernen(int artikelnummer) throws ArtikelExistiertNichtException {
		int index = -1;

		Iterator<Artikel> iter = artikelBestand.iterator();
		while (iter.hasNext()) {
			Artikel artikel = iter.next();
			if (artikel.getArtikelnummer() == artikelnummer)
				index = artikelBestand.indexOf(artikel);
		}
		
		if (index != -1)
			artikelBestand.remove(index);
		else
			throw new ArtikelExistiertNichtException(artikelnummer, " - in 'entfernen()'");
	}
	
	public void entfernen(String bezeichnung) throws ArtikelExistiertNichtException {
		int index = -1;

		Iterator<Artikel> iter = artikelBestand.iterator();
		while (iter.hasNext()) {
			Artikel artikel = iter.next();
			if (artikel.getBezeichnung().equals(bezeichnung))
				index = artikelBestand.indexOf(artikel);
		}
		
		if (index != -1)
			artikelBestand.remove(index);
		else
			throw new ArtikelExistiertNichtException(bezeichnung, " - in 'entfernen()'");
	}
	
	public List<Artikel> getArtikelBestand() {
		List<Artikel> ergebnis = new Vector<Artikel>();
		ergebnis.addAll(artikelBestand);
		return ergebnis;
	}
	
	public List<Artikel> getArtikelBestandSortiertNachArtikelnummer() {
		List<Artikel> ergebnis = new Vector<Artikel>();
		ergebnis.addAll(artikelBestand);
		Collections.sort(ergebnis, new SortierungNachArtikelnummer());
		return ergebnis;
	}
	
	public List<Artikel> getArtikelBestandSortiertNachBezeichnung() {
		List<Artikel> ergebnis = new Vector<Artikel>();
		ergebnis.addAll(artikelBestand);
		Collections.sort(ergebnis, new SortierungNachBezeichnung());
		return ergebnis;
	}
	
}
