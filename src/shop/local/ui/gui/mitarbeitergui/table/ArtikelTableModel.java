package shop.local.ui.gui.mitarbeitergui.table;

import java.util.Currency;
import java.util.List;
import java.util.Locale;

import javax.swing.table.AbstractTableModel;

import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.Massengutartikel;

@SuppressWarnings("serial")
public class ArtikelTableModel extends AbstractTableModel {

	private String[] columnNames = {"Artikelnummer", "Bezeichnung", "Preis", "Packungsgrš§e", "Bestand"};
	private List<Artikel> artikelListe;
	
	public ArtikelTableModel(List<Artikel> artikelListe) {
		this.artikelListe = artikelListe;
	}
	
	@Override
	public boolean isCellEditable(int row, int column){
		return false;
	}

	@Override
	public int getRowCount() {
		return artikelListe.size();
	}

	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Artikel a = artikelListe.get(rowIndex);
		switch(columnIndex){
		case 0: return a.getArtikelnummer();
		case 1: return a.getBezeichnung();
		case 2: return String.format("%.2f ", a.getPreis()) + Currency.getInstance(Locale.GERMANY);
		case 3: if(a instanceof Massengutartikel){
					return ((Massengutartikel)a).getPackungsgroesse();
				}else{
					return 1;
				}
		case 4: return a.getBestand();
		default: return null;
		}
		
	}
	
	@Override
	public String getColumnName(int column) {
	    return columnNames[column];
	}
	
	public Artikel getArtikel(int row){
		return artikelListe.get(row);
	}
	
}
