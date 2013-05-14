package shop.local.valueobjects;

import shop.local.domain.WarenkorbVerwaltung;

public class Kunde extends Person {

	private static final long serialVersionUID = 4664724545004822767L;
	
	// Attribute zur Beschreibung des Kunden	
	private String strasse;
	private int plz;
	private String wohnort;
	private transient WarenkorbVerwaltung warenkorb;
	
	public Kunde(String name, int id, String strasse, int plz, String wohnort) {
		super(id, name, PersonTyp.Kunde);
		this.strasse = strasse;
		this.plz = plz;
		this.wohnort = wohnort;
		warenkorb = new WarenkorbVerwaltung();
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
	
	public WarenkorbVerwaltung getWarenkorbVerwaltung() {
		return warenkorb;
	}
	
	public String toString() {
		return "Kundennummer: " + getId() + "\n" + getName() + "\n" + strasse + "\n" + plz + " " + wohnort;
	}
	
}
