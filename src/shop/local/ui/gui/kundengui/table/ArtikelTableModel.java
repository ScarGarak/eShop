package shop.local.ui.gui.kundengui.table;

import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import shop.local.valueobjects.Artikel;

@SuppressWarnings("serial")
public class ArtikelTableModel extends AbstractTableModel {

	private Vector<String> columnNames;
	private Vector<Artikel> data;
	
	public ArtikelTableModel(List<Artikel> artikel) {
		super();
		
		columnNames = new Vector<String>();
		columnNames.add("Bezeichnung");
		columnNames.add("Preis");
		columnNames.add("Bestand");
		
		updateDataVector(artikel);
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.size();
	}

	@Override
	public int getRowCount() {
		return data.size();
	}
	
	@Override
	public String getColumnName(int column) {
        return columnNames.get(column);
    }
	
	@Override
    public boolean isCellEditable(int row, int column) {
       return false;
    }
	
	@Override
	public Class<?> getColumnClass(int column) {
		if (column == 2) {
			return getValueAt(0, column).getClass();
		} else {
			return super.getColumnClass(column);
		}
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		Artikel a = data.get(row);
		switch(col) {
			case 0: return a.getBezeichnung();
			case 1: return a.getPreis();
			case 2: return a.getBestand();
			default: return "";
		}
	}
	
	public void updateDataVector(List<Artikel> artikel) {
		data = (Vector<Artikel>) artikel;
	}
	
	public Artikel getRowValue(int row) {
		return data.get(row);
	}
	
}