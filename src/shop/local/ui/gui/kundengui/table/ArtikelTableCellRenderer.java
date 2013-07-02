package shop.local.ui.gui.kundengui.table;

import java.awt.Component;
import java.util.Currency;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class ArtikelTableCellRenderer extends DefaultTableCellRenderer {

	DefaultTableCellRenderer renderer;
	
	public ArtikelTableCellRenderer(JTable table) {
        renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.LEFT);
    }
	
	 @Override
     public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		 Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		 switch(column) {
		 	case 1: comp = super.getTableCellRendererComponent(table, String.format(" %.2f ", value) + Currency.getInstance(Locale.GERMANY), isSelected, hasFocus, row, column);
		 			break;
		 	case 2: comp = super.getTableCellRendererComponent(table, String.valueOf(value) + " ", isSelected, hasFocus, row, column);
		 			break;
		 	default: comp = super.getTableCellRendererComponent(table, " " + String.valueOf(value), isSelected, hasFocus, row, column);
		 }
		 
		 return comp;
	 }
	
}
