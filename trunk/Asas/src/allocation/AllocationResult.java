package allocation;

import java.util.Vector;

import basic.ElectiveClass;
import basic.ElectiveClassPreferences;

public class AllocationResult {
	public Vector<ElectiveClass> allocated;
	public Vector<ElectiveClassPreferences> notAllocated;
	
	public AllocationResult(){
		allocated = new Vector<ElectiveClass>();
		notAllocated = new Vector<ElectiveClassPreferences>();
	}
}
