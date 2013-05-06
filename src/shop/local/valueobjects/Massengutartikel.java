package shop.local.valueobjects;

import java.util.Currency;
import java.util.Locale;

/**
 * Klasse zur Repräsentation von Massengutartikeln.
 * 
 * @author Christof Ferreira Torres
 */
public class Massengutartikel extends Artikel {

	private static final long serialVersionUID = 2184308579779058138L;
	
	// Attribute zur Beschreibung eines Massengutartikels:
	private int packungsgroesse;	

	public Massengutartikel(int artikelnummer, String bezeichnung, double preis, int packungsgroesse, int bestand) {
		super(artikelnummer, bezeichnung, preis, bestand);
		this.packungsgroesse = packungsgroesse;
	}
	
	public void setPackungsgroesse(int packungsgroesse) {
		this.packungsgroesse = packungsgroesse;
	}
	
	public int getPackungsgroesse() {
		return packungsgroesse;
	}
	
	public String toString() {
		return ("Nr: " + getArtikelnummer() + "\t Bezeichnung: " + getBezeichnung() + "\t Preis: " + String.format("%.2f ", getPreis()) + Currency.getInstance(Locale.GERMANY) + "\t Packungsgröße: " + packungsgroesse + "\t Bestand: " + getBestand() + "\t");
	}
	
	public boolean equals(Object andererArtikel) {
		if (andererArtikel instanceof Massengutartikel) {
			return (this.getArtikelnummer() == ((Massengutartikel) andererArtikel).getArtikelnummer());
		} else {
			return false;
		}
	}

}
