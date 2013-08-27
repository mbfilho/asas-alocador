package logic.dto;

import data.persistentEntities.Professor;
import data.persistentEntities.Class;
import data.persistentEntities.SlotRange;


public class ProfessorIndisponibility {
	public Professor professor;
	public Class classTaught;
	public SlotRange slot;
	
	public ProfessorIndisponibility(Professor prof, Class c, SlotRange slot){
		professor = prof;
		classTaught = c;
		this.slot = slot;
	}
}
