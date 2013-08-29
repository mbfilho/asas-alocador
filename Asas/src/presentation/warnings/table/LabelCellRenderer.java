package presentation.warnings.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import data.persistentEntities.Warning;

import logic.services.AllowedWarningsService;

import utilities.ColorUtil;

public class LabelCellRenderer extends JLabel implements TableCellRenderer{

	private static final long serialVersionUID = 5226089533728001567L;
	private AllowedWarningsService allowedWarnings;
	
	public LabelCellRenderer(AllowedWarningsService allowed){
		setOpaque(true);
		this.allowedWarnings = allowed;
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Warning war = (Warning) value;
		Color back = null;
		if(allowedWarnings.isAllowed(war)) back = AllowedWarningsService.getAllowedWarningColor();
		else back = Color.white;
		
		if(isSelected) back = ColorUtil.mixColors(back, Color.green, Color.white);
		setText(war.getMessage());
		setBackground(back);
		return this;
	}
	
}
