package data.readers.fileReaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import logic.services.ElectiveClassService;
import logic.services.StateService;

import data.DataValidation;
import data.persistentEntities.ElectiveClass;
import data.readers.DataReader;
import data.repository.Repository;


import exceptions.InvalidInputException;

public class FileElectiveReader implements DataReader<ElectiveClass>{
	
	private ElectiveClassService service;
	
	public FileElectiveReader(){
		service = new ElectiveClassService();
	}
	
	public DataValidation<Repository<ElectiveClass>> read()	throws InvalidInputException {
		try {
			LineReader reader = new LineReader();
			Scanner sc = new Scanner(new File("electives.in"));
			
			while(sc.hasNext()){
				reader.setLine(sc.nextLine(), "#");
				ElectiveClass read = new ElectiveClass();
				read.setName(reader.readString());
				read.setCh(reader.readDouble());
				read.setAlias(reader.readString());
				read.setCode(reader.readString());
				read.setCourse(reader.readString());
				read.setCh2(reader.readInt());
				
				service.add(read);
			}
			System.out.println("Eletivas: " + service.all().size());
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return new DataValidation<Repository<ElectiveClass>>(StateService.getInstance().getCurrentState().elective);
	}


}
