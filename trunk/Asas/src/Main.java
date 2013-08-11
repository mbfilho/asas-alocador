import java.io.File;
import java.text.Collator;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import data.Repository;
import data.SimpleClassReader;
import data.SimpleClassRoomReader;
import data.SimpleElectivePreferenceReader;
import data.SimpleElectiveReader;
import data.SimpleProfessorReader;

import basic.Classroom;
import basic.DataValidation;
import basic.ElectiveClass;
import basic.ElectiveClassPreferences;
import basic.Professor;

import services.WarningGeneratorService;
import statePersistence.State;
import statePersistence.StateService;
import visualizer.Visualizer;
import basic.Class;

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
		
		/*
		File tmp = new File("config.asas");
		if(tmp.exists()) tmp.delete();
		State s = new State();
		s.setStateDescription("initial-draft-for-semester-2", "first draft para o 2 semestre", false);
		StateService.getInstance().saveNewState(s);
		StateService.getInstance().setCurrentState(s.description);
		s = StateService.getInstance().getCurrentState();
		
		DataValidation<Repository<Professor>> professors = new SimpleProfessorReader().read();
		DataValidation<Repository<Classroom>> classrooms = new SimpleClassRoomReader().read(); 
		DataValidation<Repository<Class>> classes = new SimpleClassReader().read();
		DataValidation<Repository<ElectiveClass>> electives = new SimpleElectiveReader().read();
		
		//this code should not exist!
		DataValidation<Repository<ElectiveClassPreferences>> prefs = new SimpleElectivePreferenceReader().read();
		System.out.println("Prefs: " + prefs.data.all().size());
		
		StateService.getInstance().save();
		//*/
		//StateService st = StateService.getInstance();
		//st.setCurrentState(st.allStates().get(0));
		
		WarningGeneratorService warningService = new WarningGeneratorService();
		new Visualizer(warningService);
			
	}
}
