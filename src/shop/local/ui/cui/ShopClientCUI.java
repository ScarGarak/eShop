package shop.local.ui.cui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import shop.local.domain.ShopVerwaltung;
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
import shop.local.valueobjects.Kunde;
import shop.local.valueobjects.Mitarbeiter;
import shop.local.valueobjects.MitarbeiterFunktion;
import shop.local.valueobjects.Person;
import shop.local.valueobjects.PersonTyp;
import shop.local.valueobjects.WarenkorbArtikel;

/**
 * Klasse zur Ausgabe des eShops in der Konsole.
 * 
 * @author Christof Ferreira Torres, Angelo Migliosi & Oliver Thummerer
 * @version 1.0.0
 */
public class ShopClientCUI {

	private ShopVerwaltung shop;
	private BufferedReader in;
	private Person p;
	
	public ShopClientCUI() throws IOException {
		shop = new ShopVerwaltung();
		in = new BufferedReader(new InputStreamReader(System.in));
	}
	
	private void anmeldungMenue() {
		System.out.print("Befehle: \n  Anmelden >                          'a'");
		System.out.print("         \n  Registrieren >                      'r'");
		System.out.print("       \n  Username oder Passwort vergessen? > 'v'");
		System.out.println("         \n  Beenden >                           'q'");		
		System.out.print("> ");
		System.out.flush();				
	}
	
	private void mitarbeiterMenue() {
		System.out.print("Befehle: \n  Artikel einfuegen:           'e'");
		System.out.print("         \n  Artikel ausgeben nach Nr:    'a'");
		System.out.print("         \n  Artikel ausgeben nach Bez.:  'b'");
		System.out.print("         \n  Artikel Bestand erh�hen:     'c'");
		System.out.print("         \n  Artikel suche nach Nr:       'f'");
		System.out.print("         \n  Artikel suche nach Bez.:     'g'");
		System.out.print("         \n  Artikel l�schen nach Nr:     'k'");
		System.out.print("         \n                                   ");
		System.out.print("         \n  Bestandshistorie ausgeben    'bh'");
		System.out.print("         \n                                   ");
		System.out.print("         \n  Mitarbeiter einfuegen:       'me'");
		System.out.print("         \n  Mitarbeiter ausgeben:        'ma'");
		System.out.print("         \n  Mitarbeiter suche nach ID:   'mf'");
		System.out.print("         \n  Mitarbeiter l�schen nach ID: 'ml'");
		System.out.print("         \n                                   ");
		System.out.print("         \n  Kunden ausgeben:             'ka'");
		System.out.print("         \n  Kunden suche nach ID:        'kf'");
		System.out.print("         \n  Kunden l�schen nach ID:      'kl'");
		System.out.print("         \n                                   ");	
		System.out.println("         \n  Beenden:                     'q'");
		System.out.print("> ");
		System.out.flush();		
	}
	
	private void kundenMenue() {
		System.out.print("Befehle: \n  Account loeschen:            'al'");
		System.out.print("         \n                                   ");
		System.out.print("         \n  Artikel ausgeben nach Nr:    'a'");
		System.out.print("         \n  Artikel ausgeben nach Bez.:  'b'");
		System.out.print("         \n                                   ");
		System.out.print("         \n  Warenkorb Artikel hinzuf�gen:'wh'");
		System.out.print("         \n  Warenkorb Artikel entfernen: 'we'");
		System.out.print("         \n  Warenkorb anzeigen:          'wa'");
		System.out.print("         \n  Warenkorb leeren:            'wl'");
		System.out.print("         \n  Warenkorb kaufen:            'wk'");
		System.out.print("         \n                                   ");			
		System.out.println("         \n  Beenden:                     'q'");
		System.out.print("> ");
		System.out.flush();		
	}

	private String liesEingabe() throws IOException {
		return in.readLine();
	}

	private void logIn() throws IOException {
		System.out.println("Login > ");
		System.out.print("Username > ");
		String username = liesEingabe();
		System.out.print("Passwort > ");
		String password = liesEingabe();
		p = shop.pruefeLogin(username, password);
//		system prints aus shopverwaltung einf�gen
	}
	
	private void verarbeiteAnmeldungEingabe(String line) throws IOException {
		if (line.equals("r")) {
			System.out.print("Registrierung >");
			System.out.print("Username >");
			String username = liesEingabe();
			System.out.print("Passwort >");
			String passwort = liesEingabe();
			System.out.print("Name >");
			String name = liesEingabe();
			System.out.println("Strasse >");
			String strasse = liesEingabe();
			System.out.println("Postleitzahl >");
			String strPlz = liesEingabe();
			int plz = Integer.parseInt(strPlz);
			System.out.println("Wohnort >");
			String wohnort = liesEingabe();
			try {
				shop.fuegeKundenHinzu(username, passwort, name, strasse, plz, wohnort);
				System.out.println("Kunde wurde hinzugef�gt!");
			} catch (KundeExistiertBereitsException e) {
				System.err.println(e.getMessage());
			} catch (UsernameExistiertBereitsException e) {
				System.err.println(e.getMessage());
			}
		} else if (line.equals("v")) {
			// methodenaufruf zum �berschreiben des Usernamens und des passwortes
		}
	}	
	
	private void verarbeiteMitarbeiterEingabe(String line) throws IOException {
		if (line.equals("e")) { 
			try {
//				System.out.print("Mitarbeiter ID > ");
				Mitarbeiter m = shop.sucheMitarbeiter(p.getId());
				System.out.print("Artikelnummer > ");
				int nummer = Integer.parseInt(liesEingabe());
				System.out.print("Bezeichnung > ");
				String bezeichnung = liesEingabe();
				System.out.print("Preis > ");
				double preis = Double.parseDouble(liesEingabe());
				System.out.print("Massengutartikel oder Einzelartikel? (m/e) > ");
				String groesse = "";
				if (liesEingabe().toLowerCase().equals("m")) {
					System.out.print("Packungsgr��e > ");
					groesse = liesEingabe();
				} 
				System.out.print("Bestand > ");
				int bestand = Integer.parseInt(liesEingabe());
				boolean ok = false;
				if (groesse.isEmpty()) 
					shop.fuegeArtikelEin(m, nummer, bezeichnung, preis, bestand);	
				else
					shop.fuegeMassengutartikelEin(m, nummer, bezeichnung, preis, Integer.parseInt(groesse), bestand);
				ok = true;
				if (ok)
					System.out.println("Einf�gen ok");
				else
					System.out.println("Fehler beim Einf�gen");
			} catch (MitarbeiterExistiertNichtException e) {
				System.err.println("Mitarbeiter existiert nicht!");
			} catch (ArtikelBestandIstKeineVielfacheDerPackungsgroesseException e) {
				System.err.println("Bestand ist keine Vielfache der Packungsgroesse!");
			} catch (ArtikelExistiertBereitsException e) {
				System.err.println("Artikel existiert bereits!");
			} catch(NumberFormatException e) { 
				System.err.println("Sie haben eine ung�ltige Zahl eingegeben!");
			}
		}
		else if (line.equals("a")) {
			Collection<Artikel> liste = shop.gibAlleArtikelSortiertNachArtikelnummer();
			gibArtikellisteAus(liste);
		}
		else if (line.equals("b")) {
			Collection<Artikel> liste = shop.gibAlleArtikelSortiertNachBezeichnung();
			gibArtikellisteAus(liste);
		}
		else if (line.equals("c")) {
			try {
//				System.out.print("Mitarbeiter ID > ");
				Mitarbeiter m = shop.sucheMitarbeiter(p.getId());
				System.out.print("Artikelnummer > ");
				int nummer = Integer.parseInt(liesEingabe());
				System.out.print("Artikelanzahl > ");
				int anzahl = Integer.parseInt(liesEingabe());
				boolean ok = false;
				shop.artikelBestandVeraendern(m, nummer, anzahl);
				ok = true;
				if (ok)
					System.out.println("Bestand erh�hen ok");
				else
					System.out.println("Fehler beim Bestand erh�hen");
			} catch (MitarbeiterExistiertNichtException e) {
				System.err.println("Mitarbeiter existiert nicht!");
			} catch (ArtikelBestandIstKeineVielfacheDerPackungsgroesseException e) {
				System.err.println("Bestand ist keine Vielfache der Packungsgroesse!");
			} catch (ArtikelExistiertNichtException e) {
				System.err.println("Artikel existiert nicht!");
			} catch(NumberFormatException e) { 
				System.err.println("Sie haben eine ung�ltige Zahl eingegeben!");
			}
		}
		else if (line.equals("f")) {
			System.out.print("Artikelnummer > ");
			int nummer = Integer.parseInt(liesEingabe());
			List<Artikel> liste = shop.sucheArtikel(nummer);
			gibArtikellisteAus(liste);
		}
		else if (line.equals("g")) {
			System.out.print("Artikelbezeichnung > ");
			String bezeichnung = liesEingabe();
			List<Artikel> liste = shop.sucheArtikel(bezeichnung);
			gibArtikellisteAus(liste);
		}
		else if (line.equals("k")) {
			try {
//				System.out.print("Mitarbeiter ID > ");
				Mitarbeiter m = shop.sucheMitarbeiter(p.getId());
				System.out.print("Artikelnummer > ");
				int nummer = Integer.parseInt(liesEingabe());
				boolean ok = false;

				shop.entferneArtikel(m, nummer);
				ok = true;
				if (ok)
					System.out.println("L�schen ok");
				else
					System.out.println("Fehler beim L�schen");
			} catch (ArtikelExistiertNichtException e) {
				System.err.println("Artikel existiert nicht!");
			} catch (NumberFormatException e) {
				System.err.println("Die Mitarbeiter ID erwartet eine Zahl!");
			} catch (MitarbeiterExistiertNichtException e) {
				System.err.println(e.getMessage());
			}
		}
		else if(line.equals("bh")){
			
			try {
				System.out.print("Artikelnummer > ");
				int nummer = Integer.parseInt(liesEingabe());
				Artikel a = shop.gibArtikel(nummer);
				System.out.println(shop.gibBestandsHistorie(a));
			} catch (ArtikelExistiertNichtException e) {
				System.err.println(e.getMessage());
			} catch (NumberFormatException e){
				System.err.println("Sie m�ssen f�r die Artikelnummer eine Zahl eingeben.");
			}
		}
		else if (line.equals("me")) {
			System.out.print("Username >");
			String username = liesEingabe();
			System.out.print("Passwort >");
			String passwort = liesEingabe();
			System.out.print("Name >");
			String name = liesEingabe();
			System.out.print("Funktion: \tMitarbeiter / Admin >");
			String funktionStr = liesEingabe();
			
			try{
				MitarbeiterFunktion funktion = null;
				if(funktionStr.equals("Mitarbeiter")){
					funktion = MitarbeiterFunktion.Mitarbeiter;
				}else if(funktionStr.equals("Admin")){
					funktion = MitarbeiterFunktion.Admin;
				}
				
				if(funktion != null){
					shop.fuegeMitarbeiterHinzu(username, passwort, name, funktion, 0);
				}else{
					System.err.println("Die Funktion hat keinen g�ltigen Wert!");
				}
			} catch (MitarbeiterExistiertBereitsException e){
				System.err.println(e.getMessage());
			} catch (UsernameExistiertBereitsException e){
				System.err.println(e.getMessage());
			} catch (NumberFormatException e){
				System.err.println("Die id ist keine Zahl! Bitte versuchen Sie es erneut.");
			}
		}
		else if (line.equals("ma")) {
			Collection<Mitarbeiter> liste = shop.gibAlleMitarbeiter();
			gibMitarbeiterlisteAus(liste);
		}
		else if (line.equals("mf")) {
			System.out.print("Mitarbeiter ID >");
			int id = Integer.parseInt(liesEingabe());
			try{
				Mitarbeiter m = shop.sucheMitarbeiter(id);
				System.out.println(m.toString());
			}catch (MitarbeiterExistiertNichtException e1){
				System.out.println(e1.getMessage());
				e1.printStackTrace();
			}
		}
		else if (line.equals("ml")) {
			System.out.print("Mitarbeiter ID >");
			int id = Integer.parseInt(liesEingabe());
			try{
				shop.mitarbeiterLoeschen(shop.sucheMitarbeiter(id));
			}catch (MitarbeiterExistiertNichtException e1){
				System.out.println(e1.getMessage());
				e1.printStackTrace();
			}
		}
		else if (line.equals("ka")) {
			Collection<Kunde> liste = shop.gibAlleKunden();
			gibKundenlisteAus(liste);
		}
		else if (line.equals("kf")) {
			System.out.print("Kunden ID >");
			int id = Integer.parseInt(liesEingabe());
			
			try {
				Kunde k = shop.sucheKunde(id);
				System.out.println(k.toString());
			} catch (KundeExistiertNichtException e) {
				System.err.println("Der Kunde existiert nicht!");
			}
		}
		else if (line.equals("kl")) {
			System.out.print("Kunden ID >");
			try {
				shop.kundenLoeschen(shop.sucheKunde(Integer.parseInt(liesEingabe())));
			} catch (KundeExistiertNichtException e) {
				System.err.println("Der Kunde existiert nicht!");
			}
		}
	}
	
	private void verarbeiteKundenEingabe(String line) throws IOException {
		if (line.equals("al")) {
			String eingabe;
//			do {
			System.out.print("Sind Sie sicher? (y/n) >");
			eingabe = liesEingabe();
			if (eingabe.equals("y")) {
			try {
				shop.kundenLoeschen(shop.sucheKunde(p.getId()));
//				shop.kundenLoeschen(p); funktioniert?
			} catch (KundeExistiertNichtException e) {
				// ist die Exception �berhaupt noch notwendig?
				System.err.println("Der Kunde existiert nicht!");
			}
			System.out.println("Ihr Account wurde gel�scht und der Client wird nun geschlossen!");
//			shop.schreibeArtikel();
//			shop.schreibeMitarbeiter();
//			shop.schreibeKunden();
//			shop.schreibeEreignisse();
//			System.exit(0);
			} else if (eingabe.equals("n"))
				System.out.println("Ihr Account wurde nicht gel�scht.");
//			} while (!eingabe.equals("n"));
		} else if (line.equals("a")) {
			Collection<Artikel> liste = shop.gibAlleArtikelSortiertNachArtikelnummer();
			gibArtikellisteAus(liste);
		}
		else if (line.equals("b")) {
			Collection<Artikel> liste = shop.gibAlleArtikelSortiertNachBezeichnung();
			gibArtikellisteAus(liste);
		}

		// Artikel zum Warenkorb hinzufuegen
		
		else if (line.equals("wh")) {
			try {
				Kunde k = shop.sucheKunde(p.getId());
				System.out.print("Artikelnummer > ");
				Artikel a = shop.gibArtikel(Integer.parseInt(liesEingabe()));
				System.out.print("St�ckzahl eingeben > ");
				int stZa = Integer.parseInt(liesEingabe());
				boolean ok = false;
				shop.inDenWarenkorbLegen(k, a, stZa);
				ok = true;
				if (ok)
					System.out.println("Hinzuf�gen ok");
				else
					System.out.println("Fehler beim Hinzuf�gen");
			} catch (KundeExistiertNichtException e){
				System.err.println("Kunde existiert nicht!");
			} catch (ArtikelExistiertNichtException e1) {
				System.err.println("Artikel existiert nicht!");
			} catch (ArtikelBestandIstKeineVielfacheDerPackungsgroesseException e) {
				System.err.println("Die St�ckzahl ist keine Vielfache der Packungsgroesse!");
			} catch (ArtikelBestandIstZuKleinException e) {
				System.err.println("Der Bestand ist zu klein oder leer!");
			} catch(NumberFormatException e) { 
				System.err.println("Sie haben eine ung�ltige Zahl eingegeben!");
			} 
		}
		
		// Artikel aus dem Warenkorb entfernen
		
		else if (line.equals("we")) {
			try {
				Kunde k = shop.sucheKunde(p.getId());
				System.out.print("Artikelnummer > ");
				Artikel a = shop.gibArtikel(Integer.parseInt(liesEingabe()));
				boolean ok = false;
				shop.ausDemWarenkorbHerausnehmen(k, a);
				ok = true;
				if (ok)
					System.out.println("Entfernen ok");
				else
					System.out.println("Fehler beim Entfernen");
			} catch (KundeExistiertNichtException e){
				System.err.println("Kunde existiert nicht!");
			} catch (ArtikelExistiertNichtException e) {
				System.err.println("Artikel existiert nicht!");
			} catch (ArtikelBestandIstKeineVielfacheDerPackungsgroesseException e) {
				System.err.println("Der Bestand ist keine Vielfache der Packungsgroesse!");
			} catch(NumberFormatException e) { 
				System.err.println("Sie haben eine ung�ltige Zahl eingegeben!");
			}
		}
		
		// Inhalt des Warenkorbes anzeigen lassen
		
		else if (line.equals("wa")) {
			try {
				Kunde k = shop.sucheKunde(p.getId());
				Collection<WarenkorbArtikel> liste = k.getWarenkorbVerwaltung().getWarenkorb();
				gibWarenkorblisteAus(liste);
			} catch (KundeExistiertNichtException e) {
				System.err.println("Kunde existiert nicht!");
			} catch(NumberFormatException e) { 
				System.err.println("Sie haben eine ung�ltige Zahl eingegeben!");
			}
		}
		
		// Warenkorb leeren
		
		else if (line.equals("wl")) {
			try {
				Kunde k = shop.sucheKunde(p.getId());
				boolean ok = false;
				shop.leeren(k);
				ok = true;
				if (ok)
					System.out.println("Leeren ok");
				else
					System.out.println("Fehler beim Leeren");
			} catch (KundeExistiertNichtException e) {
				System.err.println("Kunde existiert nicht!");
			} catch (ArtikelBestandIstKeineVielfacheDerPackungsgroesseException e) {
				System.err.println("Der Bestand ist keine Vielfache der Packungsgroesse!");
			} catch(NumberFormatException e) { 
				System.err.println("Sie haben eine ung�ltige Zahl eingegeben!");
			}
		}
		else if (line.equals("wk")) {
			try {
				Kunde k = shop.sucheKunde(p.getId());
				System.out.println(shop.kaufen(k).toString());
			} catch (KundeExistiertNichtException e) {
				System.err.println("Kunde existiert nicht!");
			} catch (WarenkorbIstLeerException e) {
				System.err.println("Der Warenkorb ist leer!");
			} catch(NumberFormatException e) { 
				System.err.println("Sie haben eine ung�ltige Zahl eingegeben!");
			}
		}
	}
	
	private void gibArtikellisteAus(Collection<Artikel> artikel) {
		if (artikel.isEmpty()) {
			System.out.println("Liste ist leer.");
		} else {
			Iterator<Artikel> it = artikel.iterator();
			while (it.hasNext()) {
				Artikel a = it.next();
				System.out.println(a.toString());
			}
		}
	}
	
	private void gibMitarbeiterlisteAus(Collection<Mitarbeiter> mitarbeiter) {
		if (mitarbeiter.isEmpty()) {
			System.out.println("Liste ist leer.");
		} else {
			Iterator<Mitarbeiter> it = mitarbeiter.iterator();
			while (it.hasNext()) {
				Mitarbeiter m = it.next();
				System.out.println(m.toString());
			}
		}
	}
	
	private void gibKundenlisteAus(Collection<Kunde> kunden) {
		if (kunden.isEmpty()) {
			System.out.println("Liste ist leer.");
		} else {
			Iterator<Kunde> it = kunden.iterator();
			while (it.hasNext()) {
				Kunde k = it.next();
				System.out.println(k.toString());
			}
		}
	}
	
	private void gibWarenkorblisteAus(Collection<WarenkorbArtikel> warenkorb) {
		if (warenkorb.isEmpty()) {
			System.out.println("Warenkorb ist leer.");
		} else {
			Iterator<WarenkorbArtikel> it = warenkorb.iterator();
			while (it.hasNext()) {
				WarenkorbArtikel wa = it.next();
				System.out.println(wa.toString());
			}
		}
	}

	public void run() throws IOException {
		String input = "";
		do {
			anmeldungMenue();
			input = liesEingabe();
			verarbeiteAnmeldungEingabe(input);
			if (input.equals("q"))
				System.exit(0);
		} while (!input.equals("a"));
		do {
			logIn();
		} while (p == null);
		do {
			if (p.getPersonTyp().equals(PersonTyp.Mitarbeiter)) {
				mitarbeiterMenue();
		} else
			kundenMenue();
			try {
				input = liesEingabe();
				if (p.getPersonTyp().equals(PersonTyp.Mitarbeiter)) {
					verarbeiteMitarbeiterEingabe(input);
				} else {
					verarbeiteKundenEingabe(input);
				 }
			} catch (IOException e) {
				e.printStackTrace();
			}
		} while (!input.equals("q"));
			shop.schreibeArtikel();
			shop.schreibeMitarbeiter();
			shop.schreibeKunden();
			shop.schreibeEreignisse();
	}
	
	public static void main(String[] args) {
		ShopClientCUI cui;
		try {
			cui = new ShopClientCUI();
			cui.run();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
}
