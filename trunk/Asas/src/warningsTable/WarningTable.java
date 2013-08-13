package warningsTable;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import basic.Class;

import classEditor.EditClassFrame;
import classEditor.NamedPair;

import services.AllowedWarningsService;
import services.WarningGeneratorService;

import warnings.Warning;

public abstract class WarningTable extends JTable {
	private static final long serialVersionUID = -6035225065621591680L;
	private WarningTableModel model;
	private AllowedWarningsService allowService;

	public WarningTable(){
		this(new Vector<Warning>());
	}
	
	public WarningTable(Vector<Warning> warnings){
		allowService = new AllowedWarningsService();
		model = new WarningTableModel(allowService, warnings) {
			public void onChangeWarningAllowance() {
				WarningTable.this.onChangeWarningAllowance();
			}
		};
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
								new EditClassFrame(selected.getInfoToSolve(c.data)) {
									public void classInformationEdited() {}
								};
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
	
	public abstract void onChangeWarningAllowance();
}
