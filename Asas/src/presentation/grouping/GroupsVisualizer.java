package presentation.grouping;


import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import presentation.schedule.ScheduleTableModel;
import presentation.schedule.ScheduleTable;

import utilities.GuiUtil;

import java.awt.GridBagLayout;
import java.util.List;
import java.awt.GridBagConstraints;

import logic.dto.GroupsSelector;
import logic.grouping.Group;
import logic.services.GroupMakerService;


/**
 * Não precisa ser Updatable
 * @author mbof
 *
 */
public class GroupsVisualizer extends JPanel{
	private static final long serialVersionUID = -1937501007426648998L;
	private GroupSelectorPanel groupSelector;
	private JTabbedPane groupsTabbedPane;
	
	public GroupsVisualizer() {
		this(GroupSelectorConfiguration.configurationForFullSelector());
	}
	
	public GroupsVisualizer(GroupSelectorConfiguration initialConfiguration) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{21, 0};
		gridBagLayout.rowHeights = new int[]{60, 217, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		groupsTabbedPane = new JTabbedPane();
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 1;
		add(groupsTabbedPane, gbc_tabbedPane);
		
		groupSelector = new GroupSelectorPanel(initialConfiguration) {
			private static final long serialVersionUID = -7767469001000877103L;

			public void onChangeSelector(GroupsSelector newFilter) {
				refreshTable();
			}
		};
		
		GridBagConstraints gbc_groupSelector = new GridBagConstraints();
		gbc_groupSelector.gridx = 0;
		gbc_groupSelector.gridy = 0;
		gbc_groupSelector.weightx = 500;
		add(groupSelector, gbc_groupSelector);
		refreshTable();
	}
	
	private void clearTabs(){
		groupsTabbedPane.removeAll();
	}
	
	private void refreshTable(){
		String selectedTitle = GuiUtil.getSelectedTabTitle(groupsTabbedPane);
		clearTabs();
		List<Group> groups = new GroupMakerService().getGroupsDefinedByTheSelector(groupSelector.getSelector());
		for(final Group g : groups){
			ScheduleTableModel tableModel = new ScheduleTableModel(g.getFormatter());
			JScrollPane tableScroll = new JScrollPane(new ScheduleTable(tableModel));
			groupsTabbedPane.addTab(g.groupName, tableScroll);
		}
		GuiUtil.selectTabWithThisTitle(groupsTabbedPane, selectedTitle);
	}
	
	public void dispose(){
		groupSelector.dispose();
	}
}
