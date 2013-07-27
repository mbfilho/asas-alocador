package basic;

import java.io.Serializable;
import java.util.Vector;

import utilities.Constants;

public class SlotRange implements NamedEntity, Serializable{
	private static final long serialVersionUID = 7748615816585085336L;
	
	private int day, slot;
	private Classroom room;
	
	public SlotRange(int day, int slot, Classroom room){
		this.day = day;
		this.slot = slot;
		this.room = room;
	}
	
	public int getDay(){
		return day;
	}
	
	public int getSlot(){
		return slot;
	}
	
	public String toString(){
		String rep = Constants.days[day] + ", ";
		rep += (slot + 7) + "-" + (slot + 8);
		return rep;
	}
	
	public boolean equals(Object obj){
		SlotRange ot = (SlotRange) obj;
		return day == ot.getDay() && slot == ot.getSlot();
	}

	public String getName() {
		return toString();
	}
	
	public Classroom getClassroom(){
		return room;
	}
	
	@Deprecated
	public static Vector<SlotRange> all(){
		Vector<SlotRange> slots = new Vector<SlotRange>();
		for(int day = 0; day < 7; ++day) for(int s = 7; s <= 21; ++s) slots.add(new SlotRange(day, s, null));
		return slots;
	}
}
