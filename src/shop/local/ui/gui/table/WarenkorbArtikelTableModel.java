package shop.local.ui.gui.table;

import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import shop.local.valueobjects.WarenkorbArtikel;

@SuppressWarnings("serial")
public class WarenkorbArtikelTableModel extends DefaultTableModel {

	private Vector<String> columnNames;
	private Vector<Vector<String>> data;
	
	public WarenkorbArtikelTableModel(List<WarenkorbArtikel> warenkorbArtikel) {
		super();
		
		columnNames = new Vector<String>();
		columnNames.add("Stückzahl");
		columnNames.add("Bezeichner");
		columnNames.add("Preis");
		
		data = new Vector<Vector<String>>();
		updateDataVector(warenkorbArtikel);
	}
	
	public void updateDataVector(List<WarenkorbArtikel> warenkorbArtikel) {
		data.clear();
		
		for (WarenkorbArtikel wa: warenkorbArtikel) {
			Vector<String> artikelVector = new Vector<String>();
			artikelVector.add(" " + wa.getStueckzahl());
			artikelVector.add(" " + wa.getArtikel().getBezeichnung());
			artikelVector.add(" " + String.format("%.2f ", wa.getArtikel().getPreis()) + Currency.getInstance(Locale.GERMANY));
			data.add(artikelVector);
		}
		
		setDataVector(data, columnNames);
	}
	
}
