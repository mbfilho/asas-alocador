package warnings;

import java.util.Vector;

import utilities.CollectionUtil;
import utilities.StringUtil;

import basic.SlotRange;
import basic.Class;

public class ClassWithoutRoom extends Warning {

	private Class theClass;
	private Vector<SlotRange> slots;
	
	public ClassWithoutRoom(Class c, Vector<SlotRange> slots){
		theClass = c;
		this.slots = slots;
	}
	
	public Class getTheClass(){
		return theClass;
	}
	
	public Vector<SlotRange> getSlots(){
		return slots;
	}
	
	public String getMessage() {
		return String.format("<html>Turma <b>%s</b> está sem sala no(s) horário(s) <b>%s</b></html>", 
				theClass.completeName(),
				StringUtil.joinListWithSeparator(slots, "/")
			);
	}

	public boolean equals(Object obj) {
		if(!(obj instanceof ClassWithoutRoom)) return false;
		ClassWithoutRoom other = (ClassWithoutRoom) obj;
		
		return theClass == other.getTheClass()
				&& CollectionUtil.equalsWithoutOrder(slots, other.getSlots());
	}

}
