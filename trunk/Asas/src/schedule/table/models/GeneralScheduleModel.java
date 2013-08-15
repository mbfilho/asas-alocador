package schedule.table.models;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.table.AbstractTableModel;

import schedule.table.CellState;
import utilities.ColorUtil;
import utilities.Constants;

public class GeneralScheduleModel extends AbstractTableModel{
	protected CellState cellState[][];
	private String[] columnsNames;
	
	public GeneralScheduleModel(){
		cellState = new CellState[Constants.ROWS][Constants.COLUMNS];
		for(int i = 0; i < Constants.ROWS; ++i)
			for(int j = 0; j < Constants.COLUMNS; ++j)
				cellState[i][j] = new CellState();
		
		columnsNames = new String[] {
				"Hor\u00E1rio", "Domingo", "Segunda", "Ter\u00E7a", "Quarta", "Quinta", "Sexta", "S\u00E1bado"
		};
		
		configSlotsColumn();
	}
	
	private void configSlotsColumn(){
		for(int i = Constants.FIRST_INITIAL_HOUR; i <= Constants.LAST_INITIAL_HOUR; ++i){
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
		return null;
	}
}
