package shop.local.ui.gui.mitarbeitergui.table;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import shop.local.valueobjects.Kunde;

@SuppressWarnings("serial")
public class KundenTableModel extends AbstractTableModel{
	
	private String[] columnNames = {"ID", "Username", "Name", "Stra§e", "Wohnort", "Postleitzahl"};
	private List<Kunde> kundenListe;
	
	public KundenTableModel(List<Kunde> kundenListe) {
		this.kundenListe = kundenListe;
	}
	
	@Override
	public boolean isCellEditable(int row, int column){
		return false;
	}

	@Override
	public int getRowCount() {
		return kundenListe.size();
	}

	@Override
	public int getColumnCount() {
		return 6;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
//		if(kundenListe.size() != 0)
		Kunde k = kundenListe.get(rowIndex);
		switch(columnIndex){
		case 0: return k.getId();
		case 1: return k.getUsername();
		case 2: return k.getName();
		case 3: return k.getStrasse();
		case 4: return k.getWohnort();
		case 5: return k.getPlz();
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
	
	public Kunde getKunde(int row){
		return kundenListe.get(row);
	}
	
}
