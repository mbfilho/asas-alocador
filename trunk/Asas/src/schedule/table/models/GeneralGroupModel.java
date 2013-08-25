package schedule.table.models;


import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import logic.dto.schedule.ScheduleSlot;
import logic.dto.schedule.grouping.Group;

import data.persistentEntities.Class;
import data.persistentEntities.Professor;
import data.persistentEntities.SlotRange;

import utilities.Constants;

public class GeneralGroupModel extends GeneralScheduleModel{
	private static final long serialVersionUID = 4209162051390757299L;
	protected List<ScheduleSlot> theSchedule[][];
	
	public GeneralGroupModel(Group g){
		this.theSchedule = g.schedule.getSchedule();
		configureTable(g.schedule.getSchedule());
	}
	
	public GeneralGroupModel(){}
	
	protected void configureTable(List<ScheduleSlot> schedule[][]){
		this.theSchedule = schedule;
		buildTable(schedule);
	}
	
	protected boolean solveConflict(List<ScheduleSlot> conflictingScheduleSlots, int row, int column){
		cellState[row][column].setBackColor(Color.yellow);
		cellState[row][column].setFontColor(Color.black);
		cellState[row][column].setValue("Conflitos autorizados.");
		cellState[row][column].setTooltip("Conflitos autorizados.");
		return false;
	}
	
	private JPanel buildPopupForSlot(int slot, int day){
		JPanel panel = null;
		
		if(theSchedule[slot][day].size() > 1){
			List<ScheduleSlot> scheduled = theSchedule[slot][day];
			panel = new JPanel(new GridLayout());
			panel.add(new JLabel("Turmas conflitantes:"));
			
			for(ScheduleSlot slotScheduled : scheduled)
				addClassInfoToPanel(slotScheduled.theClass, panel);
			
		}else if(theSchedule[slot][day].size() == 1){
			Class scheduled = theSchedule[slot][day].get(0).theClass;
			panel = new JPanel(new GridLayout());
			addClassInfoToPanel(scheduled, panel);
		}
		
		return panel;
	}
	
	public Component getPopupContent(int row, int column) {
		return buildPopupForSlot(row, column-1);
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
	
	protected void buildTable(List<ScheduleSlot> schedule[][]){
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
					Class theClass = schedule[i][j].get(0).theClass; 
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
	
	protected Vector<Class> getClassesFromConflictingScheduleSlot(List<ScheduleSlot> slots){
		Vector<Class> classes = new Vector<Class>();
		for(ScheduleSlot slot : slots) classes.add(slot.theClass);
		return classes;
	}
}
