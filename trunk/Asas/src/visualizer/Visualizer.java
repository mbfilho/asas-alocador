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

import dataUpdateSystem.RegistrationCentral;
import dataUpdateSystem.Updatable;
import dataUpdateSystem.UpdateDescription;


public class Visualizer extends FrameWithMenu implements Updatable{
	private static final long serialVersionUID = -6441797946984712515L;
	private FilteredScheduleVisualization panelWithTable;

	public Visualizer(WarningGeneratorService warningService) {
		super(warningService);
		RegistrationCentral.register(this);
		
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
	
	public void dispose(){
		super.dispose();
		panelWithTable.dispose();
		RegistrationCentral.unregister(this);
	}
	
	protected void onEditWarningInformation(){
		super.onEditWarningInformation();
	}
	
	//ao carregar um estado previamente salvo
	protected void onLoadNewState(State s){
		super.onLoadNewState(s);
	}
	
	protected void onEditProfessorInformation(){
		super.onEditProfessorInformation();
	}
}
