package services;

import java.util.Vector;

import allocation.AllocationResult;
import basic.ElectiveClass;
import basic.ElectiveClassPreferences;
import basic.SlotRange;

public class AllocationService {
	private ConflictService conflictService;
	private ElectivePreferencesService prefService; 
	private ClassService classService;
	
	public AllocationService(){
		conflictService = new ConflictService();
		prefService = new ElectivePreferencesService();
		classService = new ClassService();
	}
	
	public boolean canAllocate(ElectiveClass electiveClass,	Vector<SlotRange> meetings) {
		for(SlotRange r : meetings){
			if(r.getClassroom().getName().compareToIgnoreCase("Area II") == 0) return false;
			if(r.getClassroom().getName().compareToIgnoreCase("CTG") == 0) return false;
			
			if(!conflictService.areProfessorsOfThisClassAvailable(electiveClass, r)) return false;
			if(!conflictService.isClassroomFreeForThisClass(electiveClass, r.getClassroom(), r)) return false;
		}
		
		return true;
	}

	public void allocate(ElectiveClass electiveClass, Vector<SlotRange> meetings) {
		electiveClass.setSlots(meetings);
		classService.add(electiveClass);
	}
	
	public boolean isAllocated(ElectiveClass ec){
		return ec.hasClassroom();
	}
	
	public AllocationResult getCurrentElectiveAllocation(){
		AllocationResult result = new AllocationResult();
		ElectivePreferencesService prefService = new ElectivePreferencesService();
		
		for(ElectiveClassPreferences pref : prefService.all()){
			ElectiveClass ec = pref.getElectiveClass();
			if(ec.hasClassroom()) result.allocated.add(ec);
			else result.notAllocated.add(pref);
		}
		return result;
	}
	
	public void clearNotAllocated(){
		for(ElectiveClassPreferences pref : prefService.all()){
			if(!isAllocated(pref.getElectiveClass()))
				removeAllocationForThisClass(pref);
		}
	}
	
	private void removeAllocationForThisClass(ElectiveClassPreferences pref){
		classService.remove(pref.getElectiveClass());
		ElectiveClass ec = pref.getElectiveClass();
		ec.setProfessors(pref.getProfessors());
		ec.getSlots().clear();
	}
	
	public void clearAllocation(){
		for(ElectiveClassPreferences pref : prefService.all())
			removeAllocationForThisClass(pref);
	}
}
