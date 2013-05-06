package shop.local.valueobjects;

import java.util.Date;

import shop.local.domain.KundenVerwaltung;
import shop.local.domain.WarenkorbVerwaltung;


/**
 *  Klasse zur erstellung eines Rechnungsobjektes
 * 
 * @author Oliver Thummerer
 *
 */

public class Rechnung {
	
	
	private KundenVerwaltung kunde;
	private Date datum;
	private WarenkorbVerwaltung warenkorb;
	
	
	public Rechnung(Date datum) {
		kunde = new KundenVerwaltung();
		this.datum = datum;
		warenkorb = new WarenkorbVerwaltung();
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
	
	public WarenkorbVerwaltung getWarenkorb() {
		return warenkorb;
	}
		
}
