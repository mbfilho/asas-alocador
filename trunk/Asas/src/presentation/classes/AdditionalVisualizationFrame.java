package presentation.classes;

import javax.swing.JFrame;

import presentation.grouping.GroupSelectorConfiguration;
import presentation.grouping.GroupsVisualizer;

import utilities.DisposableOnEscFrame;

public class AdditionalVisualizationFrame extends DisposableOnEscFrame {
	private static final long serialVersionUID = -602532138880278167L;
	
	private final int HEIGHT = 500, WIDTH = 500;
	private GroupsVisualizer scheduleVisualization;
	
	public AdditionalVisualizationFrame(String title, GroupSelectorConfiguration selectorConfig, int order){
		setTitle(title);
		scheduleVisualization = new GroupsVisualizer(selectorConfig);
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
