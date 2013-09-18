package data.persistentEntities.warningsTypes;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import presentation.NamedPair;
import presentation.classes.InitialEditState;

import data.persistentEntities.Class;
import data.persistentEntities.Classroom;
import data.persistentEntities.SlotRange;
import data.persistentEntities.Warning;

import utilities.CollectionUtil;


public class SameRoomWarning extends Warning {
	private static final long serialVersionUID = -1462166204932054942L;
	
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
					"<html>Turmas <b>%s<i>%s</i></b> e <b>%s<i>%s</i></b> na sala <b>%s</b> no horário <b>%s</b> </html>",
					oneClass.getName(),
					oneClass.getFormattedAliasName(),
					anotherClass.getName(),
					anotherClass.getFormattedAliasName(),
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
