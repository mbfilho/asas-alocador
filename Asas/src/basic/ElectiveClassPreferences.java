package basic;

import java.io.Serializable;
import java.util.Vector;

public class ElectiveClassPreferences implements NamedEntity, Serializable{

	private ElectiveClass theClass;
	private Vector<SlotRange> preferedSlots;
	private Vector<Professor> professors;
	private int students;
	
	public ElectiveClassPreferences(ElectiveClass theClass){
		this.theClass = theClass;
		preferedSlots = new Vector<SlotRange>();
		professors = new Vector<Professor>();
	}
	
	public void setStudentCount(int cont){
		students = cont;
	}
	
	public void addSlotRange(SlotRange range){
		preferedSlots.add(range);
	}
	
	public void addProfessor(Professor p){
		professors.add(p);
	}

	public String getName() {
		return theClass.getName();
	}
	
}
