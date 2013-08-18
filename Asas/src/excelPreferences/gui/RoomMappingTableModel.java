package excelPreferences.gui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import utilities.Pair;

public class RoomMappingTableModel extends AbstractTableModel{
	private static final long serialVersionUID = -1780030494395816154L;
	
	private ArrayList<Pair<String, List<String>>> mapping;
	
	public RoomMappingTableModel(){
		mapping = new ArrayList<Pair<String,List<String>>>();
		addNewEmptyRow();
	}
	
	public void addNewRow(String code, List<String> rooms){
		mapping.remove(mapping.size()-1);
		mapping.add(new Pair<String, List<String>>(code, rooms));
		addNewEmptyRow();
	}
	
	public void setValueAt(Object value, int row, int col){
		Pair<String, List<String>> pair = mapping.get(row);
		if(col == 0) pair.first = (String) value;
		else pair.second = (List<String>) value;
		
		if(row == mapping.size() - 1)
			addNewEmptyRow();
	}
	
	private void addNewEmptyRow() {
		mapping.add(new Pair<String, List<String>>(null, new LinkedList<String>()));
		fireTableDataChanged();
	}

	public boolean isCellEditable(int row, int col){
		return true;
	}
	
	public Class getColumnClass(int column){
		if(column == 0) return String.class;
		return List.class;
	}
	
	public String getColumnName(int column){
		if(column == 0) return "Código";
		return "Salas Correspondentes";
	}

	public int getColumnCount() {
		return 2;
	}

	public int getRowCount() {
		return mapping.size();
	}

	public Object getValueAt(int row, int column) {
		Pair<String, List<String>> pair = mapping.get(row);
		if(column == 0) return pair.first;
		return pair.second;
	}
}
