package shop.local.ui.gui.table;

import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import shop.local.valueobjects.Artikel;

@SuppressWarnings("serial")
public class ArtikelTableModel extends DefaultTableModel {

	private Vector<String> columnNames;
	private Vector<Vector<String>> data;
	
	public ArtikelTableModel(List<Artikel> artikel) {
		super();
		
		columnNames = new Vector<String>();
		columnNames.add("Bezeichnung");
		columnNames.add("Preis");
		columnNames.add("Bestand");
		
		data = new Vector<Vector<String>>();
		updateDataVector(artikel);
	}
	
	public void updateDataVector(List<Artikel> artikel) {
		data.clear();
		
		for (Artikel a: artikel) {
			Vector<String> artikelVector = new Vector<String>();
			artikelVector.add(" " + a.getBezeichnung());
			artikelVector.add(" " + String.format("%.2f ", a.getPreis()) + Currency.getInstance(Locale.GERMANY));
			artikelVector.add(" " + a.getBestand());
			data.add(artikelVector);
		}
		
		setDataVector(data, columnNames);
	}
	
}
