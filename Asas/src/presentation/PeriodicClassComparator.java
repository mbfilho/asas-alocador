package presentation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import data.persistentEntities.Class;
import data.persistentEntities.SlotRange;

import utilities.CollectionUtil;
import utilities.Pair;

public abstract class PeriodicClassComparator extends Thread{

	private boolean dirty;
	private boolean alive;
	
	protected abstract Pair<Class, Class> getClassesToCompare();
	protected abstract void onChangeState(boolean isDirty);
	
	public PeriodicClassComparator(){
		alive = true;
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
		List<SlotRange> orderedA = new ArrayList<SlotRange>(a), orderedB = new ArrayList<SlotRange>(b);
		
		Collections.sort(orderedA);
		Collections.sort(orderedB);
		for(int i = 0; i < orderedA.size(); ++i){
			if(orderedA.get(i).compareTo(orderedB.get(i)) != 0) 
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
