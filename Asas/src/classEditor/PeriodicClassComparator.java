package classEditor;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import utilities.CollectionUtil;
import utilities.Pair;
import basic.Class;
import basic.SlotRange;

public abstract class PeriodicClassComparator extends Thread{

	private boolean dirty;
	private boolean alive;
	private Comparator<SlotRange> slotComparator;
	
	protected abstract Pair<Class, Class> getClassesToCompare();
	protected abstract void onChangeState(boolean isDirty);
	
	public PeriodicClassComparator(){
		alive = true;
		slotComparator = new Comparator<SlotRange>() {
			public int compare(SlotRange o1, SlotRange o2) {
				if(o1.getDay() != o2.getDay()) return o1.getDay() - o2.getDay();
				if(o1.getStartSlot() != o2.getStartSlot()) return o1.getStartSlot() - o2.getStartSlot();
				if(o1.getEndSlot() != o2.getEndSlot()) return o1.getEndSlot() - o2.getEndSlot();
				if(o1.getClassroom() == null && o2.getClassroom() == null) return 0;
				if(o1.getClassroom() == null && o2.getClassroom() != null) return -1;
				if(o1.getClassroom() != null && o2.getClassroom() == null) return 1;
				return o1.getClassroom().getName().compareTo(o2.getClassroom().getName());
			}
		};
		start();
	}
	
	public void stopThread(){
		alive = false;
	}
	
	private boolean areObjectEquals(Object a, Object b){
		return (a == null && b == a) || (a != null && a.equals(b));
	}
	
	private boolean areSlotListEquals(Collection<SlotRange> a, Collection<SlotRange> b){
		if(a.size() != b.size()) return false;
		List<SlotRange> orderedA = new Vector<SlotRange>(a), orderedB = new Vector<SlotRange>(b);
		
		Collections.sort(orderedA, slotComparator);
		Collections.sort(orderedB, slotComparator);
		for(int i = 0; i < orderedA.size(); ++i){
			if(slotComparator.compare(orderedA.get(i), orderedB.get(i)) != 0) 
				return false;
		}
		return true;
	}
	
	private boolean areDifferent(Class a, Class b){
		//System.out.println(a.getCcSemester() + " x " + b.getCcSemester());
		if(a.getCcSemester() != b.getCcSemester()) return true;
		//System.out.println("cc");
		if(!areObjectEquals(a.getCode(), b.getCode())) return true;
		//System.out.println("code");
		if(!areObjectEquals(a.getCourse(), b.getCourse())) return true;
		//System.out.println("course");
		if(a.getEcSemester() != b.getEcSemester()) return true;
		//System.out.println("ec");
		if(!areObjectEquals(a.getName(), b.getName())) return true;
		//System.out.println("name");
		if(!CollectionUtil.equalsWithoutOrder(a.getProfessors(), b.getProfessors())) return true;
		//System.out.println("profs");
		if(!areSlotListEquals(a.getSlots(), b.getSlots())) return true;
		//System.out.println("slots");
		return false;
	}
	
	public void run(){
		while(alive){
			Pair<Class, Class> par = getClassesToCompare();
			if(par.first != null && par.second != null){
				if(!dirty && areDifferent(par.first, par.second)){
					onChangeState(true);
					dirty = true;
				}else if(dirty && !areDifferent(par.first, par.second)){
					dirty = false;
					onChangeState(false);
				}
			}
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
