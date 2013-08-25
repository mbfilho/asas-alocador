package logic.dto;

import java.util.LinkedList;
import java.util.List;
import data.persistentEntities.ElectiveClass;
import data.persistentEntities.ElectiveClassPreferences;


public class AllocationResult {
	public List<ElectiveClass> allocated;
	public List<ElectiveClassPreferences> notAllocated;
	
	public AllocationResult(){
		allocated = new LinkedList<ElectiveClass>();
		notAllocated = new LinkedList<ElectiveClassPreferences>();
	}

}
