package logic.reports;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import utilities.StringUtil;

import logic.html.BTag;
import logic.html.BrTag;
import logic.html.FontTag;
import logic.html.HtmlElement;
import logic.html.HtmlPlainContent;
import logic.html.ITag;
import logic.html.ImgTag;
import logic.html.PTag;
import logic.html.TableTag;
import logic.html.TdTag;
import logic.html.TrTag;

import data.persistentEntities.Classroom;
import data.persistentEntities.Professor;
import data.persistentEntities.Class;
import data.persistentEntities.SlotRange;
import data.readers.ProfessorPictureDictionary;

public class ProfessorAllocation {
	private Professor professor;
	private List<Class> classes;
	private ProfessorPictureDictionary profPictures;
	
	public ProfessorAllocation(ProfessorPictureDictionary pictures, Professor p){
		professor = p;
		classes = new LinkedList<Class>();
		profPictures = pictures;
	}
	
	public void addClass(Class c){
		classes.add(c);
	}
	
	public Professor getProfessor(){
		return professor;
	}
	
	public List<Professor> getOtherProfessors(Class c){
		List<Professor> others = new LinkedList<Professor>();
		for(Professor p : c.getProfessors()){
			if(p != professor)
				others.add(p);
		}
		
		return others;
	}
	
	public List<Class> getClasses(){
		return classes;
	}
	
	public double getCurrentCh(){
		double ch = 0;
		for(Class c : classes){
			if(c.getCh2() == 45 || !getOtherProfessors(c).isEmpty()) 
				ch += 0.5;
			else
				ch += 1;
		}
		
		return ch;
	}

	public HtmlElement getHtmlRepresentation() {
		TrTag tr = new TrTag();
		tr.addAttribute("bgcolor", "FFFF99");
		tr.addChildElement(createProfessorCell());
		tr.addChildElement(createClassesCell());
		
		return tr;
	}

	private HtmlElement createProfessorCell(){
		TdTag profInfoCell = new TdTag();
		
		ImgTag img = new ImgTag();
		img.setBorder(0);
		img.setWidth(97);
		img.setHeight(123);
		img.setSrc(profPictures.getPicture(professor.getEmail()));
		
		BrTag br = new BrTag();
		
		BTag name = new BTag(professor.getName() + " -- Dpto: " + professor.getDpto());
		HtmlPlainContent cargo = new HtmlPlainContent(professor.getCargo());
		
		profInfoCell.addChildElement(img);
		profInfoCell.addChildElement(br);
		profInfoCell.addChildElement(name);
		profInfoCell.addChildElement(br);
		profInfoCell.addChildElement(cargo);

		return profInfoCell;
	}
	
	private void addChLine(TdTag cell) {
		TableTag table = new TableTag();
		table.setBorder(0);
		table.setWidth("100%");
		cell.addChildElement(table);
		
		TrTag row = new TrTag();
		table.addChildElement(row);
		
		TdTag ch = new TdTag();
		row.addChildElement(ch);
		
		FontTag font = FontTag.defaultFontTag();
		font.addInnerText("Carga Atual: ");
		font.addChildElement(new BTag(getCurrentCh() + ""));
		ch.addChildElement(font);
	}

	private HtmlElement createClassesCell() {
		TdTag classCell = new TdTag();
		classCell.addStyle("text-align", "left");
		
		for(Class c : classes){
			classCell.addChildElement(new BTag(c.completeName()));
			classCell.addInnerText(c.getCourse());
			classCell.addChildElement(new BrTag());
			
			addOthersProfessorsLine(classCell, c);
			classCell.addInnerText("Sala: " + getRooms(c));
			
			addSlotsLine(classCell, c);
			
			classCell.addChildElement(new PTag());
		}
		
		addChLine(classCell);
		return classCell;
	}

	private void addSlotsLine(HtmlElement cell, Class c) {
		cell.addInnerText(" - Horários: ");
		for(SlotRange r : c.getSlots())
			cell.addChildElement(new ITag(r.toString()));
	}

	private void addOthersProfessorsLine(HtmlElement cell, Class c) {
		List<Professor> others = getOtherProfessors(c);
		if(!others.isEmpty()){
			cell.addInnerText(" [com ");
			for(Professor ot : others)
				cell.addChildElement(new ITag(ot.getName()));
			cell.addInnerText("]");
			cell.addChildElement(new BrTag());
		}		
	}

	private String getRooms(Class c) {
		List<String> roomNames = new LinkedList<String>();
		HashSet<String> mark = new HashSet<String>();
		for(Classroom room : c.getAllRooms()){
			roomNames.add(room.getName());
			mark.add(room.getName());
		}
		
		if(mark.size() == 1)
			return roomNames.get(0);
		else
			return StringUtil.joinListWithSeparator(roomNames, "/");
	}
}
