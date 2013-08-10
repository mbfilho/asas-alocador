package warnings;

import java.awt.Color;
import java.awt.Component;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import utilities.ColorUtil;

class CheckboxCellRenderer extends JCheckBox implements TableCellRenderer{
	
	public CheckboxCellRenderer(){
		setOpaque(true);
		setHorizontalAlignment(SwingConstants.CENTER);
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Color back = null;
		
		if(value.equals(new Boolean(true))){
			setSelected(true);
			back = AllowedWarningsService.getAllowedWarningColor();
		}else{
			setSelected(false);
			back = Color.white;
		}
		
		if(isSelected) back = ColorUtil.mixColors(back, Color.green, Color.white);
		setBackground(back);
			
		return this;
	}
}

class LabelCellRenderer extends JLabel implements TableCellRenderer{

	private AllowedWarningsService allowedWarnings;
	
	public LabelCellRenderer(AllowedWarningsService allowed){
		setOpaque(true);
		this.allowedWarnings = allowed;
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		_Warning war = (_Warning) value;
		Color back = null;
		if(allowedWarnings.isAllowed(war)) back = AllowedWarningsService.getAllowedWarningColor();
		else back = Color.white;
		
		if(isSelected) back = ColorUtil.mixColors(back, Color.green, Color.white);
		setText(war.getMessage());
		setBackground(back);
		return this;
	}
	
}

class WarningTableModel extends AbstractTableModel{
	private static final long serialVersionUID = 1L;
	private Class[] columnTypes;
	private Vector<_Warning> warnings;
	private String[] columnHeaders;
	AllowedWarningsService allowedService;
	
	public WarningTableModel(AllowedWarningsService allowed,  Vector<_Warning> warnings){
		columnHeaders = new String[]{"Mensagem", "Autorizar?"};
		allowedService = allowed;
		columnTypes = new Class[] {	Object.class, Boolean.class };
		this.warnings = new Vector<_Warning>(warnings.size());
		for(_Warning w : warnings) addWarning(w);
		sortByAllowance();
	}

	private void sortByAllowance(){
		Collections.sort(warnings, new Comparator<_Warning>() {
			public int compare(_Warning o1, _Warning o2) {
				boolean allow1 = allowedService.isAllowed(o1), allow2 = allowedService.isAllowed(o2);
				if(allow1 == allow2) return 0;
				if(allow2) return -1;
				return 1;
			}
		});	
	}
	
	public void addWarning(_Warning warning){
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
}

public class WarningTable extends JTable {
	private WarningTableModel model;
	private AllowedWarningsService allowService;

	public WarningTable(){
		this(new Vector<_Warning>());
	}
	
	@SuppressWarnings("serial")
	public WarningTable(Vector<_Warning> warnings){
		super(new WarningTableModel(new AllowedWarningsService(), warnings));
		model = (WarningTableModel) getModel();
		allowService = model.allowedService;
		getColumnModel().getColumn(0).setPreferredWidth(700);
		getColumnModel().getColumn(1).setPreferredWidth(30);
		
		getColumnModel().getColumn(1).setCellRenderer(new CheckboxCellRenderer());
		getColumnModel().getColumn(0).setCellRenderer(new LabelCellRenderer(allowService));
	}
	
	public Component prepareEditor(TableCellEditor editor, int row, int column){
		Component c = super.prepareEditor(editor, row, column);
		_Warning w = (_Warning) model.getValueAt(row, 0);
        c.setBackground(allowService.isAllowed(w) ? AllowedWarningsService.getAllowedWarningColor() : Color.white);
        c.setForeground(Color.black);
		return c;
	}
	
	public void addWarning(_Warning warning){
		model.addWarning(warning);
	}
}
