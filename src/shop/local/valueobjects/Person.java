package shop.local.valueobjects;

public class Person {

	private int id;
	private String vorname;
	private String nachname;
	private String passwort;

	
	public Person(){
		
	}
	
	
	/**
	 * Konstruktor
	 * @param id Die ID Nummer der Person.
	 */
	public Person(int id){
		this.id = id;
	}
	
	
	/**
	 * Konstruktor
	 * @param id ID Nummer der Person.
	 * @param vorname Vorname der Person.
	 * @param nachname Nachname der Person.
	 */
	public Person(int id, String vorname, String nachname){
		this.id = id;
		this.vorname = vorname;
		this.nachname = nachname;
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Person){
			return (this.id == ((Person)o).getId());
		}else{
			return false;
		}
	}
	
		/*----------------------------
		 * 			GETTERS			 |
		 *----------------------------*/
	/**
	 * Getter für "vorname".
	 * @return Den Vornamen der Person.
	 */
	public String getVorname(){
		return this.vorname;
	}
	
	/**
	 * Getter für "nachname".
	 * @return Den Nachnamen der Person.
	 */
	public String getNachname(){
		return this.nachname;
	}
	
	
	/**
	 * Getter für "id".
	 * @return Die ID Nummer der Person.
	 */
	public int getId(){
		return this.id;
	}
	
	/**
	 * Getter für "passwort".
	 * @return Das Passwort der Person.
	 */
	public String getPasswort(){
		return this.passwort;
	}
	
	
		/*----------------------------
		 * 			SETTERS			 |
		 *----------------------------*/
	
	/**
	 * Setter für "vorname".
	 * @param vorname Den Vornamen der Person.
	 */
	public void setVorname(String vorname){
		this.vorname = vorname;
	}
	
	/**
	 * Setter für "nachname".
	 * @param nachname Den Nachnamen der Person.
	 */
	public void setNachname(String nachname){
		this.nachname = nachname;
	}
	
	/**
	 * Setter für "id".
	 * @param id Die ID Nummer der Person.
	 */
	public void setId(int id){
		this.id = id;
	}
	
	/**
	 * Setter für "passwort".
	 * @param passwort Das Passwort der Person.
	 */
	public void getPasswort(String passwort){
		this.passwort = passwort;
	}
	
}
