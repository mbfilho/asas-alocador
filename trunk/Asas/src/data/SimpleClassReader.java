package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

import basic.Class;
import basic.Classroom;
import basic.DataValidation;
import basic.Professor;
import basic.SlotRange;
import exceptions.InvalidInputException;

public class SimpleClassReader extends ClassReader {
	private String fileName = "draftSemester2.in";
	private String[] st;
	int cnt;
	
	public SimpleClassReader(Repository<Professor> professors,Repository<Classroom> rooms) {
		super(professors, rooms);
	}

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
		Repository<Class> classes = new ClassRepository();
		try {
			Scanner sc = new Scanner(new File(fileName));
			while(sc.hasNext()){
				Class toRead = new Class();
				String record = sc.nextLine();
				st = record.split("#");
				cnt = 0;
				System.out.println(record);
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
						toRead.addProfessor(professors.get(profName));
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
					for(int j = desc[1]; j <= desc[2]; ++j) toRead.addSlot(new SlotRange(desc[0], j, classrooms.get(room)));
//					System.out.println(toRead.getName() + " > " + desc[0] + " " + desc[1] + " " + desc[2] + " size: " + toRead.getSlots().size());
				}
				toRead.setCh2(readInt());
				classes.addInOrder(toRead);
			}
		} catch (FileNotFoundException e) {
			errors.add("O arquivo \"" + fileName + "\" não foi encontrado.");
		}
		
		return new DataValidation(classes, errors);
	}

}

