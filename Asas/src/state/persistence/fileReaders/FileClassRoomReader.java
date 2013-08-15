package state.persistence.fileReaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import repository.Repository;
import services.ClassroomService;
import services.StateService;
import state.persistence.DataReader;

import exceptions.InvalidInputException;
import basic.Classroom;
import basic.DataValidation;

public class FileClassRoomReader implements DataReader<Classroom> {

	public DataValidation<Repository<Classroom>> read() throws InvalidInputException {
		ClassroomService service = new ClassroomService();
		try {
			Scanner sc = new Scanner(new File("classrooms.in"));
			LineReader reader = new LineReader();
			
			while(sc.hasNext()){
				String line = sc.nextLine();
				reader.setLine(line, "#");
				System.out.println(line + "|" + line.split("#").length + "|");
				Classroom room = new Classroom(reader.readString(), reader.readIntOrDefault(1000000000));
				service.add(room);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return new DataValidation<Repository<Classroom>>(StateService.getInstance().getCurrentState().classrooms);
	}
}
