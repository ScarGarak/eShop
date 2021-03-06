package shop.local.ui.gui.mitarbeitergui.table;

import java.util.Iterator;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import shop.local.valueobjects.Mitarbeiter;
import shop.local.valueobjects.MitarbeiterFunktion;

@SuppressWarnings("serial")
public class MitarbeiterTableModel extends AbstractTableModel{
	
	private String[] columnNames = {"ID", "Username", "Name", "Funktion", "Gehalt", "Blockiert"};
	private List<Mitarbeiter> mitarbeiterListe;
	private int columnCount;
	
	public MitarbeiterTableModel(List<Mitarbeiter> mitarbeiterListe, MitarbeiterFunktion mf) {
		this.mitarbeiterListe = mitarbeiterListe;
		if(mf.equals(MitarbeiterFunktion.Admin)){
			columnCount = 6; 
		}else{
			columnCount = 3;
		}
	}
	
	@Override
	public boolean isCellEditable(int row, int column){
		return false;
	}

	@Override
	public int getRowCount() {
		return mitarbeiterListe.size();
	}

	@Override
	public int getColumnCount() {
		return columnCount;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Mitarbeiter m = mitarbeiterListe.get(rowIndex);
		switch(columnIndex){
		case 0: return m.getId();
		case 1: return m.getUsername();
		case 2: return m.getName();
		case 3: return m.getFunktion().toString();
		case 4: return m.getGehalt();
		case 5: return m.getBlockiert() ? "Blockiert" : "Aktiv";
		default: return null;
		}
		
	}
	
	@Override
	public String getColumnName(int column) {
	    return columnNames[column];
	}
	
	@Override
	public Class<?> getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
	
	public Mitarbeiter getMitarbeiter(int row){
		return mitarbeiterListe.get(row);
	}
	
	public int getRowIndex(String username){
		Iterator<Mitarbeiter> iter = mitarbeiterListe.iterator();
		while(iter.hasNext()){
			Mitarbeiter m = iter.next();
			if(m.getUsername().equals(username)){
				return mitarbeiterListe.indexOf(m);
			}
		}
		
		return -1;
	}

}
