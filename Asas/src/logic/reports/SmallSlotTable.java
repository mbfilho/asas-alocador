package logic.reports;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import logic.ColorPoolForNames;
import logic.html.TdTag;
import data.persistentEntities.Class;
import data.persistentEntities.SlotRange;

public class SmallSlotTable {
	private final int ROWS_IN_SMALL_TABLE = 4;
	private ColorPoolForNames colorsToClasses;
	private List<TdTag>[] table;
	private String rowLabels[] = {"8-10", "10-12", "13-15", "15-17"};
	
	public SmallSlotTable(List<Class> classes, ColorPoolForNames colors){
		colorsToClasses = colors;
		table = new ArrayList[ROWS_IN_SMALL_TABLE];
		createSmallColoredSlotTable(classes);
	}
	
	public int getRowCount(){
		return ROWS_IN_SMALL_TABLE;
	}
	
	public List<TdTag> getNthRow(int row){
		return table[row];
	}
	
	private void createSmallColoredSlotTable(List<Class> classes) {
		Color smallTableColors[][] = calculateColorsForSmallTable(classes, colorsToClasses);
		
		for(int i = 0; i < ROWS_IN_SMALL_TABLE; ++i){
			ArrayList<TdTag> row = new ArrayList<TdTag>();
			row.add(new TdTag(rowLabels[i]));
			for(int j = 0; j < smallTableColors[i].length; ++j){
				TdTag cell = new TdTag();
				cell.setBackgroundColor(smallTableColors[i][j]).setMinWidth("30px");
				row.add(cell);
			}
			table[i] = row;
		}
	}

	private Color[][] calculateColorsForSmallTable(List<Class> classes, ColorPoolForNames colorsToClasses) {
		Color smallTable[][] = new Color[ROWS_IN_SMALL_TABLE][5];
		for(Class c : classes){
			for(SlotRange range : c.getSlots()){
				for(int i = range.getStartSlot(); i <= range.getEndSlot(); ++i)
					smallTable[getSmallTableRow(i)][getSmallTableColumn(range)] = colorsToClasses.getColor(c.getName());
			}
		}
		
		return smallTable;
	}
	
	//Nao suporta o domingo
	private int getSmallTableColumn(SlotRange slot){
		return slot.getDay() - 1;
	}

	//Não suporta o slot das 7-8 nem o das 12-13
	private int getSmallTableRow(int slot){
		if(slot > 5) //pular 12-13
			return (slot - 2) / 2;
		else
			return (slot - 1) / 2;
	}
}
