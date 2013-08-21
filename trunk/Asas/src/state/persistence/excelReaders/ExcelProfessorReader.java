package state.persistence.excelReaders;

import java.io.IOException;
import java.util.Vector;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import excelPreferences.ExcelPreferences;
import exceptions.InvalidInputException;
import repository.Repository;
import services.ProfessorService;
import services.StateService;
import state.persistence.DataReader;
import basic.DataValidation;
import basic.Professor;

public class ExcelProfessorReader implements DataReader<Professor> {
	private final String header = "Nome";
	
	private WorkbookReader reader;
	private ProfessorService profService;
	private ExcelPreferences prefs;
	//Nome	E-mail	Cargo	Depto

	public ExcelProfessorReader(ExcelPreferences prefs, WorkbookReader excelReader){
		this.reader = excelReader;
		this.prefs = prefs;
		profService = new ProfessorService();
	}
	
	private boolean isTemporaryCargo(String cargo){
		return cargo.equals(prefs.getTemporaryProfessorMarker());
	}
	
	private boolean isAwayCargo(String cargo){
		return cargo.equals(prefs.getAwayProfessorMarker());
	}
	
	private boolean isValidCargo(String cargo){
		if(isTemporaryCargo(cargo) || isAwayCargo(cargo)) return false;
		return true;
	}
	
	public DataValidation<Repository<Professor>> read()	throws InvalidInputException {
		reader.changeSheet(prefs.getProfessorsSheet());
		while(reader.peekString().equals(header) == false) reader.goToNextRow();
		
		while(true){
			if(reader.hasNextRow()) reader.goToNextRow();
			else break;
			String name = reader.readString();
			if(name.equals("??")) continue;
			Professor prof = new Professor();
			prof.setName(name);
			prof.setEmail(reader.readString());
			String cargo = reader.readString();
			if(isValidCargo(cargo))	prof.setCargo(cargo);
			prof.setTemporary(isTemporaryCargo(cargo));
			prof.setAway(isAwayCargo(cargo));
			prof.setDpto(reader.readString());
			profService.add(prof);
		}
		
		DataValidation<Repository<Professor>> result = new DataValidation<Repository<Professor>>();
		result.data = StateService.getInstance().getCurrentState().professors;
		result.validation = new Vector<String>();
		
		return result;
	}

}
