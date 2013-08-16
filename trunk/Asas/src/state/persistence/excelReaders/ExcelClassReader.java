package state.persistence.excelReaders;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import preferences.ExcelPreferences;
import basic.Class;
import basic.Classroom;
import basic.DataValidation;
import basic.Professor;
import basic.SlotRange;
import exceptions.InvalidInputException;
import repository.Repository;
import services.ClassService;
import services.ClassroomService;
import services.ProfessorService;
import state.persistence.ClassReader;
import utilities.Constants;
import utilities.Pair;
import utilities.StringUtil;

public class ExcelClassReader extends ClassReader{

	private WorkbookReader reader;
	private ExcelPreferences excelPrefs; 
	private ClassService service;
	private ProfessorService profService;
	private ClassroomService roomService;
	private List<String> errors;
	
	public ExcelClassReader(ExcelPreferences prefs){
		excelPrefs = prefs;
		service = new ClassService();
		errors = new LinkedList<String>();
		profService = new ProfessorService();
		roomService = new ClassroomService();
	}
	
	public DataValidation<Repository<Class>> read()	throws InvalidInputException {
		try {
			openWorkbook();
			ignoreUntil(excelPrefs.getGraduationRequiredMarker());
			readGraduationRequired();
			
			return null;
		} catch (IOException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}
	
	private void openWorkbook() throws IOException{
		reader = new WorkbookReader(excelPrefs.getFileLocation(), excelPrefs.getClassesSheet());
	}
	
	private void ignoreUntil(String expected){
		while(!reader.readString().equals(expected))
			ignoreRow();
		ignoreRow();
	}
	
	private void ignoreRow(){
		reader.goToNextRow();
	}
	
	private void readGraduationRequired() {
		while(true){
			String name = reader.readString();
			if(name.equals(excelPrefs.getRequiredByOtherCentersMarker())) break;
			Class theClass = readClass(name);
			ignoreRow();
			if(theClass == null) continue;
			service.add(theClass);
		}
	}
	
	private Class readClass(String name){
		Class theClass = new Class();
		theClass.setName(name);
		boolean includeClass = reader.readString().equals(excelPrefs.getOkMarker());
		if(!includeClass){
			ignoreRow();
			return null;
		}
		
		readProfsToClass(theClass);
		
		readChToClass(theClass);
		
		//TODO: tratar numero de estudantes na pre Matricula
		reader.readInt(0);
		
		//TODO: tratar número de estudantes no semestre anterior
		reader.readInt(0);

		List<String> classrooms = readClassRoomNames(theClass);
		
		readSemesterToClass(theClass);
		
		//TODO: tratar disciplina conjunta
		reader.readString();

		readCodeToClass(theClass);
		
		//TODO: tratar nome efetivo da disciplina
		reader.readValue();
		
		readCourseToClass(theClass);
		
		readSlotsToClass(classrooms, theClass);
		
		theClass.setCh2(reader.readInt(0));
		
		return theClass;
	}

	private void readProfsToClass(Class theClass) {
		for(int i = 0; i < excelPrefs.getProfessorCount(); ++i){
			String professorName = reader.readString();
			if(StringUtil.isNullOrEmpty(professorName)) continue;
			
			Professor prof = profService.getByName(professorName);
			if(prof != null) 
				theClass.addProfessor(prof);
			else
				errors.add(String.format("Não foi possível encontrar o professor '%s' indicado na disciplina '%s'", 
						professorName, theClass.getName()));
		}
	}

	private void readChToClass(Class theClass) {
		double ch = reader.readDouble();
		if(Double.isNaN(ch))
			errors.add(String.format("Turma '%s' não possui carga horária", theClass.getName()));
		else
			theClass.setCh(ch);
	}
	
	private List<String> readClassRoomNames(Class theClass) {
		String roomCode = reader.readValue();
		List<String> classrooms = new LinkedList<String>();
		if(StringUtil.isNullOrEmpty(roomCode)) return classrooms;
		
		if(excelPrefs.hasMappedRooms(roomCode))
			classrooms = excelPrefs.getMappedRooms(roomCode);
		else{
			if(roomCode.contains("/")){
				String[] splitted = roomCode.split("/");
				classrooms = new LinkedList<String>();
				for(String item : splitted) 
					classrooms.add(item);
			}else{
				errors.add(String.format("Turma '%s' com código de sala desconhecido: '%s'",
						theClass.getName(), roomCode));
			}
		}
		return classrooms;
	}
	
	private void readSemesterToClass(Class theClass) {
		String semester = reader.readValue();
		if(StringUtil.isNullOrEmpty(semester)) return;
		
		if(semester.contains(excelPrefs.getSemesterSeparator())){
			String[] ccEC = semester.split(excelPrefs.getSemesterSeparator());
			if(theClass.getName().contains(excelPrefs.getCCMarker())) ccEC[1] = null;
			if(theClass.getName().contains(excelPrefs.getECMarker())) ccEC[0] = null;
			
			theClass.setCcSemester(StringUtil.isNullOrEmpty(ccEC[0]) ? -1 : Integer.parseInt(ccEC[0]));
			theClass.setEcSemester(StringUtil.isNullOrEmpty(ccEC[1]) ? -1 : Integer.parseInt(ccEC[1]));
		}else
			errors.add(String.format("Turma '%s' com semestre inválido: '%s'", theClass.getName(), semester));
	}
	
	private void readCodeToClass(Class theClass) {
		String classCode = reader.readString();
		if(StringUtil.isNullOrEmpty(classCode) == false)
			theClass.setCode(classCode);		
	}
	
	private void readCourseToClass(Class theClass) {
		String course = reader.readValue();
		if(StringUtil.isNullOrEmpty(course)) return;
		
		theClass.setCourse(course);
	}
	
	private void readSlotsToClass(List<String> classrooms, Class theClass) {
		for(int i = 0; i < excelPrefs.getSlotCount(); ++i){
			String slot = reader.readString();
			Pair<String, String> dayAndHour = divideIntoSlotDayAndHourRange(slot);
			if(dayAndHour == null){
				errors.add(String.format("Não foi possível ler o horário '%s' da disciplina '%s'", slot, theClass.getName()));
				continue;
			}
			
			String day = dayAndHour.first, hourRange = dayAndHour.second;
			Pair<String, String> startAndEndHour = divideIntoStartAndEndHour(hourRange);
			if(startAndEndHour == null){
				errors.add(String.format("Não foi possível ler o horário '%s' da disciplina '%s'", slot, theClass.getName()));
				continue;
			}
			int startSlotNumber = getSlotNumberOfIniHour(startAndEndHour.first);
			int endSlotNumber = getSlotNumberOfEndHour(startAndEndHour.second);
			int dayIndex = getDayIndex(day);
		
			if(dayIndex == -1 || startSlotNumber == -1 || endSlotNumber == -1){
				errors.add(String.format("Não foi possível ler o horário '%s' da disciplina '%s'", slot, theClass.getName()));
				continue;
			}
			
			Classroom theRoom = null;
			if(!classrooms.isEmpty())
				theRoom = roomService.getByName(classrooms.get(Math.min(i, classrooms.size() - 1)));
			theClass.addSlot(new SlotRange(dayIndex, startSlotNumber, endSlotNumber, theRoom));
		}
		
	}
	
	private Pair<String, String> divideIntoStartAndEndHour(String hourRange) {
		if(StringUtil.isNullOrEmpty(hourRange)) return null;
		if(!hourRange.contains(excelPrefs.getSlotHourSeparator())) return null;
		String stAndEnd[] = hourRange.split(excelPrefs.getSlotHourSeparator());
		
		return new Pair<String, String>(stAndEnd[0], stAndEnd[1]);
	}

	/**
	 * 	Divide uma string representando um intervalo de tempo (SlotRange)
	 *  e retorna um par com o dia especificado e o intervalo de horas.
	 *  
	 * @param slot - o Slot a ser dividido
	 * @return O par (dia, intervalo de horas). Caso não seja possível fazer essa separação,
	 * retorna o valor null;
	 */
	private Pair<String, String> divideIntoSlotDayAndHourRange(String slot){
		if(StringUtil.isNullOrEmpty(slot)) return null;
		String daySeparator = excelPrefs.getSlotDaySeparator();
		if(!slot.contains(daySeparator)) daySeparator = " ";
		if(!slot.contains(daySeparator)) return null;
		String dayAndRange[] = slot.split(daySeparator);
		if(dayAndRange.length != 2) return null;
		return new Pair<String, String>(dayAndRange[0], dayAndRange[1]);
	}
	
	//recebe hh:mm
	private int getSlotNumberOfIniHour(String ini) {
		if(ini.equals("18:50"))
			ini = "19";
		String  hh = ini.split(":")[0];
		return SlotRange.getSlotNumberStartingWithThisHour(hh);
	}

	//recebe hh:mm
	private int getSlotNumberOfEndHour(String end) {
		if(end.equals("18:40")) end = "19";
		else if(end.equals("18:50")) end = "19";
		else if(end.equals("20:30")) end = "21";
		else if(end.equals("19:40")) end = "20";
		String[] hhmm = end.split(":");
		
		if(hhmm.length < 2 || Integer.parseInt(hhmm[1]) == 0)
			return SlotRange.getSlotNumberStartingWithThisHour(hhmm[0]);
		else
			return SlotRange.getSlotNumberEndingWithThisHour(hhmm[0]);
	}

	private int getDayIndex(String day) {
		if(StringUtil.isNullOrEmpty(day)) return -1;
		
		day = day.toLowerCase();
		for(int i = 0; i < Constants.DAYS; ++i){
			if(Constants.days[i].toLowerCase().startsWith(day))
				return i;
		}
		return -1;
	}

}
