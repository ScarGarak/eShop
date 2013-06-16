package shop.local.ui.gui.mitarbeitergui.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class ArtikelTableCellRenderer extends DefaultTableCellRenderer{
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) { 
		Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		if(!isSelected){
			if(column == 4){
				if((Integer)value == 0){
					comp.setBackground(Color.RED);
				}else{
					comp.setBackground(new Color(34, 139, 34));
				}
			}else{
				comp.setBackground(null);
			}
		}else{
			comp.setBackground(Color.BLUE);
		}
		return this; 
	} 
}
