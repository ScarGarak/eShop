package shop.local.valueobjects;

import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 *  Klasse zur erstellung eines Rechnungsobjektes
 * 
 * @author Oliver Thummerer & Christof Ferreira Torres
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
	
	public Kunde getKunde() {
		return kunde;
	}
	
	public Date getDatum() {
		return datum;
	}
	
	public List<WarenkorbArtikel> getWarenkorb() {
		return warenkorb;
	}
	
	/**
	 * Methode zum berechnen des Gesamtpreises aller Warenkorb Artiekl.
	 * 
	 * @return double Den Gesamtpreis aller Warenkorb Artikel.
	 */
	public double getGesamtpreis() {
		Iterator<WarenkorbArtikel> iter = warenkorb.iterator();
		double summe = 0.0;
		while (iter.hasNext()) {
			WarenkorbArtikel warenkorbArtikel = iter.next();
			summe += warenkorbArtikel.getStueckzahl() * warenkorbArtikel.getArtikel().getPreis();
		}
		return summe;
	}
	
	/**
	 * Methode zum ausgeben des Rechnungsobjekts. 
	 * 
	 * @return String Die Ausgabe, wie z.B. den Kunden, die gekauften Artikel, etc.
	 */
	public String toString() {
		String ergebnis = ""; 
		ergebnis += (kunde.toString());
		ergebnis += ("\nDatum: " + new SimpleDateFormat("yyyy-MM-dd").format(datum) + "\n");
		ergebnis += ("\nArtikel:\n");
		Iterator<WarenkorbArtikel> iter = warenkorb.iterator();
		while (iter.hasNext()) {
			ergebnis += (iter.next().toString() + "\n");
		}
		ergebnis += ("Gesamtpreis: " + String.format("%.2f ", getGesamtpreis()) + Currency.getInstance(Locale.GERMANY) + "\n");
		return ergebnis;
	}
	
}
