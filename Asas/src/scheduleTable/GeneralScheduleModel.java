package scheduleTable;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.table.AbstractTableModel;

import utilities.ColorUtil;
import utilities.Constants;

public class GeneralScheduleModel extends AbstractTableModel{
	protected CellState cellState[][];
	protected JPanel popups[][];
	private String[] columnsNames;
	
	public GeneralScheduleModel(){
		cellState = new CellState[Constants.ROWS][Constants.COLUMNS];
		for(int i = 0; i < Constants.ROWS; ++i)
			for(int j = 0; j < Constants.COLUMNS; ++j)
				cellState[i][j] = new CellState();
		
		popups = new JPanel[Constants.SLOTS][Constants.DAYS];
		
		columnsNames = new String[] {
				"Hor\u00E1rio", "Domingo", "Segunda", "Ter\u00E7a", "Quarta", "Quinta", "Sexta", "S\u00E1bado"
		};
		
		configSlotsColumn();
	}
	
	private void configSlotsColumn(){
		for(int i = 7; i <= 21; ++i){
			cellState[i-7][0].setValue(String.format("%d-%d", i, i+1));
			cellState[i-7][0].setBackColor(ColorUtil.mixColors(Color.gray, Color.white, Color.white));
			cellState[i-7][0].setFontColor(Color.black);
			cellState[i-7][0].setTooltip("");
		}		
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

	public Component getPopupContent(int row, int column) {
		if(column == 0) return null;
		return popups[row][column-1];
	}
}
