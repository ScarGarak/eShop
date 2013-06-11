package shop.local.ui.gui.table;

import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import shop.local.valueobjects.WarenkorbArtikel;

@SuppressWarnings("serial")
public class WarenkorbArtikelTableModel extends AbstractTableModel {

	private Vector<String> columnNames;
	private Vector<WarenkorbArtikel> data;
	
	public WarenkorbArtikelTableModel(List<WarenkorbArtikel> warenkorbArtikel) {
		super();
		
		columnNames = new Vector<String>();
		columnNames.add("Stückzahl");
		columnNames.add("Bezeichner");
		columnNames.add("Preis");
		
		updateDataVector(warenkorbArtikel);
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
	public Object getValueAt(int row, int col) {
		WarenkorbArtikel wa = data.get(row);
		switch(col) {
			case 0: return (" " + wa.getStueckzahl());
			case 1: return (" " + wa.getArtikel().getBezeichnung());
			case 2: return (" " + String.format("%.2f ", wa.getArtikel().getPreis()) + Currency.getInstance(Locale.GERMANY));
			default: return (" ");
		}
	}
	
	public void updateDataVector(List<WarenkorbArtikel> warenkorbArtikel) {
		data = (Vector<WarenkorbArtikel>) warenkorbArtikel;
	}
	
	public WarenkorbArtikel getRowValue(int row) {
		return data.get(row);
	}
	
}
