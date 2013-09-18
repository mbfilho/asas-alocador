package logic.schedule.formatting.detailing;

import java.awt.Color;
import java.util.List;

import logic.dto.ProfessorIndisponibility;
import data.persistentEntities.Class;

public class DetailsOfSlotDisponibility extends Details{

	public void addProfessorsIndisponibility(List<ProfessorIndisponibility> indisponibility){
		addTitle("Professor(es) já ocupado(s) nesse horário:", null, Color.red);
		for(ProfessorIndisponibility inds : indisponibility){
			String content = String.format("- %s [%s]", inds.professor.getName(), inds.classTaught.completeName());
			addDetail(content, Color.black);
		}
	}
	
	public void addConflictingClasses(List<Class> conflictingClasses){
		addTitle("Turma(s) nesse horário e sala:", null, Color.red);
		for(Class c : conflictingClasses)
			addDetail(String.format("- %s", c.getName()), Color.black);
	}
	
	private void addDetail(String content, Color font){
		addDetail(content, null, font);
	}
}
