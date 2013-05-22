package shop.local.valueobjects;

import java.io.Serializable;
import java.util.Currency;
import java.util.Locale;

import shop.local.domain.exceptions.ArtikelBestandIstKeineVielfacheDerPackungsgroesseException;

/**
 * Klasse zur Repräsentation einzelner Artikel.
 * 
 * @author Christof Ferreira Torres
 */
public class Artikel implements Serializable {
	
	private static final long serialVersionUID = -1025371623530827772L;
	
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
	
	public void setBestand(int bestand) throws ArtikelBestandIstKeineVielfacheDerPackungsgroesseException {
		this.bestand = bestand;
	}
	
	public int getBestand() {
		return bestand;
	}
	
	public String toString() {
		return ("Nr: " + artikelnummer + "\t Bezeichnung: " + bezeichnung + "\t Preis: " + String.format("%.2f ", preis) + Currency.getInstance(Locale.GERMANY) + "\t Bestand: " + bestand);
	}
	
	public boolean equals(Object andererArtikel) {
		if (andererArtikel instanceof Artikel) {
			return (this.artikelnummer == ((Artikel) andererArtikel).artikelnummer);
		} else {
			return false;
		}
	}

}
