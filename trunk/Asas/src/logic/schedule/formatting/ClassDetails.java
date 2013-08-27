package logic.schedule.formatting;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import data.persistentEntities.Class;
import data.persistentEntities.Professor;
import data.persistentEntities.SlotRange;

public class ClassDetails implements Iterable<FormattedDetail>{
	private List<FormattedDetail> details;
	private Class theClass;
	
	public ClassDetails(Class theClass){
		details = new LinkedList<FormattedDetail>();
		this.theClass = theClass;
		
		addTitle(theClass.completeName());
		addProfessors();
		addSlotRanges();
	}

	private void addTitle(String title){
		details.add(new FormattedDetail(title, theClass.getColor(), true));
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
		details.add(new FormattedDetail(content, theClass.getColor(), false));
	}
	
	public Iterator<FormattedDetail> iterator() {
		return details.iterator();
	}

}
