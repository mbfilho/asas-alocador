package services;

import java.util.Vector;

import basic.ElectiveClass;
import basic.ElectiveClassPreferences;
import basic.SlotRange;

public class AllocationService {
	private ConflictService conflictService;
	private ElectivePreferencesService prefService; 
	private ClassService classService;
	private ElectiveClassService electiveService;
	
	public AllocationService(){
		conflictService = new ConflictService();
		prefService = new ElectivePreferencesService();
		classService = new ClassService();
		electiveService = new ElectiveClassService();
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
	
	public void clearAllocation(){
		for(ElectiveClass ec : electiveService.all()) classService.remove(ec);
		for(ElectiveClassPreferences pref : prefService.all()){
			ElectiveClass ec = pref.getElectiveClass();
			ec.setProfessors(pref.getProfessors());
			ec.getSlots().clear();
		}
	}
}
