package preferences.gui;

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
		List<String> x = new LinkedList<String>();
		x.add("D-005");
		x.add("D-002");
		mapping.add(new Pair<String, List<String>>("92", x));
		fireTableDataChanged();
	}
	
	public void setValueAt(Object value, int row, int col){
		Pair<String, List<String>> pair = mapping.get(row);
		if(col == 0) pair.first = (String) value;
		else pair.second = (List<String>) value;
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
