package presentation.state;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.table.AbstractTableModel;

import logic.services.StateService;

import data.configurations.StateDescription;

public class StateTableModel extends AbstractTableModel{

	private static final long serialVersionUID = 5082049411907783094L;
	private ArrayList<StateDescription> states;
	private String[] columnNames = {"Data da Criação", "Arquivo Excel", "Semestre"};
	
	public StateTableModel(){
		states = new ArrayList<StateDescription>(StateService.getInstance().allStates());
		Collections.sort(states, new Comparator<StateDescription>() {
			public int compare(StateDescription o1, StateDescription o2) {
				return (-1) * o1.getCreationTime().compareTo(o2.getCreationTime());
			}
		});
	}
	
	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return states.size();
	}

	public StateDescription getStateDescriptionAt(int row){
		return states.get(row);
	}
	
	public Object getValueAt(int row, int col) {
		StateDescription s = states.get(row);
		
		if(col == 0)
			return s.getFormattedCreationTime();
		else if(col == 1)
			return s.getExcelFile().getAbsolutePath();
		else if(col == 2)
			return s.getSemester();
		
		return null;
	}
	
	public String getColumnName(int col){
		return columnNames[col];
	}

	public void remove(StateDescription selected) {
		int row = states.indexOf(selected);
		states.remove(row);
		fireTableRowsDeleted(row, row);
	}
}
