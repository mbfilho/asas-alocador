package warningsTable;

import java.awt.Color;
import java.awt.Component;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import warnings.AllowedWarningsService;
import warnings.Warning;

public class WarningTable extends JTable {
	private static final long serialVersionUID = -6035225065621591680L;
	private WarningTableModel model;
	private AllowedWarningsService allowService;

	public WarningTable(){
		this(new Vector<Warning>());
	}
	
	public WarningTable(Vector<Warning> warnings){
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
		Warning w = (Warning) model.getValueAt(row, 0);
        c.setBackground(allowService.isAllowed(w) ? AllowedWarningsService.getAllowedWarningColor() : Color.white);
        c.setForeground(Color.black);
		return c;
	}
	
	public void addWarning(Warning warning){
		model.addWarning(warning);
	}
}
