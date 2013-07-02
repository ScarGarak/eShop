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
		
		data = (Vector<Artikel>) artikel;
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
	public Class<?> getColumnClass(int c) {
		if(getValueAt(0, c) != null){
			return getValueAt(0, c).getClass();
		}else{
			return Object.class;
		}
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		if (data != null && data.size() != 0) {
			Artikel a = data.get(row);
			switch(col) {
				case 0: return a.getBezeichnung();
				case 1: return a.getPreis();
				case 2: return a.getBestand();
				default: return null;
			}
		} else {
			return null;
		}
	}
	
	public Artikel getRowValue(int row) {
		return data.get(row);
	}
	
}