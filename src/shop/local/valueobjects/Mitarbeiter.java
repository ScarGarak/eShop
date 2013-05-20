
package shop.local.valueobjects;

/**
 * Diese Klasse beinhaltet alle Informationen die nur auf Mitarbeiter zutreffen 
 * und erbt von der Super-Klasse Person.
 * 
 * @author Migliosi Angelo
 * @version 1
 * 
 * Zuletzt editiert: 18.05.2013
 */
public class Mitarbeiter extends Person{
	
	private static final long serialVersionUID = -3584470750142428422L;
	
	// Attribute zur Beschreibung eines Mitarbeiters:
	private int gehalt;
	
	/**
	 * Konstruktor
	 * @param id Id des Mitarbeiters
	 * @param username Username des Mitarbeiters
	 * @param passwort Passwort des Mitarbeiters
	 * @param name Name des Mitarbeiters
	 */
	public Mitarbeiter(int id, String username, String passwort, String name){
		super(id, username, passwort, name, PersonTyp.Mitarbeiter);
		gehalt = 0;
	}
	
	
	/**
	 * Setter Funktion für das Attribut "gehalt".
	 * @param gehalt Der Gehalt des Mitarbeiters.
	 */
	public void setGehalt(int gehalt){
		this.gehalt = gehalt;
	}
	
	/**
	 * Getter Funktion für das Attribut "gehalt".
	 * @return Den Gehalt des Mitarbeiters.
	 */
	public int getGehalt(){
		return this.gehalt;
	}
	
	
	@Override
	public String toString(){
		String output = "\nMitarbeiternummer: \t "+this.getId()+"\n"
					  + "Name:  \t\t"+this.getName()+"\n"
					  + "Username:\t\t" +this.getUsername()+"\n";
		
		if(this.gehalt != 0){
			output += "Gehalt: \t\t "+this.gehalt+"\n";
		}
		output += "\n";
		
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
