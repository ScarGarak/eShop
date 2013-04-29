package shop.local.ui.cui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import shop.local.domain.ShopVerwaltung;
import shop.local.domain.exceptions.ArtikelExistiertBereitsException;
import shop.local.domain.exceptions.ArtikelExistiertNichtException;
import shop.local.domain.exceptions.KundeExistiertBereitsException;
import shop.local.domain.exceptions.MitarbeiterExistiertBereitsException;
import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.Kunde;
import shop.local.valueobjects.Mitarbeiter;

public class ShopClientCUI {

	private ShopVerwaltung shop;
	private BufferedReader in;
	
	public ShopClientCUI() throws IOException {
		shop = new ShopVerwaltung();
		in = new BufferedReader(new InputStreamReader(System.in));
	}

	private void gibMenueAus() {
		System.out.print("Befehle: \n  Artikel einfuegen:           'e'");
		System.out.print("         \n  Artikel ausgeben nach Nr:    'a'");
		System.out.print("         \n  Artikel ausgeben nach Bez.:  'b'");
		System.out.print("         \n  Artikel suche nach Nr:       'f'");
		System.out.print("         \n  Artikel suche nach Bez.:     'g'");
		System.out.print("         \n  Artikel löschen nach Nr:     'k'");
		System.out.print("         \n  Artikel löschen nach Bez.:   'l'");
		System.out.print("         \n                                  ");
		System.out.print("         \n  Mitarbeiter einfuegen:       'me'");
		System.out.print("         \n  Mitarbeiter suche nach ID:   'mf'");
		System.out.print("         \n  Mitarbeiter löschen nach ID: 'ml'");
		System.out.print("         \n                                  ");
		System.out.print("         \n  Kunden einfuegen:         	'ke'");
		System.out.print("         \n  Kunden suche nach ID:   	    'kf'");
		System.out.print("         \n  Kunden loeschen nach ID:	    'kl'");
		System.out.println("         \n  Beenden:                     'q'");
		System.out.print("> ");
		System.out.flush();
	}

	private String liesEingabe() throws IOException {
		return in.readLine();
	}

	private void verarbeiteEingabe(String line) throws IOException {
		if (line.equals("e")) { 
			System.out.print("Artikelnummer > ");
			String nummer = liesEingabe();
			int aNr = Integer.parseInt(nummer);
			System.out.print("Bezeichnung  > ");
			String bezeichnung = liesEingabe();
			System.out.print("Preis  > ");
			String preis = liesEingabe();
			double aPr = Double.parseDouble(preis);
			System.out.print("Bestand > ");
			String bestand = liesEingabe();
			int aBtd = Integer.parseInt(bestand);
			boolean ok = false;
			try {
				shop.fuegeArtikelEin(aNr, bezeichnung, aPr, aBtd);
				ok = true;
			} catch (ArtikelExistiertBereitsException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
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
				System.err.println(e.getMessage());
				e.printStackTrace();
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
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
			if (ok)
				System.out.println("Löschen ok");
			else
				System.out.println("Fehler beim Löschen");
		}else if(line.equals("me")){
			System.out.print("Mitarbeiter ID >");
			String strId = liesEingabe();
			int id = Integer.parseInt(strId);
			System.out.print("Name >");
			String name = liesEingabe();
			try{
				shop.fuegeMitarbeiterHinzu(id, name);
			}catch(MitarbeiterExistiertBereitsException e){
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
		}else if(line.equals("mf")){
			System.out.print("Mitarbeiter ID >");
			int id = Integer.parseInt(liesEingabe());
			Mitarbeiter m = shop.sucheMitarbeiter(id);
			if(m != null){
				System.out.println(m.toString());
			}else{
				System.out.println("Ein Mitarbeiter mit dieser ID existiert nicht!");
			}
		}else if(line.equals("ml")){
			System.out.print("Mitarbeiter ID >");
			int id = Integer.parseInt(liesEingabe());
			shop.mitarbeiterLoeschen(shop.sucheMitarbeiter(id));
		}else
		
		if(line.equals("ke")){
			System.out.print("Kunden ID >");
			String strId = liesEingabe();
			int id = Integer.parseInt(strId);
			System.out.print("Name >");
			String name = liesEingabe();
			System.out.println("Strasse >");
			String strasse = liesEingabe();
			System.out.println("Postleitzahl >");
			String strPlz = liesEingabe();
			int plz = Integer.parseInt(strPlz);
			System.out.println("Wohnort >");
			String wohnort = liesEingabe();
			try{
				shop.fuegeKundenHinzu(id, name, strasse, plz, wohnort);
			}catch(KundeExistiertBereitsException e){
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
		}else if(line.equals("kf")){
			System.out.print("Kunden ID >");
			int id = Integer.parseInt(liesEingabe());
			Kunde k = shop.sucheKunde(id);
			if(k != null){
				System.out.println(k.toString());
			}else{
				System.out.println("Ein Kunde mit dieser ID existiert nicht!");
			}
		}else if(line.equals("kl")){
			System.out.print("Kunden ID >");
			int id = Integer.parseInt(liesEingabe());
			shop.kundenLoeschen(shop.sucheKunde(id));
		}else{
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

	public void run() {
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
