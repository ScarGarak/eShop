
package shop.local.valueobjects;

/**
 * Diese Klasse beinhaltet alle Informationen die nur auf Mitarbeiter zutreffen 
 * und erbt von der Super-Klasse Person.
 * 
 * @author Angelo
 * @version 1
 * 
 * Zuletzt editiert: 11.05.2013
 */
public class Mitarbeiter extends Person{
	
	private static final long serialVersionUID = -3584470750142428422L;
	
	// Attribute zur Beschreibung eines Mitarbeiters:
	private int gehalt;
	
	/**
	 * Konstruktor
	 * @param id ID Nummer des Mitarbeiters.
	 * @param name Name des Mitarbeiters.
	 * @see Person
	 */
	public Mitarbeiter(int id, String name){
		super(id, name, PersonTyp.Mitarbeiter);
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
		String output = "ID = "+this.getId()+" \t Name = "+this.getName();
		
		if(this.gehalt != 0){
			output += "\tGehalt = "+this.gehalt;
		}
		
		return output;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Mitarbeiter) {
				return ((Mitarbeiter) o).getId() == this.getId();
		}else
			return false;
	}
	
}
