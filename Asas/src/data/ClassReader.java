package data;

import services.ClassroomService;
import services.ProfessorService;
import exceptions.InvalidInputException;
import basic.Class;
import basic.Classroom;
import basic.DataValidation;
import basic.Professor;

public abstract class ClassReader implements DataReader<Class> {

	protected ProfessorService professors;
	protected ClassroomService classrooms;
	
	public ClassReader(){
		this.professors = new ProfessorService();
		this.classrooms = new ClassroomService();
	}
	
	public abstract DataValidation<Repository<Class>> read() throws InvalidInputException;

}
