package logic.services;

import java.io.IOException;
import java.util.List;

import logic.dto.WorkloadReport;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import data.configurations.ExcelPreferences;
import data.configurations.StateDescription;
import data.persistentEntities.State;
import data.readers.excelReaders.ExcelClassReader;
import data.readers.excelReaders.ExcelProfessorReader;
import data.readers.excelReaders.WorkbookReader;
import data.readers.fileReaders.FileClassRoomReader;
import exceptions.InvalidInputException;

public class ExcelStateLoader {
	private State theNewState;
	private List<String> errors;
	
	public ExcelStateLoader(ExcelPreferences prefs, StateDescription desc){
		theNewState = new State(desc);
		theNewState.setExcelPreferences(prefs);
	}
	
	public State loadState() throws InvalidInputException, InvalidFormatException, IOException{
		WorkbookReader excelReader = new WorkbookReader(theNewState.excelPrefs.getFileLocation());
		ExcelClassReader classReader = new ExcelClassReader(theNewState, excelReader, theNewState.excelPrefs.getClassesSheet());
		new ExcelProfessorReader(theNewState, excelReader).read();
		new FileClassRoomReader(theNewState).read();
		errors = classReader.read().validation;
		
		loadWorkloadFromPreviousSemester(excelReader);
		
		return theNewState;
	}
	
	private void loadWorkloadFromPreviousSemester(WorkbookReader excelReader) throws InvalidInputException {
		State lastSemester = loadLastSemesterData(excelReader);
		ProfessorWorkLoadService workloadService = new ProfessorWorkLoadService(lastSemester);
		ProfessorService profService = new ProfessorService(theNewState);
		
		for(WorkloadReport load : workloadService.calculateProfessorWorkload()){
			profService.setPreviousLoad(load.professor.getName(), load.workload);
		}
	}
	
	private State loadLastSemesterData(WorkbookReader excelReader) throws InvalidInputException{
		State lastSemester = new State();
		lastSemester.classrooms = theNewState.classrooms;
		lastSemester.professors = theNewState.professors;
		lastSemester.excelPrefs = theNewState.excelPrefs;
		new ExcelClassReader(lastSemester, excelReader, excelReader.getPreviousSheetName()).read();
		return lastSemester;
	}

	public List<String> getErrors(){
		return errors;
	}
}
