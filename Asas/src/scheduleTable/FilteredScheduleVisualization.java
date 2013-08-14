package scheduleTable;

import groupMaker.ClassFilter;
import groupMaker.FilterApplier;
import groupMaker.FilterChooser;
import groupMaker.Group;
import groupMaker.InitialFilterConfiguration;
import groupMaker.ProfessorGroup;
import groupMaker.RoomGroup;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import java.awt.Component;
import java.awt.GridBagLayout;
import java.util.List;
import java.awt.GridBagConstraints;


public class FilteredScheduleVisualization extends JPanel {
	private static final long serialVersionUID = -1937501007426648998L;
	private FilterChooser filterChooser;
	private JTabbedPane tabbedPane;
	private String _previousSelectedTitle;
	
	public FilteredScheduleVisualization() {
		this(FilterChooser.ALL, null);
	}
	
	public FilteredScheduleVisualization(int configuration, InitialFilterConfiguration initialConfiguration) {
		_previousSelectedTitle = "";
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{21, 0};
		gridBagLayout.rowHeights = new int[]{60, 217, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		tabbedPane = new JTabbedPane();
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 1;
		add(tabbedPane, gbc_tabbedPane);
		
		filterChooser = new FilterChooser(configuration, initialConfiguration) {
			private static final long serialVersionUID = -7767469001000877103L;

			public void onChangeFilter(ClassFilter newFilter) {
				refreshTable();
			}
		};
		GridBagConstraints gbc_filterChooser = new GridBagConstraints();
		gbc_filterChooser.gridx = 0;
		gbc_filterChooser.gridy = 0;
		gbc_filterChooser.weightx = 500;
		add(filterChooser, gbc_filterChooser);
		refreshTable();
	}
	
	private void clearTabs(){
		tabbedPane.removeAll();
	}
	
	private void getSelectedTitle(){
		if(tabbedPane.getSelectedIndex() != -1)
			_previousSelectedTitle = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
	}
	
	private void refreshTable(){
		System.out.println("Refreshing...");
		long t1 = System.currentTimeMillis();
		getSelectedTitle();
		clearTabs();
		List<Group> groups = new FilterApplier().applyFilter(filterChooser.getFilter());
		Component tabToSelect = null;
		for(final Group g : groups){
			GeneralGroupModel tableModel = null;
			
			if(g instanceof RoomGroup){
				tableModel = new GroupByClassroomModel(g.schedule.getSchedule(), ((RoomGroup)g).theRoom);
			}else if(g instanceof ProfessorGroup){
				tableModel = new GroupByProfessorModel(g.schedule.getSchedule(), ((ProfessorGroup) g).theProfessor);
			}else{
				tableModel = new GeneralGroupModel(g);
			}
			Component table = new ScheduleVisualizationTable(tableModel);
			JScrollPane tableScroll = new JScrollPane(table);
			tabbedPane.addTab(g.groupName, tableScroll);
			if(g.groupName.equals(_previousSelectedTitle))
				tabToSelect = tableScroll; 
		}
		
		if(tabToSelect != null)
			tabbedPane.setSelectedComponent(tabToSelect);
		System.out.println("Refreshed in: " + (System.currentTimeMillis() - t1));
	}
	
	public void update(){
		filterChooser.refresh();
	}
	
	

}
