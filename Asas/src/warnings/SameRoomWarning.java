package warnings;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import classEditor.InitialEditState;
import classEditor.NamedPair;

import utilities.CollectionUtil;

import basic.Classroom;
import basic.Class;
import basic.SlotRange;

public class SameRoomWarning extends Warning {

	private Classroom theRoom;
	private Class oneClass, anotherClass;
	private SlotRange slotRange;
	
	public SameRoomWarning(Classroom room, Class c1, Class c2, SlotRange range){
		theRoom = room;
		oneClass = c1;
		anotherClass = c2;
		slotRange = range;
	}
	
	public Classroom getRoom(){
		return theRoom;
	}
	
	public Vector<Class> getClasses(){
		Vector<Class> classes = new Vector<Class>();
		classes.add(oneClass); classes.add(anotherClass);
		return classes;
	}
	
	public SlotRange getSlotRange(){
		return slotRange;
	}
	
	public String getMessage() {
		return String.format(
					"<html>Turmas <b>%s</b> e <b>%s</b> na sala <b>%s</b> no horário <b>%s</b> </html>",
					oneClass.getName(),
					anotherClass.getName(),
					theRoom.getName(), 
					slotRange.toString()
				);
	}
	
	public boolean equals(Object obj){
		if(!(obj instanceof SameRoomWarning)) return false;
		SameRoomWarning other = (SameRoomWarning) obj;
		return theRoom == other.getRoom() &&
				CollectionUtil.equalsWithoutOrder(getClasses(), other.getClasses())
				&& other.getSlotRange().equals(slotRange);
				
	}

	public List<NamedPair<Class>> getSolutionList(){
		List<NamedPair<Class>> solutions = new LinkedList<NamedPair<Class>>();
		solutions.add(new NamedPair<Class>("Editar " + oneClass.getName() + "...", oneClass));
		solutions.add(new NamedPair<Class>("Editar " + anotherClass.getName() + "...", anotherClass));
		return solutions;
	}
	
	public InitialEditState getInfoToSolve(Class selected) {
		InitialEditState state = 
				new InitialEditState(selected, theRoom, CollectionUtil.firstOrDefault(selected.getProfessors()));
		return state;
	}
}
