package presentation.warnings;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import presentation.warnings.table.WarningTable;

import logic.dataUpdateSystem.CustomerType;
import logic.dataUpdateSystem.DataUpdateCentral;
import logic.dataUpdateSystem.Updatable;
import logic.dataUpdateSystem.UpdateDescription;
import logic.dto.WarningReport;
import logic.services.WarningGeneratorService;


import utilities.DisposableOnEscFrame;
import utilities.GuiUtil;

public class WarningsLayout extends DisposableOnEscFrame implements Updatable{
	private static final long serialVersionUID = -4926351789218160326L;
	private WarningGeneratorService service;
	private JTabbedPane pane;
	
	public WarningsLayout(){
		setTitle("Alertas.");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		service = new WarningGeneratorService();
		
		pane = new JTabbedPane();
		updateTabs();
		getContentPane().add(new JScrollPane(pane));
		setVisible(true);
		DataUpdateCentral.signIn(this, CustomerType.Gui);
	}

	public void dispose(){
		super.dispose();
		DataUpdateCentral.signOut(this);
	}
	
	private void updateTabs(){
		pane.removeAll();
		for(WarningReport report : service.getWarningReportList()){
			WarningTable table = new WarningTable(report.getAllWarnings());
			pane.addTab(report.getTitle(), new JScrollPane(table));
		}
	}
	
	public void onDataUpdate(UpdateDescription desc) {
		String selectedTitle = GuiUtil.getSelectedTabTitle(pane);
		updateTabs();
		GuiUtil.selectTabWithThisTitle(pane, selectedTitle);
	}

}
