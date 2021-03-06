package shop.local.domain;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import shop.local.domain.exceptions.ArtikelBestandIstKeineVielfacheDerPackungsgroesseException;
import shop.local.domain.exceptions.ArtikelBestandIstZuKleinException;
import shop.local.domain.exceptions.ArtikelExistiertBereitsException;
import shop.local.domain.exceptions.ArtikelExistiertNichtException;
import shop.local.domain.exceptions.KundeExistiertBereitsException;
import shop.local.domain.exceptions.KundeExistiertNichtException;
import shop.local.domain.exceptions.MitarbeiterExistiertBereitsException;
import shop.local.domain.exceptions.MitarbeiterExistiertNichtException;
import shop.local.domain.exceptions.UsernameExistiertBereitsException;
import shop.local.domain.exceptions.WarenkorbIstLeerException;
import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.Ereignis;
import shop.local.valueobjects.Kunde;
import shop.local.valueobjects.Massengutartikel;
import shop.local.valueobjects.Mitarbeiter;
import shop.local.valueobjects.MitarbeiterFunktion;
import shop.local.valueobjects.Person;
import shop.local.valueobjects.Rechnung;
import shop.local.valueobjects.WarenkorbArtikel;

/**
 * Klasse zur Verwaltung vom eShop.
 * 
 * @author Christof Ferreira Torres, Angelo Migliosi & Oliver Thummerer
 * @version 1.0.0
 */
public class ShopVerwaltung {
	
	private String artikelDateiname = "SHOP_A.ser";
	private String mitarbeiterDateiname = "SHOP_M.ser";
	private String kundenDateiname = "SHOP_K.ser";
	private String logDateiname = "EinAuslagerung.log";

	private ArtikelVerwaltung meineArtikel;
	private MitarbeiterVerwaltung meineMitarbeiter;
	private KundenVerwaltung meineKunden;
	private EreignisVerwaltung meineEreignisse;
	
	private int mitarbeiterNextId;
	private int kundenNextId;
	
	/**
	 * Konstruktor, der die Basisdaten (Artikel, Mitarbeiter, Kunden) aus Dateien einliest
	 * (Initialisierung des Shops).
	 * 
	 * Namensmuster f�r Dateien:
	 *   "SHOP_A.ser" ist die Datei der Artikel
	 *   "SHOP_M.ser" ist die Datei der Mitarbeiter
	 *   "SHOP_K.ser" ist die Datei der Kunden
	 *   
	 * @throws IOException, z.B. wenn eine der Dateien nicht existiert.
	 * @throws ArtikelExistiertBereitsException 
	 * @throws ClassNotFoundException 
	 */
	public ShopVerwaltung() throws IOException {
		meineArtikel = new ArtikelVerwaltung();
		meineArtikel.liesDaten(artikelDateiname);
		
		meineMitarbeiter = new MitarbeiterVerwaltung();
		meineMitarbeiter.liesDaten(mitarbeiterDateiname);
		mitarbeiterNextId = meineMitarbeiter.getMitarbeiterListe().get(meineMitarbeiter.getMitarbeiterListe().size()-1).getId() + 1;
		
		meineKunden = new KundenVerwaltung();
		meineKunden.liesDaten(kundenDateiname);
		kundenNextId = meineKunden.getKundenListe().get(meineKunden.getKundenListe().size()-1).getId() + 1;
		
		meineEreignisse = new EreignisVerwaltung();
	}
	
	// Artikel Methoden
	
	public void fuegeArtikelEin(Mitarbeiter mitarbeiter, int artikelnummer, String bezeichnung, double preis, int bestand) throws ArtikelExistiertBereitsException {
		Artikel artikel = new Artikel(artikelnummer, bezeichnung, preis, bestand);
		meineArtikel.einfuegen(artikel);
		
		meineEreignisse.hinzufuegen(new Ereignis(new Date(), artikel, bestand, mitarbeiter));
	}
	
	public void fuegeMassengutartikelEin(Mitarbeiter mitarbeiter, int artikelnummer, String bezeichnung, double preis, int packungsgroesse, int bestand) throws ArtikelExistiertBereitsException, ArtikelBestandIstKeineVielfacheDerPackungsgroesseException {
		Massengutartikel artikel = new Massengutartikel(artikelnummer, bezeichnung, preis, packungsgroesse, bestand);
		meineArtikel.einfuegen(artikel);
		
		meineEreignisse.hinzufuegen(new Ereignis(new Date(), artikel, bestand, mitarbeiter));
	}
	
	public Artikel gibArtikel(int artikelnummer) throws ArtikelExistiertNichtException {
		return meineArtikel.getArtikel(artikelnummer);
	}
	
	public void artikelBestandVeraendern(Mitarbeiter mitarbeiter, int artikelnummer, int anzahl) throws ArtikelExistiertNichtException, ArtikelBestandIstKeineVielfacheDerPackungsgroesseException {
		meineArtikel.bestandVeraendern(artikelnummer, anzahl);
		meineEreignisse.hinzufuegen(new Ereignis(new Date(), gibArtikel(artikelnummer), anzahl, mitarbeiter));
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
	
	public void artikelBearbeiten(int artikelnummer, double preis, String bezeichnung) throws ArtikelExistiertNichtException{
		Artikel a = gibArtikel(artikelnummer);
		a.setPreis(preis);
		a.setBezeichnung(bezeichnung);
	}
	
	public void entferneArtikel(Mitarbeiter m, int artikelnummer) throws ArtikelExistiertNichtException, IOException {
		meineEreignisse.entferneArtikelAusLog(artikelnummer, logDateiname);
		
		meineArtikel.entfernen(artikelnummer);
	}
	
	/**
	 * Methode zum Speichern des Artikelbestands in einer Datei.
	 * 
	 * @throws IOException
	 */
	public void schreibeArtikel() throws IOException {
		meineArtikel.schreibeDaten(artikelDateiname);
	}
	
	// Mitarbeiter Methoden
	
	/**
	 * Diese Methode erm�glicht es einen Mitarbeiter nach seiner ID
	 * zu suchen.
	 * @param id ID der Mitarbeiter Instanz, die man suchen m�chte
	 * @return Die Mitarbeiter Instanz mit der gegebenen ID.
	 */
	public Mitarbeiter sucheMitarbeiter(int id) throws MitarbeiterExistiertNichtException{
		return meineMitarbeiter.sucheMitarbeiter(id);
	}

	/**
	 * Diese Methode erm�glicht es um die Mitarbeiterliste einzusehen.
	 * @return Vector mit allen aktuellen Mitarbeiter Instanzen.
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
	 * Diese Methode bildet eine neue Mitarbeiter Instanz und f�gt sie
	 * zur Mitarbeiterverwaltung hinzu.
	 * @param id Id des neuen Mitarbeiters
	 * @param name Name des neuen Mitarbeiters
	 * @throws MitarbeiterExistiertBereitsException
	 * @throws UsernameExistiertBereitsException 
	 */
	public void fuegeMitarbeiterHinzu(String username, String passwort, String name, MitarbeiterFunktion funktion, double gehalt) throws MitarbeiterExistiertBereitsException, UsernameExistiertBereitsException{
		this.existiertUsernameSchon(username, " - in fuegeMitarbeiterHinzu() !");
		
		Mitarbeiter m = new Mitarbeiter(mitarbeiterNextId, username, passwort, name, funktion, gehalt);
		meineMitarbeiter.einfuegen(m);
		mitarbeiterNextId++;
	}

	/**
	 * Diese Methode schreibt alle Mitarbeiterdaten in die Datenquelle.
	 * @throws IOException
	 */
	public void schreibeMitarbeiter() throws IOException{
		meineMitarbeiter.schreibeDaten(mitarbeiterDateiname);
	}
	
	/**
	 * Diese Methode ist zum bearbeiten von Mitarbeitern.
	 * @param id
	 * @param passwort
	 * @param name
	 * @param funktion
	 * @param gehalt
	 * @param blockiert
	 * @throws MitarbeiterExistiertNichtException
	 */
	public void mitarbeiterBearbeiten(int id, String passwort, String name, MitarbeiterFunktion funktion, double gehalt, boolean blockiert) throws MitarbeiterExistiertNichtException{
		Mitarbeiter m = sucheMitarbeiter(id);
		m.setPasswort(passwort);
		m.setFunktion(funktion);
		m.setGehalt(gehalt);
		m.setBlockiert(blockiert);
	}
	
	/**
	 * Diese Methode iteriert zuerst durch die Mitarbeiterliste und dann durch die Kundenliste und 
	 * vergleicht die Usernamen.
	 * Beim ersten Treffer wird eine UsernameExistiertBereitsException geworfen.
	 * @param username Username f�r die neue Person.
	 * @param zusatzMsg Zus�tliche Informationen.
	 * @throws UsernameExistiertBereitsException
	 */
	private void existiertUsernameSchon(String username, String zusatzMsg) throws UsernameExistiertBereitsException{
		
		Iterator<Mitarbeiter> itM = meineMitarbeiter.getMitarbeiterListe().iterator();
		while(itM.hasNext()){
			if(itM.next().getUsername().equals(username)){
				throw new UsernameExistiertBereitsException(username, zusatzMsg);
			}
		}
		
		Iterator<Kunde> itK = meineKunden.getKundenListe().iterator();
		while(itK.hasNext()){
			if(itK.next().getUsername().equals(username)){
				throw new UsernameExistiertBereitsException(username, zusatzMsg);
			}
		}
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
	* Diese Methode erm�glicht es eine Kunden Instanz zu loeschen.
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
	 * @throws UsernameExistiertBereitsException 
	 */
	public void fuegeKundenHinzu(String username, String passwort, String name, String strasse, int plz, String wohnort) throws KundeExistiertBereitsException, UsernameExistiertBereitsException{
		this.existiertUsernameSchon(username, " - in fuegeKundenHinzu() !");
		
		Kunde k = new Kunde(kundenNextId, username, passwort, name, strasse, plz, wohnort);
		meineKunden.einfuegen(k);
		kundenNextId++;
	}
	
	/**
	 * Diese Methode ist zum bearbeiten einer Kunden Instanz zustaendig.
	 * @param id
	 * @param passwort
	 * @param name
	 * @param strasse
	 * @param plz
	 * @param wohnort
	 * @param blockiert
	 * @throws KundeExistiertNichtException
	 */
	public void kundenBearbeiten(int id, String passwort, String name, String strasse, int plz, String wohnort, boolean blockiert) throws KundeExistiertNichtException{
		Kunde k = sucheKunde(id);
		k.setPasswort(passwort);
		k.setName(name);
		k.setStrasse(strasse);
		k.setPlz(plz);
		k.setWohnort(wohnort);
		k.setBlockiert(blockiert);
	}

	/**
 	* Diese Methode ermoeglicht es den "schreibe" befehl der KundenVerwaltung zu triggern.
 	* @throws IOException
 	*/
	public void schreibeKunden() throws IOException{
		meineKunden.schreibeDaten(kundenDateiname);
	}
	
	public void inDenWarenkorbLegen(Kunde kunde, Artikel artikel, int stueckzahl) throws ArtikelBestandIstZuKleinException, ArtikelExistiertNichtException, ArtikelBestandIstKeineVielfacheDerPackungsgroesseException {
		meineKunden.inDenWarenkorbLegen(kunde, new WarenkorbArtikel(artikel, stueckzahl));
	}
	
	public void ausDemWarenkorbHerausnehmen(Kunde kunde, Artikel artikel) throws ArtikelExistiertNichtException, ArtikelBestandIstKeineVielfacheDerPackungsgroesseException {
		meineKunden.ausDemWarenkorbHerausnehmen(kunde, kunde.getWarenkorbVerwaltung().getWarenkorbArtikel(artikel));
	}
	
	public Rechnung kaufen(Kunde kunde) throws IOException, WarenkorbIstLeerException {
		Rechnung rechnung = meineKunden.kaufen(kunde);
		schreibeArtikel();
		Iterator<WarenkorbArtikel> iter = rechnung.getWarenkorb().iterator();
		while(iter.hasNext()){
			WarenkorbArtikel wa = iter.next();
			try {
				meineEreignisse.hinzufuegen(new Ereignis(new Date(), wa.getArtikel(), -wa.getStueckzahl(), kunde));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rechnung;
	}
	
	public void leeren(Kunde k) throws ArtikelBestandIstKeineVielfacheDerPackungsgroesseException {
		meineKunden.leeren(k);
	}
	
	public Kunde loginVergessen(String name, String strasse, int zip, String wohnort){
		Kunde result = null;
		Iterator<Kunde> itK = meineKunden.getKundenListe().iterator();
		while(itK.hasNext()){
			Kunde k = itK.next();
			if(k.getName().equals(name) && k.getStrasse().equals(strasse) && k.getPlz() == zip && k.getWohnort().equals(wohnort)){
				result = k;
				break;
			}
		}
		
		return result;
	}
	
	/**
	 * Methode zur �berpr�fung des Logins auf basis des Usernamens und des Passwortes
	 * @param username
	 * @param password
	 * @return Person p
	 */
	public Person pruefeLogin(String username, String password) {
		Iterator<Mitarbeiter> itM = meineMitarbeiter.getMitarbeiterListe().iterator();
		Person p = null;
		while (itM.hasNext()) {
			p = itM.next();
			if (((String) p.getUsername()).equals(username) && ((String) p.getPasswort()).equals(password)) {
				return p;
			}
		}
		Iterator<Kunde> itK = meineKunden.getKundenListe().iterator();
		while (itK.hasNext()) {
			p = itK.next();
			if (((String) p.getUsername()).equals(username) && ((String) p.getPasswort()).equals(password)) {
				return p;
			}
		}
		System.out.println("Zugriff verweigert!!!");
		System.out.println("Bitte �berpr�fen Sie ihren Usernamen und Ihr Passwort.");
		return null;
	}
	
	// Ereignis Methoden
	
	public void schreibeEreignisse() throws IOException{
		meineEreignisse.schreibeDaten(logDateiname);
	}
	
	public String gibBestandsHistorie(Artikel artikel) throws IOException{
		return meineEreignisse.gibBestandsHistorie(artikel, logDateiname);
	}
	
	public int[] gibBestandsHistorieDaten(Artikel artikel) throws IOException{
		return meineEreignisse.gibBestandsHistorieDaten(artikel, logDateiname);
	}
	
	public String gibLogDatei() throws IOException{
		return meineEreignisse.liesLogDatei(logDateiname);
	}
	
}
