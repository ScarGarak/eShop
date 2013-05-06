package shop.local.valueobjects;

import shop.local.domain.WarenkorbVerwaltung;

public class Kunde extends Person {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String strasse;
	private int plz;
	private String wohnort;
	private WarenkorbVerwaltung warenkorb;
	
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
	
	public WarenkorbVerwaltung getWarenkorb() {
		return warenkorb;
	}
	
	public String toString() {
		return "Kundennummer: " + getId() + "\n" + getName() + "\n" + strasse + "\n" + plz + " " + wohnort;
	}
	
	// Implementiert die abstract method equals aus abstract class ABCs (Demoklasse)
	
	public boolean equals(Object o) {
		if (o instanceof Kunde) {
			return true;
		}
		return false;
	}

}
