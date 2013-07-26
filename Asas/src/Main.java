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
import visualizer.Schedule;
import visualizer.Visualizer;
import visualizer.VisualizerService;
import basic.Class;

public class Main {

	public static void main(String[] args) throws Exception {
		/*
		DataValidation<Repository<Professor>> professors = new SimpleProfessorReader().read();
		DataValidation<Repository<Classroom>> classrooms = new SimpleClassRoomReader().read();
		DataValidation<Repository<Class>> classes = new SimpleClassReader(professors.data, classrooms.data).read();
		System.out.println(professors.data.all().size() + " " + classes.data.all().size() +  " " + classrooms.data.all().size());
		State s = new State();
		s.classes = classes.data;
		s.classrooms = classrooms.data;
		s.professors = professors.data;
		s.setStateDescription("initial-draft-for-semester-2", "first draft para o 2 semestre", true);
		StateService.getInstance().saveNewState(s);
		//*/
		VisualizerService service = new VisualizerService();
		WarningService warningService = new WarningService();
		new Visualizer(service, warningService);
	}
}
