package data.readers.fileReaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import logic.services.ClassroomService;
import logic.services.ConfigurationService;
import logic.services.StateService;

import data.DataValidation;
import data.persistentEntities.Classroom;
import data.persistentEntities.State;
import data.readers.DataReader;
import data.repository.Repository;


import exceptions.InvalidInputException;

public class FileClassRoomReader implements DataReader<Classroom> {

	private ClassroomService roomService;
	
	public FileClassRoomReader(State dataState){
		roomService = new ClassroomService(dataState);
	}
	
	public DataValidation<Repository<Classroom>> read() throws InvalidInputException {
		try {
			Scanner sc = new Scanner(new File(ConfigurationService.CLASSROOMS_FILENAME));
			LineReader reader = new LineReader();
			List<Classroom> allrooms = new LinkedList<Classroom>();
			while(sc.hasNext()){
				String line = sc.nextLine();
				reader.setLine(line, "#");
				Classroom room = new Classroom(reader.readString(), reader.readIntOrDefault(1000000000));
				room.setExternal(reader.readInt() == 1);
				allrooms.add(room);
			}
			roomService.addAll(allrooms);
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return new DataValidation<Repository<Classroom>>(StateService.getInstance().getCurrentState().classrooms);
	}
}
