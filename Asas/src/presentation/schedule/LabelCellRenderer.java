package presentation.schedule;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;

import utilities.StringUtil;


public class LabelCellRenderer extends JLabel implements TableCellRenderer{
	private static final long serialVersionUID = -4948134859251806171L;
	
	private Font defaultFont;
	private Border defaultBorder;
	
	public LabelCellRenderer(){
		setOpaque(true);
		defaultBorder = BorderFactory.createLineBorder(Color.black, 1); 
		defaultFont = getFont();
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		CellState state = (CellState) value;
		
		setText(state.getValue());
		if(column == 0){
			setHorizontalAlignment(SwingConstants.CENTER);
			setFont(new Font(defaultFont.getName(), Font.BOLD, 14));
		}else{
			setFont(defaultFont);
			setHorizontalAlignment(SwingConstants.LEFT);
		}
		
		setBorder(state.getBorder() == null ? defaultBorder : state.getBorder());
		if(!StringUtil.isNullOrEmpty(state.getTooltip()))
			setToolTipText(state.getTooltip());
		else
			setToolTipText(null);
		setBackground(state.getBackColor());
		setForeground(state.getFontColor());
		
		return this;
	}
}