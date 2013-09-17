package presentation.schedule;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JPopupMenu;
import javax.swing.JTable;



public class ScheduleTable extends JTable{
	private static final long serialVersionUID = -7132674009508957802L;
	private ScheduleTableModel model;
	
	public ScheduleTable(ScheduleTableModel tableModel){
		super(tableModel);
		this.model = tableModel;
		LabelCellRenderer render = new LabelCellRenderer();
		for(int i = 0; i < model.getColumnCount(); ++i)
			getColumnModel().getColumn(i).setCellRenderer(render);
		
		setRowHeight(50);
		setEnabled(false);
		//*
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JPopupMenu pop = new JPopupMenu();
				Point p = e.getPoint();
				if(p != null){
					int row = rowAtPoint(p), col = columnAtPoint(p);
					if(col > 0){
						List<Component> content = model.getPopupContent(row, col);
						
						if(!content.isEmpty()){
							pop.setLayout(new GridLayout(content.size(), 1));
							for(Component c : content)
								pop.add(c);
							pop.show(ScheduleTable.this, p.x, p.y);
						}
					}
				}
			}
		});//*/ 
	}
}
