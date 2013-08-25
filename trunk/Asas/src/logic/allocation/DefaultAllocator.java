package logic.allocation;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import logic.dto.AllocationResult;
import logic.services.AllocationService;
import logic.services.ClassroomService;
import logic.services.ElectivePreferencesService;

import data.persistentEntities.Classroom;
import data.persistentEntities.ElectiveClassPreferences;
import data.persistentEntities.SlotRange;



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
		AllocationResult result = new AllocationResult();
		service.clearNotAllocated();
		
		Vector<Classroom> orderedRooms = new Vector<Classroom>(roomService.all());
		Collections.sort(orderedRooms, new Comparator<Classroom>() {
			public int compare(Classroom o1, Classroom o2) {
				return o1.getCapacity() - o2.getCapacity();
			}
		});
		
		for(ElectiveClassPreferences pref : prefService.all()){
			boolean allocated = service.isAllocated(pref.getElectiveClass());
			
			for(Classroom r : orderedRooms){
				if(allocated){
					result.allocated.add(pref.getElectiveClass());
					break;
				}
				
				if(r.getCapacity() < pref.getStudentCount()) continue;
				
				for(Vector<SlotRange> meetings : pref.getSlots()){
					selectRoomForMeetings(meetings, r);
					
					if(service.canAllocate(pref.getElectiveClass(), meetings)){
						service.allocate(pref.getElectiveClass(), meetings);
						allocated = true;
						break;
					}
					
					selectRoomForMeetings(meetings, null);
				}
			}
			
			if(!allocated){
				result.notAllocated.add(pref);
				service.allocate(pref.getElectiveClass(), pref.getSlots().firstElement());
			}
		}
		return result;
	}


}
