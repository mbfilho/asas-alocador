package visualizer;


import groupMaker.ClassFilter;
import groupMaker.FilterApplier;
import groupMaker.FilterChooser;
import groupMaker.Group;
import groupMaker.ProfessorGroup;
import groupMaker.RoomGroup;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;

import java.awt.Component;
import java.awt.GridBagConstraints;

import javax.swing.JScrollPane;

import scheduleTable.GeneralGroupModel;
import scheduleTable.GroupByClassroomModel;
import scheduleTable.GroupByProfessorModel;
import scheduleTable.ScheduleVisualizationTable;
import services.WarningGeneratorService;
import statePersistence.State;

import java.util.Vector;

import java.awt.Frame;
import javax.swing.JTabbedPane;


public class Visualizer extends FrameWithMenu {
	private static final long serialVersionUID = -6441797946984712515L;
	private FilterChooser filterChooser;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Visualizer frame = new Visualizer(new WarningGeneratorService());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	private JTabbedPane tabbedPane;
	
	public Visualizer(WarningGeneratorService warningService) {
		super(warningService);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setTitle("Visualizar");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{21, 0};
		gridBagLayout.rowHeights = new int[]{115, 217, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		filterChooser = new FilterChooser() {
			public void onChangeFilter(ClassFilter newFilter) {
				refreshTable();
			}
		};
		GridBagConstraints gbc_filterChooser = new GridBagConstraints();
		gbc_filterChooser.gridx = 0;
		gbc_filterChooser.gridy = 0;
		gbc_filterChooser.weightx = 500;
		getContentPane().add(filterChooser, gbc_filterChooser);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 1;
		getContentPane().add(tabbedPane, gbc_tabbedPane);
		setVisible(true);
	}
	
	private void refreshTable(){
		clearTabs();
		Vector<Group> groups = new FilterApplier().applyFilter(filterChooser.getFilter());
		
		for(final Group g : groups){
			GeneralGroupModel tableModel = null;
			
			if(g instanceof RoomGroup){
				tableModel = new GroupByClassroomModel(g.schedule.getSchedule(), ((RoomGroup)g).theRoom);
			}else if(g instanceof ProfessorGroup){
				tableModel = new GroupByProfessorModel(g.schedule.getSchedule(), ((ProfessorGroup) g).theProfessor);
			}
			Component table = new ScheduleVisualizationTable(tableModel);
			tabbedPane.addTab(g.groupName, new JScrollPane(table));
		}
	}
	
	
	private void clearTabs(){
		tabbedPane.removeAll();
	}
	
	protected void onEditWarningInformation(){
		super.onEditWarningInformation();
		refreshTable();
	}
	
	//ao carregar um estado previamente salvo
	protected void onLoadNewState(State s){
		super.onLoadNewState(s);
		filterChooser.refresh();
		refreshTable();
	}
	
	//ao editar informaÃ§Ãµes de uma turma
	protected void onEditClassInformation() {
		super.onEditClassInformation();
		refreshTable();
	}
	
	protected void onEditProfessorInformation(){
		super.onEditProfessorInformation();
		filterChooser.refresh();
		refreshTable();
	}
	
	protected void onEditClassroomInformation(){
		super.onEditClassInformation();
		refreshTable();
	}
	
}
