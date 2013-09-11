import java.text.Collator;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import logic.services.WarningGeneratorService;


import visualizer.Visualizer;

public class Main {

	private static void setLookAndFeel(){
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}	
	}
	
	public static void main(String[] args) throws Exception {
		Collator.getInstance().setStrength(Collator.SECONDARY);
		setLookAndFeel();
		
		WarningGeneratorService warningService = new WarningGeneratorService();
		new Visualizer(warningService);
	}
}
