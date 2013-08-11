package scheduleTable;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;
import javax.swing.JTable;


public class ScheduleVisualizationTable extends JTable{
	private static final long serialVersionUID = -7132674009508957802L;
	private GeneralScheduleModel model;
	
	public ScheduleVisualizationTable(GeneralScheduleModel tableModel){
		super(tableModel);
		this.model = tableModel;
		LabelCellRenderer render = new LabelCellRenderer();
		for(int i = 0; i < model.getColumnCount(); ++i)
			getColumnModel().getColumn(i).setCellRenderer(render);
		
		setRowHeight(50);
		
		//*
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JPopupMenu pop = new JPopupMenu();
				Point p = e.getPoint();
				if(p != null){
					int row = rowAtPoint(p), col = columnAtPoint(p);
					if(col > 0){
						Component content = model.getPopupContent(row, col);
						if(content != null){
							pop.add(content);
							pop.show(ScheduleVisualizationTable.this, p.x, p.y);
						}
					}
				}
			}
		});//*/ 
	}
}
