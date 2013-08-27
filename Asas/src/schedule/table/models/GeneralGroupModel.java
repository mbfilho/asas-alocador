package schedule.table.models;


import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import logic.grouping.Group;
import logic.schedule.Schedule;

import data.persistentEntities.Class;
import data.persistentEntities.Professor;
import data.persistentEntities.SlotRange;

import utilities.Constants;

public class GeneralGroupModel extends GeneralScheduleModel{
	private static final long serialVersionUID = 4209162051390757299L;
	protected Schedule theSchedule;
	
	public GeneralGroupModel(Group g){
		this.theSchedule = g.schedule;
		configureTable(g.schedule);
	}
	
	public GeneralGroupModel(){}
	
	protected void configureTable(Schedule schedule){
		buildTable(schedule);
	}
	
	protected boolean solveConflict(List<Class> conflictingClasses, int row, int column){
		cellState[row][column].setBackColor(Color.yellow);
		cellState[row][column].setFontColor(Color.black);
		cellState[row][column].setValue("Conflitos autorizados.");
		cellState[row][column].setTooltip("Conflitos autorizados.");
		return false;
	}
	
	private JPanel buildPopupForSlot(int slot, int day){
		JPanel panel = null;
		
		if(theSchedule.hasConflict(slot, day)){
			panel = new JPanel(new GridLayout());
			panel.add(new JLabel("Turmas conflitantes:"));
			
			for(Class conflicted: theSchedule.getClassesForRead(slot, day))
				addClassInfoToPanel(conflicted, panel);
			
		}else if(!theSchedule.isEmptySlot(slot, day)){
			Class scheduled = theSchedule.getSingleClass(slot, day);
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
	
	protected void buildTable(Schedule schedule){
		for(int i = 0; i < Constants.SLOTS; ++i){
			for(int j = 0; j < Constants.DAYS; ++j){
				int row = i, column = j + 1;
				cellState[row][column].setValue("");
				
				if(schedule.hasConflict(i, j)){
					if(!solveConflict(schedule.getClassesForRead(i, j), row, column)){
						cellState[row][column].setBackColor(Color.red);
						cellState[row][column].setFontColor(Color.white);
						cellState[row][column].setValue("Conflito. Clique aqui.");
					}
				}else if(!schedule.isEmptySlot(i,j)){
					Class theClass = schedule.getSingleClass(i, j); 
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
}
