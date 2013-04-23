package shop.local.domain;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import shop.local.domain.MitarbeiterVerwaltung;
import shop.local.domain.exceptions.MitarbeiterExistiertBereitsException;

import shop.local.domain.exceptions.ArtikelExistiertBereitsException;
import shop.local.domain.exceptions.ArtikelExistiertNichtException;
import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.Mitarbeiter;

public class ShopVerwaltung {

	private ArtikelVerwaltung meineArtikel;
	private MitarbeiterVerwaltung meineMitarbeiter;
	
	public ShopVerwaltung() {
		meineArtikel = new ArtikelVerwaltung();
		meineMitarbeiter = new MitarbeiterVerwaltung();
	}
	
	
		// MITARBEITER METHODEN
	
	/**
	 * Diese Methode erm�glicht es einen Mitarbeiter nach seiner ID
	 * zu suchen.
	 * @param id der Mitarbeiter Instanz, die man suchen m�chte
	 * @return Die Mitarbeiter Instanz mit der gegebenen ID.
	 */
	public Mitarbeiter sucheMitarbeiter(int id){
		return meineMitarbeiter.sucheMitarbeiter(id);
	}

	/**
	 * Diese Methode erm�glicht es um die Mitarbeiterliste einzusehen.
	 * @return Vector mit alle aktuellen Mitarbeiter Instanzen.
	 */
	public Vector<Mitarbeiter> gibAlleMitarbeiter(){
		return meineMitarbeiter.getMitarbeiterListe();
	}

	/**
	 * Diese Methode erm�glicht es eine Mitarbeiter Instanz zu l�schen.
	 * @param m Mitarbeiter Instanz zum l�schen.
	 */
	public void mitarbeiterLoeschen(Mitarbeiter m){
		meineMitarbeiter.loeschen(m);
	}

	/**
	 * Diese Methode bidet eine neue Mitarbeiter Instanz und f�gt sie
	 * zur Mitarbeiterverwaltung hinzu.
	 * Bevor diese Instanz gebildet wird, wird gepr�ft, ob die
	 * angegebene id noch nicht existiert.
	 * @param id Id des neuen Mitarbeiters
	 * @param vorname Vorname des neuen Mitarbeiters
	 * @param nachname Nachname des neuen Mitarbeiters
	 * @throws MitarbeiterExistiertBereitsException 
	 * @throws IDExistiertBereitsException Wird geworfen, wenn die angegebene ID schon existiert.
	 */
	public void fuegeMitarbeiterHinzu(int id, String vorname, String nachname) throws MitarbeiterExistiertBereitsException{
		Mitarbeiter m = new Mitarbeiter(id, vorname, nachname);
		meineMitarbeiter.einfuegen(m);
	}

	/**
	 * Diese Methode erm�glicht es den "schreibe" befehl der Mitarbeiterverwaltung zu triggern.
	 * @throws IOException
	 */
	public void schreibeMitarbeiter() throws IOException{
		meineMitarbeiter.schreibeDaten("test");
	}
	
	
	
	
	
	public void fuegeArtikelEin(int artikelnummer, String bezeichnung, int bestand) throws ArtikelExistiertBereitsException {
		Artikel artikel = new Artikel(artikelnummer, bezeichnung, bestand);
		meineArtikel.einfuegen(artikel);
	}
	
	public List<Artikel> gibAlleArtikelSortiertNachArtikelnummer() {
		return meineArtikel.getArtikelBestandSortiertNachArtikelnummer();
	}
	
	public List<Artikel> gibAlleArtikelSortiertNachBezeichnung() {
		return meineArtikel.getArtikelBestandSortiertNachBezeichnung();
	}
	
	public List<Artikel> sucheArtikel(int artikelnummer) {
		return meineArtikel.sucheArtikel(artikelnummer); 
	}
	
	public List<Artikel> sucheArtikel(String bezeichnung) {
		return meineArtikel.sucheArtikel(bezeichnung); 
	}
	
	public void EntferneArtikel(int artikelnummer) throws ArtikelExistiertNichtException {
		meineArtikel.entfernen(artikelnummer);
	}
	
	public void EntferneArtikel(String bezeichnung) throws ArtikelExistiertNichtException {
		meineArtikel.entfernen(bezeichnung);
	}
	
}
