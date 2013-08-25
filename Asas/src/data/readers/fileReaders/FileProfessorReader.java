package data.readers.fileReaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import logic.services.ProfessorService;
import logic.services.StateService;

import data.DataValidation;
import data.persistentEntities.Professor;
import data.readers.DataReader;
import data.repository.Repository;




import exceptions.InvalidInputException;

public class FileProfessorReader implements DataReader<Professor> {
	private String fileName = "professors.in";

	public DataValidation<Repository<Professor>> read() throws InvalidInputException {
		LineReader reader = new LineReader();
		ProfessorService professorService = new ProfessorService();
		try {
			Scanner sc = new Scanner(new File(fileName));
			while(sc.hasNext()){
				String line = sc.nextLine();
				reader.setLine(line, "#");
				//System.out.println(line + " " + line.split("#").length);
				String name = reader.readString(), email = reader.readString(), cargo = reader.readString();
				String dpto = reader.readString();
				boolean temp = reader.readInt() == 1, away = reader.readInt() == 1;
				
				professorService.add(new Professor(name, email, cargo, dpto, temp, away));
			}
			sc.close();
		} catch (FileNotFoundException e) {
			throw new InvalidInputException("Arquivo \"" + fileName + "\" não encontrado.");
		}
			
		return new DataValidation<Repository<Professor>>(StateService.getInstance().getCurrentState().professors);
	}
}
