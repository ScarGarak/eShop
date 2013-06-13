package shop.local.ui.gui.kundengui.table;

import java.awt.Component;
import java.util.Currency;
import java.util.Locale;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class ArtikelTableCellRenderer extends DefaultTableCellRenderer {

	 @Override
     public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		 switch(column) {
		 	case 1: return super.getTableCellRendererComponent(table, String.format(" %.2f ", value) + Currency.getInstance(Locale.GERMANY), isSelected, hasFocus, row, column);
		 	case 2: return super.getTableCellRendererComponent(table, String.valueOf(value) + " ", isSelected, hasFocus, row, column);
		 	default: return super.getTableCellRendererComponent(table, " " + String.valueOf(value), isSelected, hasFocus, row, column);
		 }
	 }
	
}
