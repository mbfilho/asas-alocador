package logic.schedule.formatting.detailing;

import java.awt.Color;
import java.util.List;

import logic.dto.ProfessorIndisponibility;
import data.persistentEntities.Class;

public class DetailsOfSlotDisponibility extends Details{

	public void addProfessorsIndisponibility(List<ProfessorIndisponibility> indisponibility){
		addTitle("Conflito do(s) Professor(es):", null, Color.red);
		for(ProfessorIndisponibility inds : indisponibility){
			String content = String.format("- %s [%s]", inds.professor.getName(), inds.classTaught.completeName());
			addDetail(content, Color.black);
		}
	}
	
	public void addConflictingClasses(List<Class> conflictingClasses){
		addTitle("Conflito com a(s) turma(s):", null, Color.red);
		for(Class c : conflictingClasses)
			addDetail(String.format("- %s", c.getName()), Color.black);
	}
	
	private void addDetail(String content, Color font){
		addDetail(content, null, font);
	}
}
