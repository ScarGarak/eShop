package shop.local.valueobjects;

import java.util.Date;

import shop.local.domain.ArtikelVerwaltung;
import shop.local.domain.KundenVerwaltung;


public class Rechnung {
	
	
	private KundenVerwaltung kunde;
	private Date datum;
	private ArtikelVerwaltung warenkorb;
	
	public Rechnung(String kunde, Date datum, String artikel, int preis) {
		kunde = new KundenVerwaltung().toString();
		this.datum = datum;
		warenkorb = new ArtikelVerwaltung();
	}
	
	public KundenVerwaltung getKunden() {
		return kunde;
	}
	
	public void setDatum(Date datum) {
		this.datum = new Date();
	}
	
	public Date getDatum() {
		return datum;
	}
	
	public ArtikelVerwaltung getWarenkorb() {
		return warenkorb;
	}
		
}
