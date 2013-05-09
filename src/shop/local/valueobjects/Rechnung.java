package shop.local.valueobjects;

import java.util.Date;
import java.util.List;

/**
 *  Klasse zur erstellung eines Rechnungsobjektes
 * 
 * @author Oliver Thummerer
 *
 */

public class Rechnung {
	
	private Kunde kunde;
	private Date datum;
	private List<WarenkorbArtikel> warenkorb;
	
	public Rechnung(Kunde kunde, Date datum, List<WarenkorbArtikel> warenkorb) {
		this.kunde = kunde;
		this.datum = datum;
		this.warenkorb = warenkorb;
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
	
	public List<WarenkorbArtikel> getWarenkorb() {
		return warenkorb;
	}
	
	
	
}
