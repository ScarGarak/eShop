package shop.local.valueobjects;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *  Klasse zur erstellung eines Rechnungsobjektes
 * 
 * @author Oliver Thummerer & Christof Ferreira Torres
 *
 */
public class Rechnung {
	
	// Attribute zur Beschreibung einer Rechnung:
	private Kunde kunde;
	private Date datum;
	private List<WarenkorbArtikel> warenkorb;
	
	public Rechnung(Kunde kunde, Date datum, List<WarenkorbArtikel> warenkorb) {
		this.kunde = kunde;
		this.datum = datum;
		this.warenkorb = warenkorb;
	}
	
	public Kunde getKunden() {
		return kunde;
	}
	
	public Date getDatum() {
		return datum;
	}
	
	public List<WarenkorbArtikel> getWarenkorb() {
		return warenkorb;
	}
	
	public double getGesamtpreis() {
		Iterator<WarenkorbArtikel> iter = warenkorb.iterator();
		double summe = 0.0;
		while (iter.hasNext()) {
			WarenkorbArtikel warenkorbArtikel = iter.next();
			summe += warenkorbArtikel.getStueckzahl() * warenkorbArtikel.getArtikel().getPreis();
		}
		return summe;
	}
	
	public String toString() {
		String ergebnis = ""; 
		ergebnis += ("Kundennummer: " + kunde.getId() + "\n");
		ergebnis += (kunde.getName() + "\n");
		ergebnis += (kunde.getStrasse() + "\n");
		ergebnis += (kunde.getPlz() + " " + kunde.getWohnort() + "\n");
		ergebnis += ("\n Datum: " + datum + "\n");
		ergebnis += ("\n Artikel: \n");
		Iterator<WarenkorbArtikel> iter = warenkorb.iterator();
		while (iter.hasNext()) {
			ergebnis += (iter.next().toString());
		}
		ergebnis += ("Gesamtpreis: " + getGesamtpreis() + "\n");
		return ergebnis;
	}
	
}
