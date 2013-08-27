package logic.schedule.formatting.detailing;

import data.persistentEntities.Class;
import data.persistentEntities.Professor;
import data.persistentEntities.SlotRange;

public class DetailsOfClass extends Details{
	private Class theClass;
	
	public DetailsOfClass(Class theClass){
		this.theClass = theClass;
		
		addTitle(theClass.completeName());
		addProfessors();
		addSlotRanges();
	}

	private void addProfessors() {
		addTitle("Professores:");
		for(Professor p : theClass.getProfessors())
			addDetail(p.getName());
	}
	
	private void addSlotRanges(){
		addTitle("Horários:");
		for(SlotRange r : theClass.getSlots())
			addDetail(r.getName());
	}
	
	private void addDetail(String content){
		addDetail(content, theClass.getColor(), null);
	}
	
	private void addTitle(String title){
		addTitle(title, theClass.getColor(), null);
	}

}
