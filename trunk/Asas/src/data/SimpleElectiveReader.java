package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import services.ElectiveClassService;
import statePersistence.StateService;
import basic.DataValidation;
import basic.ElectiveClass;
import exceptions.InvalidInputException;

public class SimpleElectiveReader implements DataReader<ElectiveClass>{
	
	private ElectiveClassService service;
	
	public SimpleElectiveReader(){
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
