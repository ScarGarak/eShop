package shop.local.valueobjects;

import java.util.Date;

import shop.local.domain.ArtikelVerwaltung;
import shop.local.domain.KundenVerwaltung;


public class Rechnung {
	
	
	private KundenVerwaltung kunde;
	private Date datum;
	private ArtikelVerwaltung warenkorb;
	
	
	public Rechnung(Date datum) {
		kunde = new KundenVerwaltung();
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
