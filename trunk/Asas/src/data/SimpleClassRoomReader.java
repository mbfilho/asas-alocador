package data;

import java.util.Vector;

import services.ClassroomService;
import statePersistence.StateService;

import exceptions.InvalidInputException;
import basic.Classroom;
import basic.DataValidation;

public class SimpleClassRoomReader implements DataReader<Classroom> {

	public DataValidation<Repository<Classroom>> read() throws InvalidInputException {
		ClassroomService service = new ClassroomService();
		service.add(new Classroom("Área II"));
		service.add(new Classroom("CTG"));
		for(int i = 1; i < 10; ++i) service.add(new Classroom("" + i));
		for(int i = 90; i < 95; ++i) service.add(new Classroom("" + i));
		
		return new DataValidation(StateService.getInstance().getCurrentState().classrooms);
	}
}
