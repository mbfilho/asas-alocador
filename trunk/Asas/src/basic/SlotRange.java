package basic;

import java.io.Serializable;
import java.util.Vector;

import utilities.Constants;
import utilities.MyCloneable;

public class SlotRange implements NamedEntity, Serializable, MyCloneable{
	private static SlotRange _emptyRange = new SlotRange(-1, -1, -2, null);
	
	private static final long serialVersionUID = 7748615816585085336L;
	
	private int day;
	private int start, end;
	private Classroom room;
	
	public SlotRange(int day, int start, int end, Classroom room){
		this.day = day;
		this.room = room;
		this.start = start;
		this.end = end;
	}
	
	public static SlotRange emptyRange(){
		return _emptyRange;
	}
	
	public static SlotRange singleSlotRange(int day, int slot){
		return new SlotRange(day, slot, slot, null);
	}

	public void setDay(int day){
		this.day = day;
	}
	
	public int getDay(){
		return day;
	}
	
	public void setStartSlot(int st){
		this.start = st;
	}
	
	public int getStartSlot(){
		return start;
	}
	
	public void setEndSlot(int end){
		this.end = end;
	}
	
	public int getEndSlot(){
		return end;
	}
	
	public String toString(){
		String rep = Constants.days[day] + ", ";
		rep += (start + 7) + "-" + (end + 8);
		return rep;
	}
	
	public boolean equals(Object obj){
		SlotRange ot = (SlotRange) obj;
		return day == ot.getDay() && start == ot.getStartSlot() && end == ot.getEndSlot();
	}

	public SlotRange intersection(SlotRange ot){
		if(day != ot.day) return emptyRange();
		return new SlotRange(day, Math.max(start, ot.start), Math.min(end, ot.end), null);
	}
	
	public boolean intersects(SlotRange ot){
		SlotRange inter = intersection(ot);
		return inter.isValid();
	}
	
	public boolean isValid(){
		return start <= end;
	}
	
	public String getNameWithoutRoom(){
		return toString();
	}
	
	public String getName() {
		return toString() + "  - " + (room == null ? "Sem Sala" : "Sala " + room.getName());
	}
	
	public Classroom getClassroom(){
		return room;
	}
	
	public void setClassroom(Classroom room){
		this.room = room;
	}
	
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
}
