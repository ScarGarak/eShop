package shop.local.valueobjects;

abstract class ABCs {

	private int id;
	private String name;
	private String passwort;

	
	public ABCs(){
		
	}
	
	
	/**
	 * Konstruktor
	 * @param id Die ID Nummer der Person.
	 */
	public ABCs(int id){
		this.id = id;
	}
	
	
	/**
	 * Konstruktor
	 * @param id ID Nummer der Person.
	 * @param vorname Vorname der Person.
	 * @param nachname Nachname der Person.
	 */
	public ABCs(int id, String name){
		this.id = id;
		this.name = name;
	}
	
	//@Override
	public abstract boolean equals(Object o);/*{
		if(o instanceof Person){
			return (this.id == ((Person)o).getId());
		}else{
			return false;
		}
	}*/
	
		/*----------------------------
		 * 			GETTERS			 |
		 *----------------------------*/
	/**
	 * Getter für "name".
	 * @return Den Namen der Person.
	 */
	public String getName(){
		return this.name;
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
	 * Setter für "Name".
	 * @param vorname Den Vornamen der Person.
	 */
	public void setName(String name){
		this.name = name;
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
