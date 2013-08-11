package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import services.ClassroomService;
import statePersistence.StateService;

import exceptions.InvalidInputException;
import basic.Classroom;
import basic.DataValidation;

public class SimpleClassRoomReader implements DataReader<Classroom> {

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
