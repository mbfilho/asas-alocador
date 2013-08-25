package data.readers.fileReaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

import logic.services.ClassService;
import logic.services.StateService;

import data.DataValidation;
import data.persistentEntities.Class;
import data.persistentEntities.SlotRange;
import data.readers.ClassReader;
import data.repository.Repository;



import exceptions.InvalidInputException;

public class FileClassReader extends ClassReader {
	private String fileName = "draftSemester2.in";
	private String[] st;
	int cnt;
	
	private String readString(){
		return st[cnt++];
	}
	
	private int readInt(){
		return Integer.parseInt(readString());
	}
	
	private double readDouble(){
		return Double.parseDouble(readString().replace(",", "."));
	}
	
	public DataValidation<Repository<Class>> read() throws InvalidInputException {
		Vector<String> errors = new Vector<String>();
		ClassService service = new ClassService();
		
		try {
			Scanner sc = new Scanner(new File(fileName));
			while(sc.hasNext()){
				Class toRead = new Class();
				String record = sc.nextLine();
				st = record.split("#");
				cnt = 0;
				/*
				for(String tk : st){
					System.out.print("|"+tk+"|");
				}
				System.out.println();//*/
				//st = new StringTokenizer(record,"#");
				toRead.setName(readString());
				
				int profsCount = readInt();
				for(int i = 0; i < profsCount; ++i){
					String profName = readString();
					if(!professors.exists(profName))
						errors.add("Na disciplina: " + toRead.getName() + " professor " + profName + " não cadastrado.");
					else
						toRead.addProfessor(professors.getByName(profName));
				}
				toRead.setCh(readDouble());
			//	System.out.println(toRead.getName() + " | P. count: " + toRead.getProfessors().size() + " Ch: " + toRead.getCh() + " room: " + toRead.getClassroom());
				toRead.setCcSemester(readInt());
				toRead.setEcSemester(readInt());
				toRead.setCode(readString());
				toRead.setCourse(readString());
				int slotsCount = readInt();
				if(slotsCount == 0)	errors.add("Disciplina: " + toRead.getName() + " não possui slots.");
				for(int i = 0; i < slotsCount; ++i){
					int desc[] = new int[3];
					for(int j = 0; j < 3; ++j) desc[j] = Integer.parseInt(readString());
					String room = readString();
					//System.out.println(">> " + room + " x " + classrooms.getByName(room));
					System.out.println("|" + room +  "|");
					toRead.addSlot(new SlotRange(desc[0], desc[1], desc[2], classrooms.getByName(room)));
				}
				toRead.setCh2(readInt());
				service.add(toRead);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			errors.add("O arquivo \"" + fileName + "\" não foi encontrado.");
		}
		
		return new DataValidation<Repository<Class>>(StateService.getInstance().getCurrentState().classes, errors);
	}

}

