package shop.local.valueobjects;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class Person implements Serializable{

	private int id;
	private String name;
	private String passwort;
	private PersonTyp typ;
	
	/**
	 * Konstruktor
	 * @param id ID Nummer der Person.
	 * @param name Name der Person.
	 * @param typ Der Typ der Person, d.h. Mitarbeiter oder Kunde.
	 */
	public Person(int id, String name, PersonTyp typ){
		this.id = id;
		this.name = name;
		this.typ = typ;
	}
	
	
		/*----------------------------
		 * 			GETTERS			 |
		 *----------------------------*/
	/**
	 * Getter f�r "name".
	 * @return Den Namen der Person.
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Getter f�r "id".
	 * @return Die ID Nummer der Person.
	 */
	public int getId(){
		return this.id;
	}
	
	/**
	 * Getter f�r "passwort".
	 * @return Das Passwort der Person.
	 */
	public String getPasswort(){
		return this.passwort;
	}
	
	/**
	 * Getter fuer "typ".
	 * @param typ
	 */
	public PersonTyp getPersonTyp(PersonTyp typ){
		return this.typ;
	}
	
	
		/*----------------------------
		 * 			SETTERS			 |
		 *----------------------------*/
	
	/**
	 * Setter f�r "Name".
	 * @param name Den Namen der Person.
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * Setter f�r "id".
	 * @param id Die ID Nummer der Person.
	 */
	public void setId(int id){
		this.id = id;
	}
	
	/**
	 * Setter f�r "passwort".
	 * @param passwort Das Passwort der Person.
	 */
	public void setPasswort(String passwort){
		this.passwort = passwort;
	}
	
	/**
	 * Setter fuer den "typ".
	 * @param typ Der Typ der Person.
	 */
	public void setPersonTyp(PersonTyp typ){
		this.typ = typ;
	}
	
}
