
package shop.local.valueobjects;

/**
 * Diese Klasse beinhaltet alle Informationen die nur auf Mitarbeiter zutreffen 
 * und erbt von der Super-Klasse Person.
 * 
 * @author Angelo
 * @version 1
 * 
 * Zuletzt editiert: 10.04.2013
 */



public class Mitarbeiter extends Person{
	
	private int gehalt;
	
	
	/**
	 * Eine leere Konstruktor Funktion, die es ermöglicht eine Instanz dieses Objektes zu erstellen,
	 * und im nachhinein die Attribute zu initialisieren.
	 * @see Person
	 */
	public Mitarbeiter(){
		super();
		gehalt = 0;
	}
	
	/**
	 * Eine Konstruktor Funktion, die es ermöglicht das ID Attribut gleich zu Anfang zu initialisieren,
	 * und später die Andern.
	 * @param id Die ID Nummer des Mitarbeiters.
	 * @see Person
	 */
	public Mitarbeiter(int id){
		super(id);
		gehalt = 0;
	}
	
	/**
	 * Eine Konstruktor Funktion, die es ermöglicht gleich zu Anfang alle Attribute zu initialisieren.
	 * @param id ID Nummer des Mitarbeiters.
	 * @param vorname Vorname des Mitarbeiters.
	 * @param nachname Nachname des Mitarbeiters.
	 * @see Person
	 */
	public Mitarbeiter(int id, String name){
		super(id, name);
		gehalt = 0;
	}
	
	
	/**
	 * Getter Funktion für das Attribut "gehalt".
	 * @return Den Gehalt des Mitarbeiters.
	 */
	public int getGehalt(){
		return this.gehalt;
	}
	
	/**
	 * Setter Funktion für das Attribut "gehalt".
	 * @param gehalt Der Gehalt des Mitarbeiters.
	 */
	public void setGehalt(int gehalt){
		this.gehalt = gehalt;
	}
	
	@Override
	public String toString(){
		String output = "Mitarbeiter ID = "+this.getId()+" \t Name = "+this.getName();
		
		if(this.gehalt != 0){
			output += "\tGehalt = "+this.gehalt;
		}
		
		return output;
	}
	
	/*// Implementiert die abstract method equals aus abstract class ABCs (Demoklasse)	
	
	public boolean equals(Object o) {
		if (o instanceof Mitarbeiter) {
			return true;
		}
		return false;
	}*/
	
}
