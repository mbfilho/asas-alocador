package presentation.schedule;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.table.AbstractTableModel;

import presentation.classes.EditClass;

import logic.schedule.formatting.detailing.Details;
import logic.schedule.formatting.detailing.FormattedDetail;
import logic.schedule.formatting.detailing.ScheduleSlotDetails;
import logic.schedule.formatting.formatters.ScheduleFormatter;
import logic.schedule.formatting.formatters.ScheduleSlotFormat;

import utilities.ColorUtil;
import utilities.Constants;

public class ScheduleTableModel extends AbstractTableModel{
	private static final long serialVersionUID = -4500499494277149184L;
	
	protected CellState cellState[][];
	private String[] columnsNames;
	private ScheduleFormatter formatter;
	
	public ScheduleTableModel(ScheduleFormatter formatter){
		this.formatter = formatter;
		cellState = new CellState[Constants.ROWS][Constants.COLUMNS];
		for(int i = 0; i < Constants.ROWS; ++i)
			for(int j = 0; j < Constants.COLUMNS; ++j)
				cellState[i][j] = new CellState();
		
		columnsNames = new String[] {
				"Hor\u00E1rio", "Domingo", "Segunda", "Ter\u00E7a", "Quarta", "Quinta", "Sexta", "S\u00E1bado"
		};
		
		configSlotsColumn();
		configureTable();
	}
	
	private void configSlotsColumn(){
		for(int i = 0; i < Constants.SLOTS; ++i){
			cellState[i][0].setValue((i + Constants.FIRST_INITIAL_HOUR) + "-" + (i+1+Constants.FIRST_INITIAL_HOUR));
			cellState[i][0].setBackColor(ColorUtil.mixColors(Color.gray, Color.white, Color.white));
			cellState[i][0].setFontColor(Color.black);
			cellState[i][0].setTooltip("");
		}		
	}
	
	private void configureTable(){
		for(int i = 0; i < Constants.SLOTS; ++i){
			for(int j = 0; j < Constants.DAYS; ++j){
				int row = i, column = j + 1;
				ScheduleSlotFormat format = formatter.getFormat(i, j);
				cellState[row][column].setBackColor(format.background);
				cellState[row][column].setFontColor(format.foreground);
				cellState[row][column].setTooltip(format.tooltip);
				cellState[row][column].setValue(joinIntoMultilineString(format.content));
				cellState[row][column].setBorder(format.border);
			}
		}
	}
	
	private String joinIntoMultilineString(List<String> lines){
		String joined = "<html>";
		for(String line : lines)
			joined += String.format("<p>%s</p>", line);
		return joined + "</html>";
	}
	
	public String getColumnName(int col){
		return columnsNames[col];
	}
	
	public int getRowCount() {
		return Constants.ROWS;
	}

	public int getColumnCount() {
		return columnsNames.length;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return cellState[rowIndex][columnIndex];
	}
	
	public List<Component> getPopupContent(int row, int column) {
		int slot = row, day = column - 1;
		return buildPopup(formatter.getDetails(slot, day));
	}
	
	private List<Component> buildPopup(ScheduleSlotDetails slotDetails){
		List<Component> popupComponents = new LinkedList<Component>(); 
		
		for(Details classDetail : slotDetails){
			for(FormattedDetail detail : classDetail)
				createSlotDetailsComponents(popupComponents, detail);
			popupComponents.add(new JPopupMenu.Separator());
		}
		
		return popupComponents;
	}

	private void createSlotDetailsComponents(List<Component> listOfComponents, FormattedDetail detail) {
		Component line = null;
		if(detail.isTitle()){
			line = createTitleLabel(detail.getContent(), detail.getBackgroundColor(), detail.getForegroundColor());
		}else if(detail.isLinkToClassEdition()){
			line = createLink(detail);
		}else			
			line = createColoredLabel(detail.getContent(), detail.getBackgroundColor(), detail.getForegroundColor());
		listOfComponents.add(line);
	}

	private JMenuItem createLink(final FormattedDetail detail) {
		String content = String.format("<html><i>(clique aqui) </i><b>%s</b></html>", detail.getContent());
		JMenuItem item = new JMenuItem(content);
		item.setOpaque(true);
		item.setBackground(detail.getBackgroundColor());
		item.setForeground(detail.getForegroundColor());
		item.setBorder(null);
		
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new EditClass(detail.getInitialEditState());
			}
		});
		return item;
	}
	
	private JLabel createColoredLabel(String text, Color bg, Color font){
		JLabel label = new JLabel(text);
		label.setBackground(bg);
		label.setForeground(font);
		label.setOpaque(true);
		return label;
	}
	
	private JLabel createTitleLabel(String text, Color bg, Color font){
		return createColoredLabel("<html><b>" + text + "</b></html>", bg, font);
	}
}
