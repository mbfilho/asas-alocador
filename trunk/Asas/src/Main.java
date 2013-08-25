import java.io.File;
import java.io.PrintWriter;
import java.text.Collator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import logic.dataUpdateSystem.DataUpdateCentral;
import logic.services.StateService;
import logic.services.WarningGeneratorService;


import data.DataValidation;
import data.configurations.ExcelPreferences;
import data.configurations.StateDescription;
import data.persistentEntities.Class;
import data.persistentEntities.Classroom;
import data.persistentEntities.ElectiveClass;
import data.persistentEntities.ElectiveClassPreferences;
import data.persistentEntities.Professor;
import data.persistentEntities.State;
import data.readers.fileReaders.FileClassReader;
import data.readers.fileReaders.FileClassRoomReader;
import data.readers.fileReaders.FileElectivePreferenceReader;
import data.readers.fileReaders.FileElectiveReader;
import data.readers.fileReaders.FileProfessorReader;
import data.repository.Repository;
import excelPreferences.gui.EditExcelPreferences;
import excelPreferences.gui.EditExcelPreferencesLayout;
import excelPreferences.gui.RoomListEditorInterface;




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
		
		/*
		File tmp = new File("config.asas");
		if(tmp.exists()) tmp.delete();
		State s = new State();
		s.setStateDescription("initial-draft-for-semester-2", "first draft para o 2 semestre", false);
		StateService.getInstance().saveNewState(s);
		StateService.getInstance().setCurrentState(s.description);
		
		DataValidation<Repository<Professor>> professors = new FileProfessorReader().read();
		DataValidation<Repository<Classroom>> classrooms = new FileClassRoomReader().read(); 
		DataValidation<Repository<Class>> classes = new FileClassReader().read();
		DataValidation<Repository<ElectiveClass>> electives = new FileElectiveReader().read();
		
		//this code should not exist!
		DataValidation<Repository<ElectiveClassPreferences>> prefs = new FileElectivePreferenceReader().read();
		System.out.println("Prefs: " + prefs.data.all().size());
		
		StateService.getInstance().save();
		//*/
		
		/*/
		File tmp = new File("config.asas");
		if(tmp.exists()) tmp.delete();
		List<String> errors = StateService.getInstance().switchToLoadedStateFromExcel(new ExcelPreferences(), new StateDescription("excel_generated", "Gerado do excel"));
		PrintWriter out = new PrintWriter("log.txt");
		for(String error : errors){
			out.println(error);
		}
		out.close();
		//*/
		WarningGeneratorService warningService = new WarningGeneratorService();
		new Visualizer(warningService);
	}
}
