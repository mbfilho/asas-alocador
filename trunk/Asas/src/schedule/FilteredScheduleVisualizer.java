package schedule;

import group.Group;
import group.ProfessorGroup;
import group.RoomGroup;
import group.filtering.ClassFilter;
import group.filtering.FilterApplier;
import group.filtering.FilterChooser;
import group.filtering.InitialFilterConfiguration;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import schedule.table.ScheduleVisualizationTable;
import schedule.table.models.GeneralGroupModel;
import schedule.table.models.GroupByClassroomModel;
import schedule.table.models.GroupByProfessorModel;
import utilities.GuiUtil;

import java.awt.GridBagLayout;
import java.util.List;
import java.awt.GridBagConstraints;


/**
 * Não precisa ser Updatable
 * @author mbof
 *
 */
public class FilteredScheduleVisualizer extends JPanel{
	private static final long serialVersionUID = -1937501007426648998L;
	private FilterChooser filterChooser;
	private JTabbedPane tabbedPane;
	
	public FilteredScheduleVisualizer() {
		this(FilterChooser.ALL, null);
	}
	
	public FilteredScheduleVisualizer(int configuration, InitialFilterConfiguration initialConfiguration) {
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
	
	private void refreshTable(){
		String selectedTitle = GuiUtil.getSelectedTabTitle(tabbedPane);
		clearTabs();
		List<Group> groups = new FilterApplier().applyFilter(filterChooser.getFilter());
		for(final Group g : groups){
			GeneralGroupModel tableModel = null;
			if(g instanceof RoomGroup){
				tableModel = new GroupByClassroomModel(g.schedule.getSchedule(), ((RoomGroup)g).theRoom);
			}else if(g instanceof ProfessorGroup){
				tableModel = new GroupByProfessorModel(g.schedule.getSchedule(), ((ProfessorGroup) g).theProfessor);
			}else{
				tableModel = new GeneralGroupModel(g);
			}
			JScrollPane tableScroll = new JScrollPane(new ScheduleVisualizationTable(tableModel));
			tabbedPane.addTab(g.groupName, tableScroll);
		}
		GuiUtil.selectTabWithThisTitle(tabbedPane, selectedTitle);
	}
	
	public void dispose(){
		filterChooser.dispose();
	}
}
