package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import basic.DataValidation;
import basic.Professor;

import exceptions.InvalidInputException;

public class SimpleProfessorReader implements DataReader<Professor> {
	private String fileName = "professors.in";

	public DataValidation<Repository<Professor>> read() throws InvalidInputException {
		LineReader reader = new LineReader();
		SimpleRepository<Professor> profs = new SimpleRepository<Professor>();
		try {
			Scanner sc = new Scanner(new File(fileName));
			while(sc.hasNext()){
				reader.setLine(sc.nextLine(), "#");
				String name = reader.readString(), email = reader.readString(), cargo = reader.readString();
				String dpto = reader.readString();
				boolean temp = reader.readInt() == 1, away = reader.readInt() == 1;
				
				profs.addInOrder(new Professor(name, email, cargo, dpto, temp, away));
			}
			sc.close();
		} catch (FileNotFoundException e) {
			throw new InvalidInputException("Arquivo \"" + fileName + "\" não encontrado.");
		}
			
		return new DataValidation<Repository<Professor>>(profs);
	}
}
