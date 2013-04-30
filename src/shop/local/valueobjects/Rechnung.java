package shop.local.valueobjects;

import java.util.Date;

import shop.local.domain.ArtikelVerwaltung;
import shop.local.domain.KundenVerwaltung;
import shop.local.domain.WarenkorbVerwaltung;


/**
 *  Klasse zur erstellung eines Rechnungsobjektes
 * 
 * @author Oliver Thummerer
 *
 */

public class Rechnung {
	
	
	private Kunde kunde;
	private Date datum;
	
	
	public Rechnung(Kunde kunde, Date datum) {
		this.kunde = kunde;
		this.datum = datum;
	}
	
	public Kunde getKunden() {
		return kunde;
	}
	
	public void setDatum(Date datum) {
		this.datum = new Date();
	}
	
	public Date getDatum() {
		return datum;
	}
	
}
