package classEditor;

import javax.swing.JFrame;

import group.filtering.InitialFilterConfiguration;
import schedule.FilteredScheduleVisualizer;
import utilities.DisposableOnEscFrame;

public class AdditionalVisualizationFrame extends DisposableOnEscFrame {
	private final int HEIGHT = 500, WIDTH = 500;
	private FilteredScheduleVisualizer scheduleVisualization;
	
	public AdditionalVisualizationFrame(String title, int filterMode, 
			InitialFilterConfiguration initialFiltering, int order){
		setTitle(title);
		scheduleVisualization = new FilteredScheduleVisualizer(filterMode, initialFiltering);
		setContentPane(scheduleVisualization);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(order * WIDTH + 30, 30, WIDTH, HEIGHT);
		setVisible(true);
	}
	
	public void dispose(){
		super.dispose();
		scheduleVisualization.dispose();
	}
}
