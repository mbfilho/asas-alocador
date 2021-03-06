package visualizer;


import javax.swing.JFrame;

import presentation.grouping.GroupsVisualizer;

import java.awt.GridBagLayout;

import java.awt.GridBagConstraints;


import java.awt.Frame;

import logic.services.WarningGeneratorService;

public class Visualizer extends FrameWithMenu{
	private static final long serialVersionUID = -6441797946984712515L;
	private GroupsVisualizer panelWithTable;

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
		
		panelWithTable = new GroupsVisualizer();
		GridBagConstraints gbc_panelWithTable = new GridBagConstraints();
		gbc_panelWithTable.fill = GridBagConstraints.BOTH;
		gbc_panelWithTable.gridy = 0;
		getContentPane().add(panelWithTable, gbc_panelWithTable);
		setVisible(true);
	}
	
	public void dispose(){
		super.dispose();
		panelWithTable.dispose();
	}
}
