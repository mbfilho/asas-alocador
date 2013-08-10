package scheduleVisualization;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import services.ConflictService;
import basic.Class;
import basic.Classroom;
import basic.Professor;
import basic.SlotRange;

public class DisponibilityFormatter implements TableFormatter{

	private Class theClass;
	private Classroom theClassroom;
	private ConflictService conflictService;
	private final int NO_CONFLICT = 0, PROFESSOR_CONFLICT_ONLY = 1, ROOM_OCCUPIED_ONLY = 2, PROFESSOR_AND_ROOM = 3;
	
	public DisponibilityFormatter(Classroom room, Class theClass){
		this.theClass = theClass;
		theClassroom = room;
		conflictService = new ConflictService();
	}
	
	private int calculateConflictState(int day, int slot){
		int state = NO_CONFLICT;
		if(!conflictService.isClassroomFreeForThisClass(theClass, theClassroom, SlotRange.singleSlotRange(day, slot)))
			state |= ROOM_OCCUPIED_ONLY;
		if(!conflictService.areProfessorsOfThisClassAvailable(theClass, SlotRange.singleSlotRange(day, slot)))
			state |= PROFESSOR_CONFLICT_ONLY;
		return state;
	}
	
	public void formatCell(JLabel cell, int day, int slot) {
		int cellConflictState = calculateConflictState(day, slot);
		
		if(conflictService.isClassInThisRoomAtThisSlot(theClass, new SlotRange(day, slot, slot, theClassroom))){
			cell.setBorder(BorderFactory.createLineBorder(Color.blue, 3));
			if(cellConflictState == NO_CONFLICT) cell.setBackground(Color.blue);
		}
		
		if(cellConflictState != NO_CONFLICT) cell.setBackground(Color.red);
	}

	private JLabel createColoredLabel(String msg, Color color){
		JLabel label = new JLabel(msg);
		label.setForeground(color);
		return label;
	}
	
	public Component getPopupContent(int day, int slot) {
		int state = calculateConflictState(day, slot);

		JPanel menu = new JPanel();
		menu.setLayout(new GridLayout(100, 1));
		int rows = 0;
		if(conflictService.isClassInThisRoomAtThisSlot(theClass, new SlotRange(day, slot, slot, theClassroom))){
			menu.add(createColoredLabel("Disciplina alocada nesse slot.", Color.blue));
			rows += 1;
		}
		if(state != NO_CONFLICT){
			if((state & PROFESSOR_CONFLICT_ONLY) != 0){
				Vector<Professor> profs = conflictService.getUnavailableProfessorsOfThisClass(theClass, SlotRange.singleSlotRange(day, slot));
				menu.add(createColoredLabel("Conflito do(s) Professor(es):", Color.red));
				for(Professor p : profs) menu.add(createColoredLabel("- " + p.getName(), Color.black));
				rows += 1 + profs.size();
			}
			if((state & ROOM_OCCUPIED_ONLY) != 0){
				Vector<Class> classes = conflictService.getClassesOccupingThisRoom(new SlotRange(day, slot, slot, theClassroom));
				menu.add(createColoredLabel("Conflito com a(s) turma(s):", Color.red));
				for(Class c : classes) menu.add(createColoredLabel("- " + c.getName(), Color.black));
				rows += 1 + classes.size();
			}
		}
		if(rows != 0){
			((GridLayout)menu.getLayout()).setRows(rows);
			return menu;
		}
		return null;
	}
	
	private String abbreviate(String s){
		String names[] = s.split(" "), name = names[0];
		if(names.length > 1) name += " " + names[1].substring(0, Math.min(4, names[1].length())) + ".";
		return name;
	}

	public Object getCellContent(int day, int slot) {
		int state = calculateConflictState(day, slot);
		if(state == PROFESSOR_CONFLICT_ONLY){
			Vector<Professor> profsConflicteds = conflictService.getUnavailableProfessorsOfThisClass(theClass, SlotRange.singleSlotRange(day, slot));
			String content = "<html>";
			for(Professor p : profsConflicteds){
				content += "<p style=\"color:white\">" + abbreviate(p.getName()) + "</p>";
			}
			content += "</html>";
			return content;
		}else if(state == ROOM_OCCUPIED_ONLY){
			Vector<Class> otherClasses = conflictService.getClassesOccupingThisRoom(new SlotRange(day, slot, slot, theClassroom));
			String content = "<html>";
			for(Class c : otherClasses) content += "<p style=\"color:white\">" + abbreviate(c.getName()) + "</p>";
			return content + "</html>";
		}else if(state == PROFESSOR_AND_ROOM){
			return "<html><p style='color:black'>Conflitos: Clique aqui.</p></html>";
		}else return "";
	}
}
