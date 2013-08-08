package basic;

import java.io.Serializable;
import java.util.Vector;

public class ElectiveClassPreferences implements NamedEntity, Serializable{

	private ElectiveClass theClass;
	private Vector<Vector<SlotRange>> preferedSlots;
	private Vector<Professor> professors;
	private int students;
	
	public ElectiveClassPreferences(ElectiveClass theClass){
		this.theClass = theClass;
		preferedSlots = new Vector<Vector<SlotRange>>();
		professors = new Vector<Professor>();
	}
	
	public void setStudentCount(int cont){
		students = cont;
	}
	
	public int getStudentCount(){
		return students;
	}
	
	public void addSlotRange(Vector<SlotRange> range){
		preferedSlots.add(range);
	}
	
	public Vector<Vector<SlotRange>> getSlots(){
		return preferedSlots;
	}
	
	public void addProfessor(Professor p){
		professors.add(p);
	}
	
	public Vector<Professor> getProfessors(){
		return professors;
	}

	public ElectiveClass getElectiveClass(){
		return theClass;
	}
	
	public String getName() {
		return theClass.getName();
	}
	
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
}
