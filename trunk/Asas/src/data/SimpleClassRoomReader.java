package data;

import java.util.Vector;

import exceptions.InvalidInputException;
import basic.Classroom;
import basic.DataValidation;

public class SimpleClassRoomReader implements DataReader<Classroom> {

	public DataValidation<Repository<Classroom>> read() throws InvalidInputException {
		Vector<Classroom> classes = new Vector<Classroom>();
		classes.add(new Classroom("Área II"));
		classes.add(new Classroom("CTG"));
		for(int i = 1; i < 10; ++i) classes.add(new Classroom("" + i));
		for(int i = 90; i < 95; ++i) classes.add(new Classroom("" + i));
		
		return new DataValidation(new SimpleRepository<Classroom>(classes));
	}
}
