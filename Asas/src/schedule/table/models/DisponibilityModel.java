package schedule.table.models;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;

import services.ConflictService;
import utilities.ColorUtil;
import utilities.Constants;
import utilities.Pair;

import basic.Classroom;
import basic.Class;
import basic.Professor;
import basic.SlotRange;

public class DisponibilityModel extends GeneralScheduleModel {
	private static final long serialVersionUID = 8119692674051932307L;
	private final int NO_CONFLICT = 0, PROFESSOR_CONFLICT_ONLY = 1, ROOM_OCCUPIED_ONLY = 2, PROFESSOR_AND_ROOM = 3;
	private ConflictService conflictService;
	private Class theClass;
	private Classroom theClassroom;
	
	public DisponibilityModel(Class theClass, Classroom theRoom){
		conflictService = new ConflictService();
		this.theClass = theClass;
		this.theClassroom = theRoom;
		configureTable();
	}
	
	private int calculateConflictState(int day, int slot){
		int state = NO_CONFLICT;
		if(!conflictService.isClassroomFreeForThisClass(theClass, theClassroom, SlotRange.singleSlotRange(day, slot)))
			state |= ROOM_OCCUPIED_ONLY;
		if(!conflictService.areProfessorsOfThisClassAvailable(theClass, SlotRange.singleSlotRange(day, slot)))
			state |= PROFESSOR_CONFLICT_ONLY;
		return state;
	}
	
	private void configureTable(){
		for(int slot = 0; slot < Constants.SLOTS; ++slot){
			for(int day = 0; day < Constants.DAYS; ++day){
				int row = slot, col = day + 1, state = calculateConflictState(day, slot);
				cellState[row][col].setValue(getCellContent(day, slot, state));
				cellState[row][col].setFontColor(Color.black);
				cellState[row][col].setBackColor(Color.white);
				
				if(conflictService.isClassInThisRoomAtThisSlot(theClass, new SlotRange(day, slot, slot, theClassroom))){
					if(state == NO_CONFLICT) 
						cellState[row][col].setBackColor(ColorUtil.mixWithWhite(Color.blue));
					else
						cellState[row][col].setBorder(ColorUtil.mixWithWhite(Color.blue), 3);
				}
				
				if(state != NO_CONFLICT) cellState[row][col].setBackColor(Color.orange);
			}
		}
	}
	
	private String getCellContent(int day, int slot, int state) {
		if(state == PROFESSOR_CONFLICT_ONLY){
			Vector<Pair<Professor, Class>> profsConflicteds = 
					conflictService.getUnavailableProfessorsOfThisClass(theClass, SlotRange.singleSlotRange(day, slot));
			String content = "<html>";
			for(Pair<Professor, Class> pair : profsConflicteds){
				content += "<p style=\"color:black\">" + abbreviate(pair.first.getName()) + "</p>";
			}
			content += "</html>";
			return content;
		}else if(state == ROOM_OCCUPIED_ONLY){
			Vector<Class> otherClasses = conflictService.getClassesOccupingThisRoom(new SlotRange(day, slot, slot, theClassroom));
			String content = "<html>";
			for(Class c : otherClasses) content += "<p style=\"color:black\">" + abbreviate(c.getName()) + "</p>";
			return content + "</html>";
		}else if(state == PROFESSOR_AND_ROOM){
			return "<html><p style='color:red'>Múltiplos conflitos: Clique aqui.</p></html>";
		}else return "";
	}
	
	private JLabel createColoredLabel(String msg, Color color){
		JLabel label = new JLabel(msg);
		label.setForeground(color);
		return label;
	}
	
	private JPanel createPopup(int day, int slot, int state) {
		JPanel menu = new JPanel();
		menu.setLayout(new GridLayout(100, 1));
		int rows = 0;
		if(conflictService.isClassInThisRoomAtThisSlot(theClass, new SlotRange(day, slot, slot, theClassroom))){
			menu.add(createColoredLabel("Disciplina alocada nesse slot.", Color.blue));
			rows += 1;
		}
		if(state != NO_CONFLICT){
			if((state & PROFESSOR_CONFLICT_ONLY) != 0){
				Vector<Pair<Professor, Class>> profs = conflictService.getUnavailableProfessorsOfThisClass(theClass, SlotRange.singleSlotRange(day, slot));
				menu.add(createColoredLabel("Conflito do(s) Professor(es):", Color.red));
				for(Pair<Professor, Class> pair : profs) 
					menu.add(createColoredLabel("- " + pair.first.getName() + " [" + pair.second.completeName() + "]", Color.black));
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
	
	public Component getPopupContent(int row, int column) {
		int day = column-1, slot = row;
		int state = calculateConflictState(day, slot);
		return createPopup(day, slot, state);
	}
	
	private String abbreviate(String s){
		return s;
	}
}
