package state.persistence.fileReaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


import repository.Repository;
import services.ElectiveClassService;
import services.StateService;
import state.persistence.DataReader;
import basic.DataValidation;
import basic.ElectiveClass;
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
