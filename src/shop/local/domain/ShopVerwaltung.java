package shop.local.domain;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import shop.local.domain.exceptions.ArtikelExistiertBereitsException;
import shop.local.domain.exceptions.ArtikelExistiertNichtException;
import shop.local.domain.exceptions.KundeExistiertBereitsException;
import shop.local.domain.exceptions.MitarbeiterExistiertBereitsException;
import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.Kunde;
import shop.local.valueobjects.Mitarbeiter;

public class ShopVerwaltung {

	private ArtikelVerwaltung meineArtikel;
	private MitarbeiterVerwaltung meineMitarbeiter;
	private KundenVerwaltung meineKunden;
	
	public ShopVerwaltung() {
		meineArtikel = new ArtikelVerwaltung();
		meineMitarbeiter = new MitarbeiterVerwaltung();
		meineKunden = new KundenVerwaltung();
	}
	
	
		// MITARBEITER METHODEN
	
	/**
	 * Diese Methode ermšglicht es einen Mitarbeiter nach seiner ID
	 * zu suchen.
	 * @param id der Mitarbeiter Instanz, die man suchen mšchte
	 * @return Die Mitarbeiter Instanz mit der gegebenen ID.
	 */
	public Mitarbeiter sucheMitarbeiter(int id){
		return meineMitarbeiter.sucheMitarbeiter(id);
	}

	/**
	 * Diese Methode ermšglicht es um die Mitarbeiterliste einzusehen.
	 * @return Vector mit alle aktuellen Mitarbeiter Instanzen.
	 */
	public Vector<Mitarbeiter> gibAlleMitarbeiter(){
		return meineMitarbeiter.getMitarbeiterListe();
	}

	/**
	 * Diese Methode ermšglicht es eine Mitarbeiter Instanz zu lšschen.
	 * @param m Mitarbeiter Instanz zum lšschen.
	 */
	public void mitarbeiterLoeschen(Mitarbeiter m){
		meineMitarbeiter.loeschen(m);
	}

	/**
	 * Diese Methode bidet eine neue Mitarbeiter Instanz und fŸgt sie
	 * zur Mitarbeiterverwaltung hinzu.
	 * Bevor diese Instanz gebildet wird, wird geprŸft, ob die
	 * angegebene id noch nicht existiert.
	 * @param id Id des neuen Mitarbeiters
	 * @param vorname Vorname des neuen Mitarbeiters
	 * @param nachname Nachname des neuen Mitarbeiters
	 * @throws MitarbeiterExistiertBereitsException 
	 * @throws IDExistiertBereitsException Wird geworfen, wenn die angegebene ID schon existiert.
	 */
	public void fuegeMitarbeiterHinzu(int id, String name) throws MitarbeiterExistiertBereitsException{
		Mitarbeiter m = new Mitarbeiter(id, name);
		meineMitarbeiter.einfuegen(m);
	}

	/**
	 * Diese Methode ermšglicht es den "schreibe" befehl der Mitarbeiterverwaltung zu triggern.
	 * @throws IOException
	 */
	public void schreibeMitarbeiter() throws IOException{
		meineMitarbeiter.schreibeDaten("test");
	}
	
	// Kunden METHODEN
	
/**
 * Diese Methode ermšglicht es einen Kunden nach seiner ID
 * zu suchen.
 * @param id der Kunden Instanz, die man suchen moechte
 * @return Die Kunden Instanz mit der gegebenen ID.
 */
public Kunde sucheKunde(int id){
	return meineKunden.sucheKunde(id);
}

/**
 * Diese Methode ermoeglicht es um die Kundenliste einzusehen.
 * @return Vector mit alle aktuellen Kunden Instanzen.
 */
public Vector<Kunde> gibAlleKunden(){
	return meineKunden.getKundenListe();
}

/**
 * Diese Methode ermöglicht es eine Kunden Instanz zu loeschen.
 * @param k Kunde Instanz zum loeschen.
 */
public void kundenLoeschen(Kunde k){
	meineKunden.loeschen(k);
}

/**
 * Diese Methode bidet eine neue Kunden Instanz und fuegt sie
 * zur Kundenverwaltung hinzu.
 * Bevor diese Instanz gebildet wird, wird geprüft, ob die
 * angegebene id noch nicht existiert.
 * @param id Id des neuen Kunden
 * @param name Name des neuen Kunden
 * @throws KundeExistiertBereitsException 
 * @throws IDExistiertBereitsException Wird geworfen, wenn die angegebene ID schon existiert.
 */
public void fuegeKundenHinzu(int id, String name, String strasse, int plz, String wohnort) throws KundeExistiertBereitsException{
	Kunde k = new Kunde(name, id, strasse, plz, wohnort);
	meineKunden.einfuegen(k);
}

/**
 * Diese Methode ermšglicht es den "schreibe" befehl der KundenVerwaltung zu triggern.
 * @throws IOException
 */
public void schreibeKunden() throws IOException{
	meineKunden.schreibeDaten("test");
}

///////////////////////////////////////////////////////
	
	
	
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
