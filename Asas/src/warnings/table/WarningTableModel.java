package warnings.table;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import dataUpdateSystem.RegistrationCentral;

import services.AllowedWarningsService;

import warnings.types.Warning;

public class WarningTableModel extends AbstractTableModel{
	public static final int WARNING_COLUMN = 0;
	private static final long serialVersionUID = 1L;
	private Class[] columnTypes;
	private Vector<Warning> warnings;
	private String[] columnHeaders;
	private AllowedWarningsService allowedService;
	
	public WarningTableModel(AllowedWarningsService allowed,  List<Warning> warnings){
		columnHeaders = new String[]{"Mensagem", "Autorizar?"};
		allowedService = allowed;
		columnTypes = new Class[] {	Object.class, Boolean.class };
		this.warnings = new Vector<Warning>(warnings);
		sortByAllowance();
		fireTableDataChanged();
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
		return col != WARNING_COLUMN;
	}
	
	public void setValueAt(Object obj, int row, int col){
		if(col == 1){
			Boolean value = (Boolean) obj;
			if(value) allowedService.allow(warnings.get(row));
			else allowedService.disallow(warnings.get(row));
			
			sortByAllowance();
			RegistrationCentral.registerUpdate("Mudança na autorização de alertas.");
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
		if(columnIndex == WARNING_COLUMN) return warnings.get(rowIndex);
		else return allowedService.isAllowed(warnings.get(rowIndex));
	}
}

