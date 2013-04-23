package shop.local.valueobjects;


/**
 * Klasse zur Repräsentation einzelner Artikel.
 * 
 * @author Christof Ferreira Torres
 */
public class Artikel {
	
	// Attribute zur Beschreibung eines Artikels:
	private int artikelnummer;
	private String bezeichnung;
	private int bestand;
	
	public Artikel(int artikelnummer, String bezeichnung, int bestand) {
		this.artikelnummer = artikelnummer;
		this.bezeichnung = bezeichnung;
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
	
	public void setBestand(int bestand) {
		this.bestand = bestand;
	}
	
	public int getBestand() {
		return bestand;
	}
	
	public String toString() {
		return ("Nr: " + artikelnummer + "\t Bezeichnung: " + bezeichnung + "\t Bestand: " + bestand + "\t");
	}
	
	public boolean equals(Object andererArtikel) {
		if (andererArtikel instanceof Artikel) {
			return ((this.artikelnummer == ((Artikel) andererArtikel).artikelnummer) 
					&& (this.bezeichnung.equals(((Artikel) andererArtikel).bezeichnung)));
		} else {
			return false;
		}
	}

}
