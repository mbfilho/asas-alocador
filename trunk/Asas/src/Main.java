import java.io.File;
import java.text.Collator;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;



import basic.Classroom;
import basic.DataValidation;
import basic.ElectiveClass;
import basic.ElectiveClassPreferences;
import basic.Professor;

import repository.Repository;
import services.StateService;
import services.WarningGeneratorService;
import state.State;
import state.persistence.fileReaders.FileClassReader;
import state.persistence.fileReaders.FileClassRoomReader;
import state.persistence.fileReaders.FileElectivePreferenceReader;
import state.persistence.fileReaders.FileElectiveReader;
import state.persistence.fileReaders.FileProfessorReader;
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
		
		DataValidation<Repository<Professor>> professors = new FileProfessorReader().read();
		DataValidation<Repository<Classroom>> classrooms = new FileClassRoomReader().read(); 
		DataValidation<Repository<Class>> classes = new FileClassReader().read();
		DataValidation<Repository<ElectiveClass>> electives = new FileElectiveReader().read();
		
		//this code should not exist!
		DataValidation<Repository<ElectiveClassPreferences>> prefs = new FileElectivePreferenceReader().read();
		System.out.println("Prefs: " + prefs.data.all().size());
		
		StateService.getInstance().save();
		//*/
		//StateService st = StateService.getInstance();
		//st.setCurrentState(st.allStates().get(0));
		
		WarningGeneratorService warningService = new WarningGeneratorService();
		new Visualizer(warningService);
			
	}
}
