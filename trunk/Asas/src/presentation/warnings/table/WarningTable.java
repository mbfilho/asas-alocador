package presentation.warnings.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import presentation.NamedPair;
import presentation.classes.edition.EditClass;

import logic.services.AllowedWarningsService;

import data.persistentEntities.Class;
import data.persistentEntities.Warning;

public class WarningTable extends JTable {
	private static final long serialVersionUID = -6035225065621591680L;
	private WarningTableModel model;
	private AllowedWarningsService allowService;

	public WarningTable(){
		this(new Vector<Warning>());
	}
	
	public WarningTable(List<Warning> warnings){
		allowService = new AllowedWarningsService();
		model = new WarningTableModel(allowService, warnings);
		setModel(model);
		
		getColumnModel().getColumn(0).setPreferredWidth(700);
		getColumnModel().getColumn(1).setPreferredWidth(30);
		
		getColumnModel().getColumn(1).setCellRenderer(new CheckboxCellRenderer());
		getColumnModel().getColumn(0).setCellRenderer(new LabelCellRenderer(allowService));
		configureSolveWarningPopup();
	}

	private void configureSolveWarningPopup(){
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Point p = e.getPoint();
				int row = rowAtPoint(p);
				
				if(e.getButton() == MouseEvent.BUTTON3){
					setRowSelectionInterval(row, row);
					final Warning selected = (Warning) model.getValueAt(row, WarningTableModel.WARNING_COLUMN);
					JPopupMenu menu = new JPopupMenu();
					
					for(final NamedPair<Class> c : selected.getSolutionList()){
						JMenuItem solveItem = new JMenuItem(c.name);
						solveItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								new EditClass(selected.getInfoToSolve(c.data));
							}
						});
						menu.add(solveItem);
					}
					menu.show(WarningTable.this, p.x, p.y);
				}
			}
		});
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
