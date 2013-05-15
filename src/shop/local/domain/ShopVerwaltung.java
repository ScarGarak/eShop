package shop.local.domain;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import shop.local.domain.exceptions.ArtikelBestandIstZuKleinException;
import shop.local.domain.exceptions.ArtikelExistiertBereitsException;
import shop.local.domain.exceptions.ArtikelExistiertNichtException;
import shop.local.domain.exceptions.KundeExistiertBereitsException;
import shop.local.domain.exceptions.KundeExistiertNichtException;
import shop.local.domain.exceptions.MitarbeiterExistiertBereitsException;
import shop.local.domain.exceptions.MitarbeiterExistiertNichtException;
import shop.local.persitence.FilePersistenceManager;
import shop.local.persitence.LogPersistenceManager;
import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.Kunde;
import shop.local.valueobjects.Mitarbeiter;
import shop.local.valueobjects.Rechnung;
import shop.local.valueobjects.WarenkorbArtikel;

public class ShopVerwaltung {

	private ArtikelVerwaltung meineArtikel;
	private MitarbeiterVerwaltung meineMitarbeiter;
	private KundenVerwaltung meineKunden;
	
	// Persistenz-Schnittstelle, die für die Details des Dateizugriffs verantwortlich ist
	private LogPersistenceManager lpm = new FilePersistenceManager();
	
	/**
	 * Konstruktor, der die Basisdaten (Artikel, Mitarbeiter, Kunden) aus Dateien einliest
	 * (Initialisierung des Shops).
	 * 
	 * Namensmuster für Dateien:
	 *   "SHOP_A.ser" ist die Datei der Artikel
	 *   "SHOP_M.ser" ist die Datei der Mitarbeiter
	 *   "SHOP_K.ser" ist die Datei der Kunden
	 *   
	 * @throws IOException, z.B. wenn eine der Dateien nicht existiert.
	 * @throws ArtikelExistiertBereitsException 
	 * @throws ClassNotFoundException 
	 */
	public ShopVerwaltung() throws IOException, ArtikelExistiertBereitsException, ClassNotFoundException{
		meineArtikel = new ArtikelVerwaltung();
		meineArtikel.liesDaten("SHOP_A.ser");
		
		meineMitarbeiter = new MitarbeiterVerwaltung();
		meineMitarbeiter.liesDaten("SHOP_M.ser");
		
		meineKunden = new KundenVerwaltung();
		meineKunden.liesDaten("SHOP_K.ser");
	}
	
	// Artikel Methoden
	
	public void fuegeArtikelEin(Mitarbeiter mitarbeiter, int artikelnummer, String bezeichnung, double preis, int bestand) throws ArtikelExistiertBereitsException, IOException {
		Artikel artikel = new Artikel(artikelnummer, bezeichnung, preis, bestand);
		meineArtikel.einfuegen(artikel);
		lpm.openForWriting("EinAuslagerung.log");
		lpm.speichereEinlagerung(mitarbeiter, bestand, artikelnummer);
		lpm.close();
	}
	
	public Artikel gibArtikel(int artikelnummer) throws ArtikelExistiertNichtException {
		return meineArtikel.getArtikel(artikelnummer);
	}
	
	public void artikelBestandErhoehen(Mitarbeiter mitarbeiter, int artikelnummer, int anzahl) throws ArtikelExistiertNichtException, IOException {
		meineArtikel.bestandErhoehen(artikelnummer, anzahl);
		lpm.openForWriting("EinAuslagerung.log");
		lpm.speichereEinlagerung(mitarbeiter, anzahl, artikelnummer);
		lpm.close();
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
	
	/**
	 * Methode zum Speichern des Artikelbestands in einer Datei.
	 * 
	 * @throws IOException
	 */
	public void schreibeArtikel() throws IOException {
		meineArtikel.schreibeDaten("SHOP_A.ser");
	}
	
	// Mitarbeiter Methoden
	
	/**
	 * Diese Methode ermöglicht es einen Mitarbeiter nach seiner ID
	 * zu suchen.
	 * @param id ID der Mitarbeiter Instanz, die man suchen möchte
	 * @return Die Mitarbeiter Instanz mit der gegebenen ID.
	 */
	public Mitarbeiter sucheMitarbeiter(int id) throws MitarbeiterExistiertNichtException{
		return meineMitarbeiter.sucheMitarbeiter(id);
	}

	/**
	 * Diese Methode ermöglicht es um die Mitarbeiterliste einzusehen.
	 * @return Vector mit allen aktuellen Mitarbeiter Instanzen.
	 */
	public Vector<Mitarbeiter> gibAlleMitarbeiter(){
		return meineMitarbeiter.getMitarbeiterListe();
	}

	/**
	 * Diese Methode ermöglicht es eine Mitarbeiter Instanz zu löschen.
	 * @param m Mitarbeiter Instanz zum löschen.
	 */
	public void mitarbeiterLoeschen(Mitarbeiter m){
		meineMitarbeiter.loeschen(m);
	}

	/**
	 * Diese Methode bildet eine neue Mitarbeiter Instanz und fügt sie
	 * zur Mitarbeiterverwaltung hinzu.
	 * @param id Id des neuen Mitarbeiters
	 * @param name Name des neuen Mitarbeiters
	 * @throws MitarbeiterExistiertBereitsException
	 */
	public void fuegeMitarbeiterHinzu(int id, String name) throws MitarbeiterExistiertBereitsException{
		Mitarbeiter m = new Mitarbeiter(id, name);
		meineMitarbeiter.einfuegen(m);
	}

	/**
	 * Diese Methode schreibt alle Mitarbeiterdaten in die Datenquelle.
	 * @throws IOException
	 */
	public void schreibeMitarbeiter() throws IOException{
		meineMitarbeiter.schreibeDaten("SHOP_M.ser");
	}
	
	// Kunden Methoden
	
	/**
	* Diese Methode ermoeglicht es einen Kunden nach seiner ID
	* zu suchen.
	* @param id der Kunden Instanz, die man suchen moechte
	* @return Die Kunden Instanz mit der gegebenen ID.
	*/
	public Kunde sucheKunde(int id) throws KundeExistiertNichtException{
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
	* Diese Methode ermˆglicht es eine Kunden Instanz zu loeschen.
	* @param k Kunde Instanz zum loeschen.
	*/
	public void kundenLoeschen(Kunde k){
		meineKunden.loeschen(k);
	}

	/**
	* Diese Methode bidet eine neue Kunden Instanz und fuegt sie
	* zur Kundenverwaltung hinzu.
	* @param id Id des neuen Kunden
	* @param name Name des neuen Kunden
	* @throws KundeExistiertBereitsException
	*/
	public void fuegeKundenHinzu(int id, String name, String strasse, int plz, String wohnort) throws KundeExistiertBereitsException{
		Kunde k = new Kunde(name, id, strasse, plz, wohnort);
		meineKunden.einfuegen(k);
	}

	/**
 	* Diese Methode ermoeglicht es den "schreibe" befehl der KundenVerwaltung zu triggern.
 	* @throws IOException
 	*/
	public void schreibeKunden() throws IOException{
		meineKunden.schreibeDaten("SHOP_K.ser");
	}
	
	public void inDenWarenkorbLegen(Kunde kunde, Artikel artikel, int stueckzahl) throws ArtikelBestandIstZuKleinException, ArtikelExistiertNichtException {
		meineKunden.inDenWarenkorbLegen(kunde, new WarenkorbArtikel(artikel, stueckzahl));
	}
	
	public void ausDemWarenkorbHerausnehmen(Kunde kunde, Artikel artikel) throws ArtikelExistiertNichtException {
		meineKunden.ausDemWarenkorbHerausnehmen(kunde, kunde.getWarenkorbVerwaltung().getWarenkorbArtikel(artikel));
	}
	
	public Rechnung kaufen(Kunde kunde) throws IOException {
		Rechnung rechnung = meineKunden.kaufen(kunde);
		lpm.openForWriting("EinAuslagerung.log");
		Iterator<WarenkorbArtikel> iter = rechnung.getWarenkorb().iterator();
		while(iter.hasNext()){
			try {
				lpm.speichereAuslagerung(kunde, (WarenkorbArtikel) iter.next());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		lpm.close();
		return rechnung;
	}
	
	public void leeren(Kunde k) {
		meineKunden.leeren(k);
	}
	
}
