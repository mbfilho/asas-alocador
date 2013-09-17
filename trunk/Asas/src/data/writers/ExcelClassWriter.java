package data.writers;

import java.util.Collection;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import data.configurations.ExcelPreferences;
import data.persistentEntities.Class;
import data.persistentEntities.Professor;
import data.persistentEntities.SlotRange;
import exceptions.WritingException;

/**
 * Implementada para ser o mais compatível possível com a leitura
 */
public class ExcelClassWriter extends ExcelWriter<Class>{
	private ExcelPreferences prefs;
	
	public ExcelClassWriter(Workbook workbook, ExcelPreferences thePrefs){
		super(workbook, thePrefs.getClassesSheet());
		prefs = thePrefs;
	}
	
	public void Write(Class c) throws WritingException {
		goToRow(c.getExcelMetadata().getRow());
		
		writeField(c.getName());
		writeField("OK");
		writeProfessors(c.getProfessors());
		writeField(c.getCh());
		ignoreField("Alunos na pré-matrícula");
		ignoreField("Alunos no semestre anterior");
		writeField(prefs.getMappingCode(c.getRoomsOrderedBySlot()));
		writeField(getSemesterString(c.getCcSemester(), c.getEcSemester()));
		ignoreField("Disciplina conjunta");
		writeField(c.getCode());
		ignoreField("Nome efetivo da disciplina");
		writeField(c.getCourse());
		writeSlots(c.getSlots());
		writeField(c.getCh2());
	}

	private void writeProfessors(List<Professor> professors){
		for(int p = 0; p < professors.size(); ++p)
			writeField(professors.get(p).getName());
		
		for(int i = professors.size(); i < prefs.getProfessorCount(); ++i)
			ignoreField("Professor");
	}
	
	private String getSemesterString(int ccSemester, int ecSemester) {
		if(ccSemester == -1 && ecSemester == -1)
			return null;
		
		String cc = ccSemester == -1 ? "" : (ccSemester + "");
		String ec = ecSemester == -1 ? "" : (ecSemester + "");
		String semesterString = cc + prefs.getSemesterSeparator() + ec;
		
		return semesterString;
	}
	
	private void writeSlots(Collection<SlotRange> slots) {
		for(SlotRange slot : slots)
			writeField(slot.toString());
		
		for(int i = slots.size(); i < prefs.getSlotCount(); ++i)
			ignoreField("Horário");
	}
}
