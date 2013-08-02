package allocation;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import services.AllocationService;
import services.ClassroomService;
import services.ElectivePreferencesService;

import basic.Classroom;
import basic.ElectiveClass;
import basic.ElectiveClassPreferences;
import basic.SlotRange;

public class DefaultAllocator implements Allocator {

	private AllocationService service;
	private ElectivePreferencesService prefService;
	private ClassroomService roomService;
	
	public DefaultAllocator(){
		service = new AllocationService();
		prefService = new ElectivePreferencesService();
		roomService = new ClassroomService();
	}
	
	private void selectRoomForMeetings(Vector<SlotRange> meetings, Classroom room){
		for(SlotRange meeting : meetings) meeting.setClassroom(room);
	}
	
	public AllocationResult allocate(boolean onlyInPreferedSlots) {
		service.clearAllocation();
		
		AllocationResult result = new AllocationResult();
		Vector<Classroom> orderedRooms = new Vector<Classroom>(roomService.all());
		Collections.sort(orderedRooms, new Comparator<Classroom>() {
			public int compare(Classroom o1, Classroom o2) {
				return o1.getCapacity() - o2.getCapacity();
			}
		});
		
		for(ElectiveClassPreferences pref : prefService.all()){
			boolean allocated = false;
			
			for(Classroom r : orderedRooms){
				if(allocated) break;
				if(r.getCapacity() < pref.getStudentCount()) continue;
				
				for(Vector<SlotRange> meetings : pref.getSlots()){
					selectRoomForMeetings(meetings, r);
					
					if(service.canAllocate(pref.getElectiveClass(), meetings)){
						result.allocated.add(pref.getElectiveClass());
						service.allocate(pref.getElectiveClass(), meetings);
						allocated = true;
						break;
					}
					
					selectRoomForMeetings(meetings, null);
				}
			}
			
			if(!allocated) result.notAllocated.add(pref);
		}
		return result;
	}


}
