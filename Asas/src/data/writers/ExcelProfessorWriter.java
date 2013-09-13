package data.writers;

import org.apache.poi.ss.usermodel.Workbook;

import data.configurations.ExcelPreferences;
import data.persistentEntities.Professor;
import exceptions.WritingException;

public class ExcelProfessorWriter extends ExcelWriter<Professor>{

	public ExcelProfessorWriter(Workbook workbook, ExcelPreferences prefs){
		super(workbook, prefs.getProfessorsSheet());
	}

	public void Write(Professor argument) throws WritingException {
		goToRow(argument.getExcelMetadata().getRow());
		
		writeField(argument.getName());
		writeField(argument.getEmail());
		writeField(argument.getCargo());
		writeField(argument.getDpto());
	}
}
