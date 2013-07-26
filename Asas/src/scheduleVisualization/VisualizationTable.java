package scheduleVisualization;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class VisualizationTable extends JTable{
	private static final long serialVersionUID = -7132674009508957802L;
	private TableCellRenderer[][] tab = new TableCellRenderer[15][9];
	private TableFormatter formatter;
	
	public VisualizationTable(TableFormatter formatter){
		this.formatter = formatter;
		for(int i = 0; i < 15; ++i) for(int j = 0; j < 9; ++j) tab[i][j] = new DefaultTableCellRenderer();
		setRowHeight(50);
		setModel(new DefaultTableModel(
			new Object[][] {
				{"7-8", null, null, null, null, null, null, null},
				{"8-9", null, null, null, null, null, null, null},
				{"9-10", null, null, null, null, null, null, null},
				{"10-11", null, null, null, null, null, null, null},
				{"11-12", null, null, null, null, null, null, null},
				{"12-13", null, null, null, null, null, null, null},
				{"13-14", null, null, null, null, null, null, null},
				{"14-15", null, null, null, null, null, null, null},
				{"15-16", null, null, null, null, null, null, null},
				{"16-17", null, null, null, null, null, null, null},
				{"17-18", null, null, null, null, null, null, null},
				{"18-19", null, null, null, null, null, null, null},
				{"19-20", null, null, null, null, null, null, null},
				{"20-21", null, null, null, null, null, null, null},
			},
			new String[] {
				"Hor\u00E1rio", "Domingo", "Segunda", "Ter\u00E7a", "Quarta", "Quinta", "Sexta", "S\u00E1bado"
			}
		));
		setEnabled(false);
		for(int slot = 0; slot < 15; ++slot){
			for(int day = 0; day < 7; ++day){
				formatter.formatCell((JLabel)tab[slot][day+1].getTableCellRendererComponent(this, "", false, false, slot, day+1), day, slot);
			}
		}
		
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JPopupMenu pop = new JPopupMenu();
				Point p = e.getPoint();
				if(p != null){
					int row = VisualizationTable.this.rowAtPoint(p), col = VisualizationTable.this.columnAtPoint(p);
					if(col > 0){
						Component content = VisualizationTable.this.formatter.getPopupContent(col-1, row);
						if(content != null){
							pop.add(content);
							pop.show(VisualizationTable.this, p.x, p.y);
						}
					}
				}
			}
		}); 
	}
	
	public TableCellRenderer getCellRenderer(int row, int column){
		return tab[row][column];
	}
}
