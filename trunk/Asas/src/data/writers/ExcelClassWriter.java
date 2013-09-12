package data.writers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import logic.services.StateService;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import data.configurations.ExcelPreferences;
import data.persistentEntities.Class;
import data.persistentEntities.Professor;
import data.persistentEntities.SlotRange;
import exceptions.WritingException;

/**
 * Implementada para ser o mais compatível possível com a leitura
 */
public class ExcelClassWriter extends ExcelWriter<Class>{
	private Sheet classesSheet;
	private Row currentRow;
	private ExcelPreferences prefs;
	private int cellNumber;
	
	public ExcelClassWriter(File file) throws WritingException {
		super(file);
		prefs = StateService.getInstance().getCurrentState().excelPrefs;
		classesSheet = workbook.getSheet(prefs.getClassesSheet());
	}
	
	public void save() throws IOException{
		FileOutputStream out = new FileOutputStream(prefs.getFileLocation());
		workbook.write(out);
		out.close();
	}
	
	public void Write(Class c) throws WritingException {
		currentRow = classesSheet.getRow(c.getExcelMetadata().getRow());
		cellNumber = 0;
		
		writeField(c.getName());
		writeField("OK");
		writeProfessors(c.getProfessors());
		writeField(c.getCh() + "");
		ignoreField("Alunos na pré-matrícula");
		ignoreField("Alunos no semestre anterior");
		writeField(prefs.getMappingCode(c.getRoomsOrderedBySlot()));
		writeField(getSemesterString(c.getCcSemester(), c.getEcSemester()));
		ignoreField("Disciplina conjunta");
		writeField(c.getCode());
		ignoreField("Nome efetivo da disciplina");
		writeField(c.getCourse());
		writeSlots(c.getSlots());
		writeField(c.getCh2() + "");
	}

	private void writeProfessors(List<Professor> professors){
		for(int p = 0; p < professors.size(); ++p)
			writeField(professors.get(p).getName());
		
		for(int i = professors.size(); i < prefs.getProfessorCount(); ++i)
			ignoreField("Professor");
	}
	
	private void writeField(String value){
		Cell cell = currentRow.getCell(cellNumber);
		if(cell == null)
			cell = currentRow.createCell(cellNumber);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellValue(value);
		++cellNumber;
	}
	
	private String getSemesterString(int ccSemester, int ecSemester) {
		if(ccSemester == -1 && ecSemester == -1)
			return null;
		
		String cc = ccSemester == -1 ? "" : (ccSemester + "");
		String ec = ecSemester == -1 ? "" : (ecSemester + "");
		String semesterString = cc + prefs.getSemesterSeparator() + ec;
		
		return semesterString;
	}
	
	private void ignoreField(String fieldIgnored){
		ignoreField();
	}
	
	private void ignoreField(){
		++cellNumber;
	}
	
	private void writeSlots(Collection<SlotRange> slots) {
		for(SlotRange slot : slots)
			writeField(slot.toString());
		
		for(int i = slots.size(); i < prefs.getSlotCount(); ++i)
			ignoreField("Horário");
	}
}
