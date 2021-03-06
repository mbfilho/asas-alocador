package presentation.historySystem;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import logic.dataUpdateSystem.CustomerType;
import logic.dataUpdateSystem.DataUpdateCentral;
import logic.dataUpdateSystem.Updatable;
import logic.dataUpdateSystem.UpdateDescription;
import logic.historySystem.HistoryItem;
import logic.historySystem.HistoryService;

import exceptions.StateIOException;

public class HistoryTableModel extends AbstractTableModel implements Updatable{
	private static final long serialVersionUID = 160388601059615015L;
	
	private ArrayList<HistoryItem> rows;
	private HistoryService historyService;
	private String[] columnsNames;
	
	public HistoryTableModel(){
		historyService = HistoryService.getInstance();
		columnsNames = new String[]{"Histórico", "Hora da Modificação"};
		update();
		DataUpdateCentral.signIn(this, CustomerType.Gui);
	}
	
	public String getColumnName(int col){
		return columnsNames[col];
	}
	
	public int getRowCount() {
		return rows.size();
	}

	public int getColumnCount() {
		return columnsNames.length;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		if(columnIndex == 0)
			return rows.get(rowIndex).getDescription();
		else
			return rows.get(rowIndex).getModificationTime();
	}

	private void update(){
		rows = new ArrayList<HistoryItem>(historyService.getAllItens());
		fireTableDataChanged();
	}
	
	public void onDataUpdate(UpdateDescription desc) {
		update();
	}

	public void loadHistoryItem(int row) throws StateIOException {
		historyService.goToSelectedState(rows.get(row));
	}
}
