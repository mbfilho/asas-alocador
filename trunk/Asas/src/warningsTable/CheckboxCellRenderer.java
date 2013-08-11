package warningsTable;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

import services.AllowedWarningsService;
import utilities.ColorUtil;

public class CheckboxCellRenderer extends JCheckBox implements TableCellRenderer{
	
	private static final long serialVersionUID = 1L;

	public CheckboxCellRenderer(){
		setOpaque(true);
		setHorizontalAlignment(SwingConstants.CENTER);
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Color back = null;
		
		if(value.equals(new Boolean(true))){
			setSelected(true);
			back = AllowedWarningsService.getAllowedWarningColor();
		}else{
			setSelected(false);
			back = Color.white;
		}
		
		if(isSelected) back = ColorUtil.mixColors(back, Color.green, Color.white);
		setBackground(back);
			
		return this;
	}
}