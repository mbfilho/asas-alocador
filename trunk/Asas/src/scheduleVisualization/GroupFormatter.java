package scheduleVisualization;

import groupMaker.Group;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import utilities.Constants;
import basic.Class;
import basic.Professor;
import basic.SlotRange;

public class GroupFormatter implements TableFormatter {

	private Vector<ScheduleSlot> schedule[][]; 
	private JPanel popups[][];
	
	public GroupFormatter(Group g){
		schedule = g.schedule.getSchedule();
		popups = new JPanel[Constants.SLOTS][Constants.DAYS];
		createPopups();
	}
	
	private void createPopups(){
		for(int s = 0; s < Constants.SLOTS; ++s){
			for(int d = 0; d < Constants.DAYS; ++d){
				if(schedule[s][d].size() > 1){
					Vector<ScheduleSlot> scheduled = schedule[s][d];
					JPanel panel = popups[s][d] = new JPanel();
					panel.setLayout(new GridLayout(1+scheduled.size(), 1));
					panel.add(new JLabel("Turmas conflitantes:"));
					for(ScheduleSlot ss : scheduled){
						JLabel turma = new JLabel(ss.theClass.completeName());
						turma.setOpaque(true);
						turma.setBackground(ss.theClass.getColor());
						panel.add(turma);
					}
				}else if(schedule[s][d].size() == 1){
					Class scheduled = schedule[s][d].firstElement().theClass;
					JPanel panel = popups[s][d] = new JPanel();
					panel.setLayout(new GridLayout(5 + scheduled.getProfessors().size() + scheduled.getSlots().size(), 1));
					panel.add(new JLabel(scheduled.completeName()));
					panel.add(new JSeparator(SwingConstants.HORIZONTAL));
					panel.add(new JLabel("Professores:"));
					for(Professor p : scheduled.getProfessors()) panel.add(new JLabel(p.getName()));
					panel.add(new JSeparator(SwingConstants.HORIZONTAL));
					panel.add(new JLabel("Horários:"));
					for(SlotRange r : scheduled.getSlots())	panel.add(new JLabel(r.toString()));
				}
			}
		}
	}
	
	public void formatCell(JLabel cell, int day, int slot) {
		Vector<ScheduleSlot> scheduled = schedule[slot][day];
		if(scheduled.size() > 1){
			cell.setBackground(Color.RED);
			cell.setForeground(Color.white);
		}else if(scheduled.size() == 1){
			cell.setBackground(scheduled.get(0).theClass.getColor());
			cell.setToolTipText(scheduled.get(0).theClass.completeName());
		}
	}

	public Component getPopupContent(int day, int slot) {
		return popups[slot][day];
	}

	public Object getCellContent(int day, int slot) {
		if(schedule[slot][day].size() == 1) return schedule[slot][day].get(0).theClass.completeName();
		if(schedule[slot][day].size() > 1) return "Conflito: clique aqui.";
		return "";
	}

}
