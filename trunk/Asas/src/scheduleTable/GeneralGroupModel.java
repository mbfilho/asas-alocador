package scheduleTable;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import scheduleVisualization.ScheduleSlot;
import utilities.Constants;
import basic.Class;
import basic.Professor;
import basic.SlotRange;

public class GeneralGroupModel extends GeneralScheduleModel{
	private static final long serialVersionUID = 4209162051390757299L;

	protected void configureTable(Vector<ScheduleSlot> schedule[][]){
		buildTable(schedule);
		buildPopups(schedule);	
	}
	
	protected void solveConflict(Vector<ScheduleSlot> conflictingScheduleSlots, int row, int column){
		cellState[row][column].setBackColor(Color.yellow);
		cellState[row][column].setFontColor(Color.black);
		cellState[row][column].setValue("Conflitos autorizados.");
		cellState[row][column].setTooltip("Conflitos autorizados.");
	}
	
	protected void buildPopups(Vector<ScheduleSlot> schedule[][]){
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
					for(SlotRange r : scheduled.getSlots())	panel.add(new JLabel(r.getName()));
				}
			}
		}
	}
	
	protected void buildTable(Vector<ScheduleSlot> schedule[][]){
		for(int i = 0; i < Constants.SLOTS; ++i){
			for(int j = 0; j < Constants.DAYS; ++j){
				int row = i, column = j + 1;
				cellState[row][column].setValue("");
				
				if(schedule[i][j].size() > 1){
					cellState[row][column].setBackColor(Color.red);
					cellState[row][column].setFontColor(Color.white);
					cellState[row][column].setValue("Conflito. Clique aqui.");
					solveConflict(schedule[i][j], row, column);
				}else if(schedule[i][j].size() == 1){
					Class theClass = schedule[i][j].firstElement().theClass; 
					cellState[row][column].setBackColor(theClass.getColor());
					cellState[row][column].setFontColor(Color.black);
					cellState[row][column].setValue(theClass.completeName());
					cellState[row][column].setTooltip(theClass.completeName());
				}else{
					cellState[row][column].setBackColor(Color.white);
					cellState[row][column].setFontColor(Color.black);
				}
			}
		}
	}
	
	protected Vector<Class> getClassesFromConflictingScheduleSlot(Vector<ScheduleSlot> slots){
		Vector<Class> classes = new Vector<Class>();
		for(ScheduleSlot slot : slots) classes.add(slot.theClass);
		return classes;
	}
}
