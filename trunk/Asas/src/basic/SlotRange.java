package basic;

import java.io.Serializable;
import java.util.Vector;

public class SlotRange implements NamedEntity, Serializable{
	private int day, slot;
	private static String days[] = {"domingo", "segunda", "terça", "quarta", "quinta", "sexta", "sábado"};
		
	public SlotRange(int day, int slot){
		this.day = day;
		this.slot = slot;
	}
	
	public int getDay(){
		return day;
	}
	
	public int getSlot(){
		return slot;
	}
	
	public String toString(){
		String rep = days[day] + ", ";
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
	
	public static Vector<SlotRange> all(){
		Vector<SlotRange> slots = new Vector();
		for(int day = 0; day < 7; ++day) for(int s = 7; s <= 21; ++s) slots.add(new SlotRange(day, s));
		return slots;
	}
}
