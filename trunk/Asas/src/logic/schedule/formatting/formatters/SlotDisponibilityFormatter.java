package logic.schedule.formatting.formatters;

import java.awt.Color;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import utilities.ColorUtil;
import data.persistentEntities.Class;
import data.persistentEntities.Classroom;
import data.persistentEntities.SlotRange;
import logic.dto.ProfessorIndisponibility;
import logic.schedule.formatting.detailing.Details;
import logic.schedule.formatting.detailing.DetailsOfSlotDisponibility;
import logic.schedule.formatting.detailing.ScheduleSlotDetails;
import logic.services.ConflictService;

public class SlotDisponibilityFormatter implements ScheduleFormatter{

	private final int NO_CONFLICT = 0, ROOM_OCCUPIED = 1 << 0, PROFESSOR_CONFLICT = 1 << 1;
	private ConflictService conflictService;
	private Class theClass;
	private Classroom theClassroom;
	
	public SlotDisponibilityFormatter(Class theClass, Classroom room){
		this.theClass = theClass;
		theClassroom = room;
		conflictService = new ConflictService();
	}
	
	public static List<SlotDisponibilityFormatter> getFormatterForThisClassAndSlots(Class theClass, Iterable<SlotRange> slots){
		List<SlotDisponibilityFormatter> formatters = new LinkedList<SlotDisponibilityFormatter>();
		HashSet<Classroom> rooms = new HashSet<Classroom>();
		for(SlotRange s : slots){
			if(s.getClassroom() != null) rooms.add(s.getClassroom());
		}
		
		for(Classroom r : rooms)
			formatters.add(new SlotDisponibilityFormatter(theClass, r));
		
		if(rooms.isEmpty()){
			Classroom room = new Classroom("Turma sem sala");
			formatters.add(new SlotDisponibilityFormatter(theClass, room));
		}
		
		return formatters;
	}
	
	public Classroom getRoom(){
		return theClassroom;
	}
	
	private int calculateConflictState(int slot, int day){
		int state = NO_CONFLICT;
		if(!conflictService.isClassroomFreeForThisClass(theClass, theClassroom, SlotRange.singleSlotRange(day, slot)))
			state |= ROOM_OCCUPIED;
		if(!conflictService.areProfessorsOfThisClassAvailable(theClass, SlotRange.singleSlotRange(day, slot)))
			state |= PROFESSOR_CONFLICT;
		return state;
	}
	
	@Override
	public ScheduleSlotFormat getFormat(int slot, int day) {
		int conflictState = calculateConflictState(slot, day);
		ScheduleSlotFormat format = new ScheduleSlotFormat(Color.white, Color.black, getCellContent(slot, day, conflictState), "");
		if(conflictService.isClassInThisRoomAtThisSlot(theClass, new SlotRange(day, slot, slot, theClassroom))){
			if(conflictState == NO_CONFLICT)
				format.background = ColorUtil.mixWithWhite(Color.blue);
			else{
				format.setBorder(ColorUtil.mixWithWhite(Color.blue), 3);
			}
		}
		
		if(conflictState != NO_CONFLICT) 
			format.background = Color.orange;
		
		return format;
	}
	
	private List<String> getCellContent(int slot, int day, int state) {
		List<String> content = new LinkedList<String>();
		if(state == PROFESSOR_CONFLICT){
			getProfessorOnlyConflict(slot, day, content);
		}else if(state == ROOM_OCCUPIED){
			getRoomOnlyConflict(slot, day, content);
		}else if(state == (PROFESSOR_CONFLICT | ROOM_OCCUPIED)){
			content.add("Múltiplos conflitos: Clique aqui.");
		}
		return content;
	}

	private void getRoomOnlyConflict(int slot, int day, List<String> content) {
		List<Class> otherClasses = conflictService.getClassesOccupingThisRoom(new SlotRange(day, slot, slot, theClassroom));
		for(Class c : otherClasses) 
			content.add(c.getName());
	}

	private void getProfessorOnlyConflict(int slot, int day, List<String> content) {
		List<ProfessorIndisponibility> profsConflicteds = 
				conflictService.getUnavailableProfessorsOfThisClass(theClass, SlotRange.singleSlotRange(day, slot));
		for(ProfessorIndisponibility prof : profsConflicteds)
			content.add(prof.professor.getName());
	}

	@Override
	public ScheduleSlotDetails getDetails(int slot, int day) {
		int state = calculateConflictState(slot, day);
		ScheduleSlotDetails detailsList = new ScheduleSlotDetails();

		if(conflictService.isClassInThisRoomAtThisSlot(theClass, new SlotRange(day, slot, slot, theClassroom))){
			Details allocationDetail = new Details();
			allocationDetail.addTitle("Disciplina alocada nesse slot.", null, Color.blue);
			detailsList.add(allocationDetail);
		}
		
		if(state != NO_CONFLICT){
			DetailsOfSlotDisponibility dispDetails = new DetailsOfSlotDisponibility();
			
			if((state & PROFESSOR_CONFLICT) != 0){
				List<ProfessorIndisponibility> profs = conflictService.getUnavailableProfessorsOfThisClass(theClass, SlotRange.singleSlotRange(day, slot));
				dispDetails.addProfessorsIndisponibility(profs);
			}
			
			if((state & ROOM_OCCUPIED) != 0){
				List<Class> classes = conflictService.getClassesOccupingThisRoom(new SlotRange(day, slot, slot, theClassroom));
				dispDetails.addConflictingClasses(classes);
			}
			detailsList.add(dispDetails);
		}
		
		return detailsList;
	}

}
