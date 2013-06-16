package shop.local.ui.gui.mitarbeitergui.table;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import shop.local.valueobjects.Mitarbeiter;

@SuppressWarnings("serial")
public class MitarbeiterTableModel extends AbstractTableModel{
	
	private String[] columnNames = {"ID", "Username", "Name", "Gehalt"};
	private List<Mitarbeiter> mitarbeiterListe;
	
	public MitarbeiterTableModel(List<Mitarbeiter> mitarbeiterListe) {
		this.mitarbeiterListe = mitarbeiterListe;
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
		return 4;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Mitarbeiter m = mitarbeiterListe.get(rowIndex);
		switch(columnIndex){
		case 0: return m.getId();
		case 1: return m.getUsername();
		case 2: return m.getName();
		case 3: return m.getGehalt();
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

}
