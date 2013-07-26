package data;

import exceptions.InvalidInputException;
import basic.Class;
import basic.Classroom;
import basic.DataValidation;
import basic.Professor;

public abstract class ClassReader implements DataReader<Class> {

	protected Repository<Professor> professors;
	protected Repository<Classroom> classrooms;
	
	public ClassReader(Repository<Professor> professors, Repository<Classroom> rooms){
		this.professors = professors;
		this.classrooms = rooms;
	}
	
	public abstract DataValidation<Repository<Class>> read() throws InvalidInputException;

}
