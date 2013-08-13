package scheduleTable;

import groupMaker.Group;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import scheduleVisualization.Schedule;
import scheduleVisualization.ScheduleSlot;
import utilities.Constants;
import basic.Class;
import basic.Professor;
import basic.SlotRange;

public class GeneralGroupModel extends GeneralScheduleModel{
	private static final long serialVersionUID = 4209162051390757299L;

	public GeneralGroupModel(Group g){
		configureTable(g.schedule.getSchedule());
	}
	
	public GeneralGroupModel(){}
	
	protected void configureTable(Vector<ScheduleSlot> schedule[][]){
		buildTable(schedule);
		buildPopups(schedule);	
	}
	
	protected boolean solveConflict(Vector<ScheduleSlot> conflictingScheduleSlots, int row, int column){
		cellState[row][column].setBackColor(Color.yellow);
		cellState[row][column].setFontColor(Color.black);
		cellState[row][column].setValue("Conflitos autorizados.");
		cellState[row][column].setTooltip("Conflitos autorizados.");
		return false;
	}
	
	protected void buildPopups(Vector<ScheduleSlot> schedule[][]){
		for(int s = 0; s < Constants.SLOTS; ++s){
			for(int d = 0; d < Constants.DAYS; ++d){
				if(schedule[s][d].size() > 1){
					Vector<ScheduleSlot> scheduled = schedule[s][d];
					popups[s][d] = new JPanel(new GridLayout());
					popups[s][d].add(new JLabel("Turmas conflitantes:"));
					
					for(ScheduleSlot slot : scheduled)
						addClassInfoToPanel(slot.theClass, popups[s][d]);
					
				}else if(schedule[s][d].size() == 1){
					Class scheduled = schedule[s][d].firstElement().theClass;
					popups[s][d] = new JPanel(new GridLayout());
					addClassInfoToPanel(scheduled, popups[s][d]);
				}
			}
		}
	}

	private JLabel createColoredLabel(String text, Color c){
		JLabel label = new JLabel(text);
		label.setBackground(c);
		label.setOpaque(true);
		return label;
	}
	
	private JLabel createTitleLabel(String text, Color c){
		return createColoredLabel("<html><b>" + text + "</b></html>", c);
	}
	
	private void addSeparatorToPanel(JPanel thePanel){
		thePanel.add(new JSeparator(SwingConstants.HORIZONTAL));
	}
	
	private void addClassInfoToPanel(Class theClass, JPanel thePanel){
		GridLayout layout = (GridLayout) thePanel.getLayout();
		int rows = 6 + theClass.getProfessors().size() + theClass.getSlots().size();
		layout.setRows(layout.getRows() + rows);
		addSeparatorToPanel(thePanel);
		thePanel.add(createTitleLabel(theClass.completeName(), theClass.getColor()));
		addSeparatorToPanel(thePanel);
		thePanel.add(createTitleLabel("Professores:", theClass.getColor()));
		for(Professor p : theClass.getProfessors()) 
			thePanel.add(createColoredLabel(p.getName(), theClass.getColor()));
		addSeparatorToPanel(thePanel);
		thePanel.add(createTitleLabel("Horários:", theClass.getColor()));
		for(SlotRange r : theClass.getSlots())	
			thePanel.add(createColoredLabel(r.getName(), theClass.getColor()));
	}
	
	protected void buildTable(Vector<ScheduleSlot> schedule[][]){
		for(int i = 0; i < Constants.SLOTS; ++i){
			for(int j = 0; j < Constants.DAYS; ++j){
				int row = i, column = j + 1;
				cellState[row][column].setValue("");
				
				if(schedule[i][j].size() > 1){
					if(!solveConflict(schedule[i][j], row, column)){
						cellState[row][column].setBackColor(Color.red);
						cellState[row][column].setFontColor(Color.white);
						cellState[row][column].setValue("Conflito. Clique aqui.");
					}
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
