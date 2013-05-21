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
import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.Kunde;
import shop.local.valueobjects.Mitarbeiter;
import shop.local.valueobjects.WarenkorbArtikel;

public class ShopClientCUI {

	private ShopVerwaltung shop;
	private BufferedReader in;
	
	public ShopClientCUI() throws IOException, ArtikelExistiertBereitsException, ClassNotFoundException {
		shop = new ShopVerwaltung();
		in = new BufferedReader(new InputStreamReader(System.in));
	}

	private void gibMenueAus() {
		System.out.print("Befehle: \n  Artikel einfuegen:           'e'");
		System.out.print("         \n  Artikel ausgeben nach Nr:    'a'");
		System.out.print("         \n  Artikel ausgeben nach Bez.:  'b'");
		System.out.print("         \n  Artikel Bestand erhöhen:     'c'");
		System.out.print("         \n  Artikel suche nach Nr:       'f'");
		System.out.print("         \n  Artikel suche nach Bez.:     'g'");
		System.out.print("         \n  Artikel löschen nach Nr:     'k'");
		System.out.print("         \n  Artikel löschen nach Bez.:   'l'");
		System.out.print("         \n                                   ");
		System.out.print("         \n  Mitarbeiter einfuegen:       'me'");
		System.out.print("         \n  Mitarbeiter ausgeben:        'ma'");
		System.out.print("         \n  Mitarbeiter suche nach ID:   'mf'");
		System.out.print("         \n  Mitarbeiter löschen nach ID: 'ml'");
		System.out.print("         \n                                   ");
		System.out.print("         \n  Kunden einfuegen:            'ke'");
		System.out.print("         \n  Kunden ausgeben:             'ka'");
		System.out.print("         \n  Kunden suche nach ID:        'kf'");
		System.out.print("         \n  Kunden löschen nach ID:      'kl'");
		System.out.print("         \n                                   ");
		System.out.print("         \n  Warenkorb Artikel hinzufügen:'wh'");
		System.out.print("         \n  Warenkorb Artikel entfernen: 'we'");
		System.out.print("         \n  Warenkorb anzeigen:          'wa'");
		System.out.print("         \n  Warenkorb leeren:            'wl'");
		System.out.print("         \n  Warenkorb kaufen:            'wk'");
		System.out.println("         \n  Beenden:                     'q'");
		System.out.print("> ");
		System.out.flush();
	}

	private String liesEingabe() throws IOException {
		return in.readLine();
	}

	private void verarbeiteEingabe(String line) throws IOException {
		if (line.equals("e")) { 
			System.out.print("Mitarbeiter ID > ");
			String id = liesEingabe();
			System.out.print("Artikelnummer > ");
			String nummer = liesEingabe();
			System.out.print("Bezeichnung > ");
			String bezeichnung = liesEingabe();
			System.out.print("Preis  > ");
			String preis = liesEingabe();
			System.out.print("Massengutartikel oder Einzelartikel? (m/e) > ");
			String groesse = "";
			if (liesEingabe().toLowerCase().equals("m")) {
				System.out.print("Packungsgröße > ");
				groesse = liesEingabe();
			} 
			System.out.print("Bestand > ");
			String bestand = liesEingabe();
			boolean ok = false;
			if (groesse.isEmpty()) 
				try {
					shop.fuegeArtikelEin(shop.sucheMitarbeiter(Integer.parseInt(id)), Integer.parseInt(nummer), bezeichnung, Double.parseDouble(preis), Integer.parseInt(bestand));
					ok = true;
				} catch (ArtikelExistiertBereitsException e) {
					System.err.println("Artikel existiert bereits!");
				} catch (MitarbeiterExistiertNichtException e) {
					System.err.println("Mitarbeiter existiert bereits!");
				} catch(NumberFormatException e) { 
					System.err.println("Sie haben eine ungültige Zahl eingegeben!");
				}
			else 
				try {
					shop.fuegeMassengutartikelEin(shop.sucheMitarbeiter(Integer.parseInt(id)), Integer.parseInt(nummer), bezeichnung, Double.parseDouble(preis), Integer.parseInt(groesse), Integer.parseInt(bestand));
					ok = true;
				} catch (ArtikelExistiertBereitsException e) {
					System.err.println("Massengutartikel existiert bereits!");
				} catch (ArtikelBestandIstKeineVielfacheDerPackungsgroesseException e) {
					System.err.println("Bestand ist keine Vielfache der Packungsgroesse!");
				} catch (MitarbeiterExistiertNichtException e) {
					System.err.println("Mitarbeiter existiert bereits!");
				} catch(NumberFormatException e) { 
					System.err.println("Sie haben eine ungültige Zahl eingegeben!");
				}
			if (ok)
				System.out.println("Einfügen ok");
			else
				System.out.println("Fehler beim Einfügen");
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
			System.out.print("Mitarbeiter ID > ");
			String id = liesEingabe();
			int aID = Integer.parseInt(id);
			System.out.print("Artikelnummer  > ");
			String nummer = liesEingabe();
			int aNr = Integer.parseInt(nummer);
			System.out.print("Artikelanzahl  > ");
			String anzahl = liesEingabe();
			int aAn = Integer.parseInt(anzahl);
			boolean ok = false;
			try {
				shop.artikelBestandErhoehen(shop.sucheMitarbeiter(aID), aNr, aAn);
				ok = true;
			} catch (ArtikelExistiertNichtException e) {
				System.err.println("Artikel existiert nicht!");
			} catch (MitarbeiterExistiertNichtException e) {
				System.err.println("Mitarbeiter existiert nicht!");
			}
			if (ok)
				System.out.println("Bestand erhöhen ok");
			else
				System.out.println("Fehler beim Bestand erhöhen");
		}
		else if (line.equals("f")) {
			System.out.print("Artikelnummer  > ");
			String nummer = liesEingabe();
			int aNr = Integer.parseInt(nummer);
			List<Artikel> liste = shop.sucheArtikel(aNr);
			gibArtikellisteAus(liste);
		}
		else if (line.equals("g")) {
			System.out.print("Artikelbezeichnung  > ");
			String bezeichnung = liesEingabe();
			List<Artikel> liste = shop.sucheArtikel(bezeichnung);
			gibArtikellisteAus(liste);
		}
		else if (line.equals("k")) {
			System.out.print("Artikelnummer > ");
			String nummer = liesEingabe();
			int aNr = Integer.parseInt(nummer);
			boolean ok = false;
			try {
				shop.EntferneArtikel(aNr);
				ok = true;
			} catch (ArtikelExistiertNichtException e) {
				System.err.println("Artikel existiert nicht!");
			}
			if (ok)
				System.out.println("Löschen ok");
			else
				System.out.println("Fehler beim Löschen");
		}
		else if (line.equals("l")) {
			System.out.print("Artikelbezeichnung  > ");
			String bezeichnung = liesEingabe();
			boolean ok = false;
			try {
				shop.EntferneArtikel(bezeichnung);
				ok = true;
			} catch (ArtikelExistiertNichtException e) {
				System.err.println("Artikel existiert nicht!");
			}
			if (ok)
				System.out.println("Löschen ok");
			else
				System.out.println("Fehler beim Löschen");
		}
		else if (line.equals("me")) {
			System.out.print("Mitarbeiter ID >");
			String strId = liesEingabe();
			System.out.print("Username >");
			String username = liesEingabe();
			System.out.print("Passwort >");
			String passwort = liesEingabe();
			System.out.print("Name >");
			String name = liesEingabe();
			try{
				int id = Integer.parseInt(strId);
				
				shop.fuegeMitarbeiterHinzu(id, username, passwort, name);
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
		}else  if(line.equals("ke")){
			System.out.print("Kunden ID >");
			String strId = liesEingabe();
			int id = Integer.parseInt(strId);
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
				shop.fuegeKundenHinzu(id, username, passwort, name, strasse, plz, wohnort);
			} catch (KundeExistiertBereitsException e) {
				System.err.println(e.getMessage());
			} catch (UsernameExistiertBereitsException e) {
				System.err.println(e.getMessage());
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

		// Artikel zum Warenkorb hinzugen
		
		else if (line.equals("wh")) {
			System.out.print("Kunden ID >");
			int id = Integer.parseInt(liesEingabe());
			System.out.println("Artikel Nr. >");
			int artNr = Integer.parseInt(liesEingabe());
			try {
				Artikel a = shop.gibArtikel(artNr);
				System.out.println("Stückzahl eingeben >");
				int stZa = Integer.parseInt(liesEingabe());
				shop.inDenWarenkorbLegen(shop.sucheKunde(id), a, stZa);
			} catch (ArtikelBestandIstZuKleinException e) {
				System.err.println("Der Bestand ist zu klein oder leer!");
			} catch (KundeExistiertNichtException e){
				System.err.println("Der Kunde existiert nicht!");
			} catch (ArtikelExistiertNichtException e1) {
				System.out.println("Artikel existiert nicht!");
			}
		}
		
		// Artikel aus dem Warenkorb entfernen
		
		else if (line.equals("we")) {
			System.out.print("Kunden ID >");
			int id = Integer.parseInt(liesEingabe());
			System.out.println("Artikel Nr. >");
			int artNr = Integer.parseInt(liesEingabe());
			boolean ok = false;
			try {
				shop.ausDemWarenkorbHerausnehmen(shop.sucheKunde(id), shop.gibArtikel(artNr));
				ok = true;
			} catch (KundeExistiertNichtException e){
				System.err.println("Der Kunde existiert nicht!");
			} catch (ArtikelExistiertNichtException e) {
				System.out.println("Artikel existiert nicht!");
			}
			if (ok)
				System.out.println("Entfernen ok");
			else
				System.out.println("Fehler beim Entfernen");
		}
		
		// Inhalt des Warenkorbes anzeigen lassen
		
		else if (line.equals("wa")) {
			System.out.print("Kunden ID >");
			int id = Integer.parseInt(liesEingabe());
			try {
				Kunde k = shop.sucheKunde(id);
				Collection<WarenkorbArtikel> liste = k.getWarenkorbVerwaltung().getWarenkorb();
				gibWarenkorblisteAus(liste);
			} catch (KundeExistiertNichtException e) {
				System.err.println("Kunde existiert nicht!");
			}
		}
		
		// Warenkorb leeren
		
		else if (line.equals("wl")) {
			System.out.print("Kunden ID >");
			int id = Integer.parseInt(liesEingabe());
			try {
				Kunde k = shop.sucheKunde(id);
				shop.leeren(k);
			} catch (KundeExistiertNichtException e) {
				System.err.println("Kunde existiert nicht!");
			}
		}
		
		// Artikel im Warenkorb kaufen und Rechnungsobjekt erstellen
		
		else if (line.equals("wk")) {
			System.out.print("Kunden ID >");
			try {
				Kunde k = shop.sucheKunde(Integer.parseInt(liesEingabe()));
				System.out.println(shop.kaufen(k).toString());
			} catch (KundeExistiertNichtException e) {
				System.err.println("Kunde existiert nicht!");
			}
		}
		else if (line.equals("q")) {
			
		}
		else {
			System.out.println("Dieser Befehl existiert nicht!");
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
			gibMenueAus();
			try {
				input = liesEingabe();
				verarbeiteEingabe(input);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} while (!input.equals("q"));
		shop.schreibeArtikel();
		shop.schreibeMitarbeiter();
		shop.schreibeKunden();
	}
	
	public static void main(String[] args) {
		ShopClientCUI cui;
		try {
			cui = new ShopClientCUI();
			cui.run();
		} catch (IOException IOe) {
			IOe.printStackTrace();
		} catch (ArtikelExistiertBereitsException AEBe) {
			AEBe.printStackTrace();
		} catch (ClassNotFoundException CNFe) {
			CNFe.printStackTrace();
		}
	}
	
}
