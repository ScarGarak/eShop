package shop.local.valueobjects;

import shop.local.domain.ArtikelVerwaltung;

public class Kunde extends Person {

	private String strasse;
	private int plz;
	private String wohnort;
	private ArtikelVerwaltung warenkorb;
	
	public Kunde(String name, int id, String strasse, int plz, String wohnort) {
		super(id, name);
		this.strasse = strasse;
		this.plz = plz;
		this.wohnort = wohnort;
		warenkorb = new ArtikelVerwaltung();
	}
	
	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}
	
	public String getStrasse() {
		return strasse;
	}
	
	public void setPlz(int plz) {
		this.plz = plz;
	}
	
	public int getPlz() {
		return plz;
	}
	
	public void setWohnort(String wohnort) {
		this.wohnort = wohnort;
	}
	
	public String getWohnort() {
		return wohnort;
	}
	
	public ArtikelVerwaltung getWarenkorb() {
		return warenkorb;
	}
	
	public String toString() {
		return "Kundennummer: " + getId() + "\n" + getName() + "\n" + strasse + "\n" + plz + " " + wohnort;
	}

}
