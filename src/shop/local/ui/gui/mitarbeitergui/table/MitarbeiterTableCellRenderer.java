package shop.local.ui.gui.mitarbeitergui.table;

import java.awt.Component;
import java.util.Currency;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class MitarbeiterTableCellRenderer extends DefaultTableCellRenderer {

	public MitarbeiterTableCellRenderer(JTable table) {
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.LEFT);
    }
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) { 
		if(column == 3){
			return super.getTableCellRendererComponent(table, String.format(" %.2f ", value) + Currency.getInstance(Locale.GERMANY), isSelected, hasFocus, row, column); 
		}else{
			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}
	} 
}
