package shop.local.ui.gui.mitarbeitergui.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class KundenTableCellRenderer extends DefaultTableCellRenderer {
	
	public KundenTableCellRenderer(){
		
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) { 
		Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		if(isSelected){
			comp.setBackground(new Color(35, 79, 154));
			comp.setForeground(Color.WHITE);
		}else{
			comp.setBackground(Color.WHITE);
			comp.setForeground(Color.BLACK);
			if(column == 6){
				if(value.equals("Blockiert")){
					comp.setBackground(Color.RED);
				}else{
					comp.setBackground(new Color(34, 139, 34));
				}
			}
		}
		
		return comp;
	}
}
