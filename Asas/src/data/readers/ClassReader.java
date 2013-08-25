package data.readers;

import logic.services.ClassroomService;
import logic.services.ProfessorService;
import data.DataValidation;
import data.persistentEntities.Class;
import data.repository.Repository;
import exceptions.InvalidInputException;

public abstract class ClassReader implements DataReader<Class> {

	protected ProfessorService professors;
	protected ClassroomService classrooms;
	
	public ClassReader(){
		this.professors = new ProfessorService();
		this.classrooms = new ClassroomService();
	}
	
	public abstract DataValidation<Repository<Class>> read() throws InvalidInputException;

}
