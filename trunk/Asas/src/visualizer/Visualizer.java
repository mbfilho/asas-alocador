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

import scheduleTable.FilteredScheduleVisualization;
import scheduleTable.GeneralGroupModel;
import scheduleTable.GroupByClassroomModel;
import scheduleTable.GroupByProfessorModel;
import scheduleTable.ScheduleVisualizationTable;
import services.WarningGeneratorService;
import statePersistence.State;

import java.util.List;
import java.util.Vector;

import java.awt.Frame;
import javax.swing.JTabbedPane;


public class Visualizer extends FrameWithMenu {
	private static final long serialVersionUID = -6441797946984712515L;
	private FilteredScheduleVisualization panelWithTable;
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
	
	public Visualizer(WarningGeneratorService warningService) {
		super(warningService);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setTitle("Visualizar");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{21, 0};
		gridBagLayout.rowHeights = new int[]{60, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		panelWithTable = new FilteredScheduleVisualization();
		GridBagConstraints gbc_panelWithTable = new GridBagConstraints();
		gbc_panelWithTable.fill = GridBagConstraints.BOTH;
		gbc_panelWithTable.gridy = 0;
		getContentPane().add(panelWithTable, gbc_panelWithTable);
		setVisible(true);
	}
	
	
	protected void onEditWarningInformation(){
		super.onEditWarningInformation();
		panelWithTable.update();
	}
	
	//ao carregar um estado previamente salvo
	protected void onLoadNewState(State s){
		super.onLoadNewState(s);
		System.out.println("ONCE, VIS 89");
		long t1 = System.currentTimeMillis();
		panelWithTable.update();
		System.out.println("To update: " + (System.currentTimeMillis() - t1));
	}
	
	//ao editar informaÃ§Ãµes de uma turma
	protected void onEditClassInformation() {
		super.onEditClassInformation();
		panelWithTable.update();
	}
	
	protected void onEditProfessorInformation(){
		super.onEditProfessorInformation();
		panelWithTable.update();
	}
	
	protected void onEditClassroomInformation(){
		super.onEditClassInformation();
		panelWithTable.update();
	}
	
}
