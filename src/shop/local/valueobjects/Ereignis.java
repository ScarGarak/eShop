package shop.local.valueobjects;

import java.util.Date;

public class Ereignis {
	
	private Date datum;
	
	private Artikel artikel;
	private int anzahl;
	
	private Person p;
	
	public Ereignis (Date datum, Artikel artikel, int anzahl, Person p){
		this.datum = datum;
		this.artikel = artikel;
		this.anzahl = anzahl;
		this.p = p;
	}
	
	public void setDatum(Date datum){
		this.datum = datum;
	}
	
	public Date getDatum(){
		return datum;
	}
	
	
	public void setArtikel(Artikel artikel){
		this.artikel = artikel;
	}
	
	public Artikel getArtikel(){
		return artikel;
	}
	
	
	public void setAnzahl(int anzahl){
		this.anzahl = anzahl;
	}
	
	public int getAnzahl(){
		return anzahl;
	}
	
	
	public void setPerson(Person p){
		this.p = p;
	}
	
	public Person getPerson(){
		return p;
	}

}
