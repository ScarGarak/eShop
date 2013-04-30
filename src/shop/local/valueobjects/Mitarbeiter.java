
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
	 * Eine Konstruktor Funktion, die es erm�glicht gleich zu Anfang alle Attribute zu initialisieren.
	 * @param id ID Nummer des Mitarbeiters.
	 * @param vorname Vorname des Mitarbeiters.
	 * @param nachname Nachname des Mitarbeiters.
	 * @see Person
	 */
	public Mitarbeiter(int id, String name){
		super(id, name, PersonTyp.Mitarbeiter);
		gehalt = 0;
	}
	
	
	/**
	 * Getter Funktion f�r das Attribut "gehalt".
	 * @return Den Gehalt des Mitarbeiters.
	 */
	public int getGehalt(){
		return this.gehalt;
	}
	
	/**
	 * Setter Funktion f�r das Attribut "gehalt".
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
	
	// Implementiert die abstract method equals aus abstract class ABCs (Demoklasse)	
	
	// => Das ist gar nicht n�tig, denn diese wird schon von 'Object' geerbt und ist schon implementiert.
	//    Wir m�ssen diese Methode nur �berschreiben und unseren Bed�rfnissen anpassen.
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Mitarbeiter) {
				return ((Mitarbeiter) o).getId() == this.getId();
		}else
			return false;
	}
	
}
