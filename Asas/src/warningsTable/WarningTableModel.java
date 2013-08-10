package warningsTable;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import warnings.AllowedWarningsService;
import warnings.Warning;

public abstract class WarningTableModel extends AbstractTableModel{
	private static final long serialVersionUID = 1L;
	private Class[] columnTypes;
	private Vector<Warning> warnings;
	private String[] columnHeaders;
	AllowedWarningsService allowedService;
	
	public WarningTableModel(AllowedWarningsService allowed,  Vector<Warning> warnings){
		columnHeaders = new String[]{"Mensagem", "Autorizar?"};
		allowedService = allowed;
		columnTypes = new Class[] {	Object.class, Boolean.class };
		this.warnings = new Vector<Warning>(warnings.size());
		for(Warning w : warnings) addWarning(w);
		sortByAllowance();
	}

	private void sortByAllowance(){
		Collections.sort(warnings, new Comparator<Warning>() {
			public int compare(Warning o1, Warning o2) {
				boolean allow1 = allowedService.isAllowed(o1), allow2 = allowedService.isAllowed(o2);
				if(allow1 == allow2) return 0;
				if(allow2) return -1;
				return 1;
			}
		});	
	}
	
	public void addWarning(Warning warning){
		warnings.add(warning);
		fireTableRowsInserted(warnings.size(), warnings.size());
	}

	public String getColumnName(int col){
		return columnHeaders[col];
	}
	
	public Class getColumnClass(int columnIndex) {
		return columnTypes[columnIndex];
	}
	
	public boolean isCellEditable(int row, int col){
		return col != 0;
	}
	
	public void setValueAt(Object obj, int row, int col){
		if(col == 1){
			Boolean value = (Boolean) obj;
			if(value) allowedService.allow(warnings.get(row));
			else allowedService.disallow(warnings.get(row));
			
			sortByAllowance();
			onChangeWarningAllowance();
		}
		fireTableDataChanged();
	}
	
	public int getRowCount() {
		return warnings.size();
	}

	public int getColumnCount() {
		return 2;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		if(columnIndex == 0) return warnings.get(rowIndex);
		else return allowedService.isAllowed(warnings.get(rowIndex));
	}
	
	public abstract void onChangeWarningAllowance();
}

