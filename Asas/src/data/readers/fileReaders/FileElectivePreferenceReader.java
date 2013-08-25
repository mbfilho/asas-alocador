package data.readers.fileReaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

import logic.services.ElectiveClassService;
import logic.services.ElectivePreferencesService;
import logic.services.ProfessorService;
import logic.services.StateService;

import data.DataValidation;
import data.persistentEntities.ElectiveClass;
import data.persistentEntities.ElectiveClassPreferences;
import data.persistentEntities.SlotRange;
import data.readers.DataReader;
import data.repository.Repository;


import exceptions.InvalidInputException;

//TODO Esse código não deve existir
public class FileElectivePreferenceReader implements DataReader<ElectiveClassPreferences>{

	@SuppressWarnings("resource")
	public DataValidation<Repository<ElectiveClassPreferences>> read()	throws InvalidInputException {
		ElectivePreferencesService service = new ElectivePreferencesService();
		ElectiveClassService electiveService = new ElectiveClassService();
		ProfessorService profService = new ProfessorService();
		
		try {
			Scanner sc = new Scanner(new File("dummyElectives.in"));
			LineReader reader = new LineReader();
			
			while(sc.hasNext()){
				reader.setLine(sc.nextLine(), "#");
				ElectiveClass theClass = electiveService.getByName(reader.readString());
				ElectiveClassPreferences pref = new ElectiveClassPreferences(theClass);
				pref.setStudentCount(reader.readInt());
				int profCnt = reader.readInt();
				for(int i = 0; i < profCnt; ++i){
					String p = reader.readString();
					pref.addProfessor(profService.getByName(p));
					if(profService.getByName(p) == null) throw new RuntimeException();
				}
				int slotCnt = reader.readInt();
				for(int i = 0; i < slotCnt; ++i){
					int meetings = reader.readInt();
					Vector<SlotRange> horarios = new Vector<SlotRange>();
					
					for(int j = 0; j < meetings; ++j){
						int dia = reader.readInt(), ini = reader.readInt(), fim = reader.readInt();
						horarios.add(new SlotRange(dia, ini, fim, null));
					}
					pref.addSlotRange(horarios);
				}
				
				if(!service.existPreference(pref.getElectiveClass())) 
					service.add(pref);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return new DataValidation<Repository<ElectiveClassPreferences>>(StateService.getInstance().getCurrentState().electivePreferences);
	}
	

}
