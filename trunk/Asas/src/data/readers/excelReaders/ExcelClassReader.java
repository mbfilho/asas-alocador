package data.readers.excelReaders;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import logic.services.ClassService;
import logic.services.ClassroomService;
import logic.services.ProfessorService;

import data.DataValidation;
import data.configurations.ExcelPreferences;
import data.persistentEntities.Class;
import data.persistentEntities.Classroom;
import data.persistentEntities.Professor;
import data.persistentEntities.SlotRange;
import data.readers.ClassReader;
import data.repository.Repository;

import exceptions.InvalidInputException;
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
	
	public ExcelClassReader(ExcelPreferences prefs, WorkbookReader excelReader){
		excelPrefs = prefs;
		service = new ClassService();
		errors = new LinkedList<String>();
		profService = new ProfessorService();
		roomService = new ClassroomService();
		reader = excelReader;
	}
	
	public DataValidation<Repository<Class>> read()	throws InvalidInputException {
		reader.changeSheet(excelPrefs.getClassesSheet());
		
		ignoreUntil(excelPrefs.getRequiredByGraduationMarker());
		
		if(excelPrefs.isRequiredByGraduationEnabled()){
			readGraduationRequired();
			ignoreRow();
		}else ignoreUntil(excelPrefs.getRequiredByOtherCentersMarker());
		
		if(excelPrefs.isRequiredByOtherCentersEnabled()){
			readRequiredByOtherCenters();
			ignoreRow();
		}else ignoreUntil(excelPrefs.getElectivesFromGraduationMaker());

		if(excelPrefs.isElectivesFromGraduationEnabled()){
			readElectivesFromGraduation();
			ignoreRow();
		}else ignoreUntil(excelPrefs.getRequiredByPosGraduationMarker());
		
		if(excelPrefs.isRequiredByPosGraduationEnabled()){
			readRequiredByPosGraduation();
			ignoreRow();
		}else ignoreUntil(excelPrefs.getElectivesFromPosGraduationMarker());
		
		if(excelPrefs.isElectivesFromPosGraduationEnabled())
			readElectivesFromPosGraduation();
		
		DataValidation<Repository<Class>> x = new DataValidation<Repository<Class>>();
		x.validation = new Vector<String>(errors);
		return x;
	}
	
	private void ignoreUntil(String expected){
		while(!reader.readString().equals(expected))
			ignoreRow();
		ignoreRow();
	}
	
	private void ignoreRow(){
		reader.goToNextRow();
	}
	
	private void readClassesUntil(String marker){
		while(true){
			String name = reader.peekString();
			if(name.equals(marker)) break;
			Class theClass = readClass();
			ignoreRow();
			if(theClass == null) continue;
			service.add(theClass);
		}
	}
	
	private void readGraduationRequired() {
		readClassesUntil(excelPrefs.getRequiredByOtherCentersMarker());
	}
	
	private void readRequiredByOtherCenters(){
		readClassesUntil(excelPrefs.getElectivesFromGraduationMaker());
	}
	
	//TODO: deve ser feito de maneira diferente, não?
	private void readElectivesFromGraduation(){
		readClassesUntil(excelPrefs.getRequiredByPosGraduationMarker());
	}
	
	private void readRequiredByPosGraduation(){
		readClassesUntil(excelPrefs.getElectivesFromPosGraduationMarker());
	}
	
	private void readElectivesFromPosGraduation(){
		readClassesUntil(excelPrefs.getEndOfFileMarker());
	}
	
	
	private Class readClass(){
		Class theClass = new Class();
		theClass.setName(reader.readString());
		
		String ok = reader.readString();
		boolean includeClass = ok.equalsIgnoreCase(ExcelPreferences.OK_MARKER);

		if(!includeClass)
			return null;
		
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
		reader.readString();
		
		readCourseToClass(theClass);

		readSlotsToClass(classrooms, theClass);
		
		theClass.setCh2(reader.readInt(0));
		
		return theClass;
	}

	private void readProfsToClass(Class theClass) {
		for(int i = 0; i < excelPrefs.getProfessorCount(); ++i){
			String professorName = reader.readString();
			if(StringUtil.isNullOrEmpty(professorName) || professorName.equals("??")) continue;
			
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
		String roomCode = reader.readString();
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
		String semester = reader.readString() + " ";
		if(StringUtil.isNullOrEmpty(semester)) return;

		if(semester.contains(excelPrefs.getSemesterSeparator())){
			String[] ccEC = semester.split(excelPrefs.getSemesterSeparator());
			if(theClass.getName().contains(ExcelPreferences.CC_MARKER)) ccEC[1] = "";
			if(theClass.getName().contains(ExcelPreferences.EC_MARKER)) ccEC[0] = "";
			
			theClass.setCcSemester(StringUtil.sanitize(ccEC[0]));
			theClass.setEcSemester(StringUtil.sanitize(ccEC[1]));
		}else
			errors.add(String.format("Turma '%s' com semestre inválido: '%s'", theClass.getName(), semester));
	}
	
	private void readCodeToClass(Class theClass) {
		String classCode = reader.readString();
		if(StringUtil.isNullOrEmpty(classCode) == false)
			theClass.setCode(classCode);		
	}
	
	private void readCourseToClass(Class theClass) {
		String course = reader.readString();
		if(StringUtil.isNullOrEmpty(course)) return;
		
		theClass.setCourse(course);
	}
	
	private void readSlotsToClass(List<String> classrooms, Class theClass) {
		for(int i = 0; i < excelPrefs.getSlotCount(); ++i){
			String slot = reader.readString();
			if(StringUtil.isNullOrEmpty(slot)) continue;
			
			Pair<String, String> dayAndHour = divideIntoSlotDayAndHourRange(slot);
			if(dayAndHour == null){
				errors.add(String.format("Não foi possível ler o horário (%d) '%s' da disciplina '%s'", i, slot, theClass.getName()));
				continue;
			}
			
			String day = dayAndHour.first, hourRange = dayAndHour.second;
			Pair<String, String> startAndEndHour = divideIntoStartAndEndHour(hourRange);
			if(startAndEndHour == null){
				errors.add(String.format("Não foi possível ler o horário (%d) '%s' da disciplina '%s'", i, slot, theClass.getName()));
				continue;
			}
			int startSlotNumber = getSlotNumberOfIniHour(startAndEndHour.first);
			int endSlotNumber = getSlotNumberOfEndHour(startAndEndHour.second);
			int dayIndex = getDayIndex(day);
		
			if(dayIndex == -1 || startSlotNumber == -1 || endSlotNumber == -1){
				errors.add(String.format("Não foi possível ler o horário (%d) '%s' da disciplina '%s'", i, slot, theClass.getName()));
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
		if(!hourRange.contains(ExcelPreferences.SLOT_HOURS_SEPARATOR)) return null;
		String stAndEnd[] = hourRange.split(ExcelPreferences.SLOT_HOURS_SEPARATOR);
		
		return new Pair<String, String>(StringUtil.sanitize(stAndEnd[0]), StringUtil.sanitize(stAndEnd[1]));
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
		String daySeparator = ExcelPreferences.SLOT_DAY_SEPARATOR;
		if(!slot.contains(daySeparator)) daySeparator = " ";
		if(!slot.contains(daySeparator)) return null;
		String dayAndRange[] = slot.split(daySeparator);
		if(dayAndRange.length != 2) return null;
		return new Pair<String, String>(StringUtil.sanitize(dayAndRange[0]), StringUtil.sanitize(dayAndRange[1]));
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
			return SlotRange.getSlotNumberEndingWithThisHour(hhmm[0]);
		else
			return SlotRange.getSlotNumberStartingWithThisHour(hhmm[0]);
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
