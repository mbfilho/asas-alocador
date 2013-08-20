package history.gui;

import history.HistoryTableModel;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import exceptions.StateIOException;


public class HistoryTable extends JTable {

	private static final long serialVersionUID = 14561845089841408L;
	
	public HistoryTable(final HistoryTableModel model){
		super(model);
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2){
					int op = JOptionPane.showConfirmDialog(
								HistoryTable.this,
								"Dejesa recuperar o estado selecionado? Todas as demais alterações serão perdidas.",
								"Atenção",
								JOptionPane.YES_NO_OPTION
							); 
					if(op == JOptionPane.YES_OPTION){
						Point p = e.getPoint();
						int row = rowAtPoint(p);
						try {
							model.loadHistoryItem(row);
						} catch (StateIOException e1) {
							JOptionPane.showMessageDialog(
										HistoryTable.this, 
										"Erro ao tentar recuperar estado.", 
										"Erro!",
										JOptionPane.ERROR_MESSAGE
									);
						}
					}
				}
			}
		});
	}
}
