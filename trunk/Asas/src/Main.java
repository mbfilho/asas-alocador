import java.io.File;
import java.text.Collator;

import data.Repository;
import data.SimpleClassReader;
import data.SimpleClassRoomReader;
import data.SimpleProfessorReader;
import data.SimpleRepository;

import basic.Classroom;
import basic.DataValidation;
import basic.Professor;

import statePersistence.State;
import statePersistence.StateService;
import validation.WarningService;
import visualizer.Visualizer;
import visualizer.VisualizerService;
import basic.Class;

public class Main {

	public static void main(String[] args) throws Exception {
		Collator.getInstance().setStrength(Collator.SECONDARY);
		
		//*
		File tmp = new File("config.asas");
		if(tmp.exists()) tmp.delete();
		State s = new State();
		s.professors = new SimpleRepository<Professor>();
		s.classrooms = new SimpleRepository<Classroom>();
		s.classes = new SimpleRepository<Class>();
		s.setStateDescription("initial-draft-for-semester-2", "first draft para o 2 semestre", false);
		StateService.getInstance().saveNewState(s);
		StateService.getInstance().setCurrentState(s.description);
		s = StateService.getInstance().getCurrentState();
		
		DataValidation<Repository<Professor>> professors = new SimpleProfessorReader().read();
		DataValidation<Repository<Classroom>> classrooms = new SimpleClassRoomReader().read();
		DataValidation<Repository<Class>> classes = new SimpleClassReader().read();
		StateService.getInstance().save();
		//*/
		StateService st = StateService.getInstance();
		st.setCurrentState(st.allStates().get(0));
		
		VisualizerService service = new VisualizerService();
		WarningService warningService = new WarningService();
		new Visualizer(service, warningService);
	}
}
