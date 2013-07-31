package scheduleVisualization;

import java.awt.Color;
import java.awt.Component;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTextField;

import validation.WarningService;
import basic.Class;
import basic.Classroom;
import basic.Professor;
import basic.SlotRange;

public class DisponibilityFormatter implements TableFormatter{

	private Class theClass;
	private Classroom theClassroom;
	private WarningService warningService;
	
	public DisponibilityFormatter(Classroom room, Class theClass, WarningService service){
		this.theClass = theClass;
		warningService = service;
		theClassroom = room;
	}
	
	public void formatCell(JLabel cell, int day, int slot) {
		Color cellColor = Color.green;
		if(!warningService.isClassroomFree(theClass.getId(), theClassroom, new SlotRange(day, slot, slot + 1, null)))
			cellColor = Color.yellow;
		for(Professor p : theClass.getProfessors()){
			if(!warningService.isProfessorFree(theClass.getId(), p, new SlotRange(day, slot, slot + 1, null)))
				cellColor = Color.red;
		}
		
		cell.setBackground(cellColor);
		cell.add(new JTextField("testando"));
	}

	public Component getPopupContent(int day, int slot) {
		return null;
	}

	public Object getCellContent(int day, int slot) {
		return "<html><p style=\"color:red\">Ola</p><p style=\"color:white\">Xau</p></html>"; 
	}
}
