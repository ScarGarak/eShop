package shop.local.valueobjects;

import java.util.Currency;
import java.util.Locale;

/**
 * Klasse zur Repr�sentation einzelner Artikel.
 * 
 * @author Christof Ferreira Torres
 */
public class Artikel {
	
	// Attribute zur Beschreibung eines Artikels:
	private int artikelnummer;
	private String bezeichnung;
	private double preis;
	private int bestand;
	
	public Artikel(int artikelnummer, String bezeichnung, double preis, int bestand) {
		this.artikelnummer = artikelnummer;
		this.bezeichnung = bezeichnung;
		this.preis = preis;
		this.bestand = bestand;
	}
	
	public void setArtikelnummer(int artikelnummer) {
		this.artikelnummer = artikelnummer;
	}
	
	public int getArtikelnummer() {
		return artikelnummer;
	}
	
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	
	public String getBezeichnung() {
		return bezeichnung;
	}
	
	public void setPreis(double preis) {
		this.preis = preis;
	}
	
	public double getPreis() {
		return preis;
	}
	
	public void setBestand(int bestand) {
		this.bestand = bestand;
	}
	
	public int getBestand() {
		return bestand;
	}
	
	public String toString() {
		return ("Nr: " + artikelnummer + "\t Bezeichnung: " + bezeichnung + "\t Preis: " + String.format("%.2f ", preis) + Currency.getInstance(Locale.GERMANY) + "\t Bestand: " + bestand + "\t");
	}
	
	public boolean equals(Object andererArtikel) {
		if (andererArtikel instanceof Artikel) {
			return (this.artikelnummer == ((Artikel) andererArtikel).artikelnummer);
		} else {
			return false;
		}
	}

}
