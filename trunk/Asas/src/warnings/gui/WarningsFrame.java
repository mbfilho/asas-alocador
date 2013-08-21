package warnings.gui;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import dataUpdateSystem.CustomerType;
import dataUpdateSystem.RegistrationCentral;
import dataUpdateSystem.Updatable;
import dataUpdateSystem.UpdateDescription;

import services.WarningGeneratorService;
import utilities.DisposableOnEscFrame;
import utilities.GuiUtil;
import warnings.WarningReport;
import warnings.gui.table.WarningTable;

public class WarningsFrame extends DisposableOnEscFrame implements Updatable{
	private static final long serialVersionUID = -4926351789218160326L;
	private WarningGeneratorService service;
	private JTabbedPane pane;
	
	public WarningsFrame(){
		setTitle("Alertas.");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		service = new WarningGeneratorService();
		
		pane = new JTabbedPane();
		updateTabs();
		getContentPane().add(new JScrollPane(pane));
		setVisible(true);
		RegistrationCentral.signIn(this, CustomerType.Gui);
	}

	public void dispose(){
		super.dispose();
		RegistrationCentral.signOut(this);
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
