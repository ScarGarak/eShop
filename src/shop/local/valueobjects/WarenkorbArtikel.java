package shop.local.valueobjects;

/**
 * Klasse zur Repräsentation einzelner Warenkorb Artikel.
 * 
 * @author Christof Ferreira Torres
 */
public class WarenkorbArtikel {

	// Attribute zur Beschreibung eines Warenkorb Artikels:
	private Artikel artikel;
	private int stueckzahl;
	
	public WarenkorbArtikel(Artikel artikel, int stueckzahl) {
		this.artikel = artikel;
		this.stueckzahl = stueckzahl;
	}
	
	public void setArtikel(Artikel artikel) {
		this.artikel = artikel;
	}
	
	public Artikel getArtikel() {
		return artikel;
	}
	
	public void setStueckzahl(int stueckzahl) {
		this.stueckzahl = stueckzahl;
	}
	
	public int getStueckzahl() {
		return stueckzahl;
	}
	
	public String toString() {
		return ("Nr: " + artikel.getArtikelnummer() + "\t Bezeichnung: " + artikel.getBezeichnung() + "\t Stückzahl: " + stueckzahl);
	}
	
	public boolean equals(Object andererWarenkorbArtikel) {
		if (andererWarenkorbArtikel instanceof WarenkorbArtikel) {
			return (this.artikel.equals(((WarenkorbArtikel) andererWarenkorbArtikel).getArtikel()));
		} else {
			return false;
		}
	}
	
}
