package classEditor;

import javax.swing.JFrame;

import dataUpdateSystem.Updatable;
import dataUpdateSystem.UpdateDescription;

import groupMaker.InitialFilterConfiguration;
import scheduleTable.FilteredScheduleVisualization;
import utilities.DisposableOnEscFrame;

public class AdditionalVisualizationFrame extends DisposableOnEscFrame {
	private final int HEIGHT = 500, WIDTH = 500;
	public AdditionalVisualizationFrame(String title, int filterMode, 
			InitialFilterConfiguration initialFiltering, int order){
		setTitle(title);
		setContentPane(new FilteredScheduleVisualization(filterMode, initialFiltering));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(order * WIDTH + 30, 30, WIDTH, HEIGHT);
		setVisible(true);
	}
}
