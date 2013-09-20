package logic.reports.perProfessor;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import utilities.StringUtil;

import logic.dto.WorkloadReport;
import logic.html.ATag;
import logic.html.BTag;
import logic.html.BrTag;
import logic.html.HtmlElement;
import logic.html.HtmlPlainContent;
import logic.html.ITag;
import logic.html.ImgTag;
import logic.html.PTag;
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
	private WorkloadReport professorWorkload;

	public ProfessorAllocation(ProfessorPictureDictionary pictures, WorkloadReport workload){
		classes = new LinkedList<Class>();
		professorWorkload = workload;
		profPictures = pictures;
		professor = workload.professor;
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

	public HtmlElement getHtmlRepresentation(boolean isEvenRow) {
		TrTag tr = new TrTag();
		if(professor.getEmail() != null)
			tr.setId(professor.getEmail());
		tr.addChildElement(createProfessorCell(professor, profPictures));
		tr.addChildElement(createClassesCell(isEvenRow));

		return tr;
	}

	public static TdTag createProfessorCell(Professor professor, ProfessorPictureDictionary profPictures){
		TdTag profInfoCell = new TdTag();
		profInfoCell.addCssClass(CssRulesForTableCreator.getProfessorCellClassName()).setWidth("220px");

		ImgTag img = new ImgTag();
		img.setBorder(0);
		img.setWidth(97);
		img.setHeight(123);
		img.setSrc(profPictures.getPicture(professor.getEmail()));

		PTag paragraph = new PTag();

		paragraph.addChildElement(new BTag(professor.getName()));
		paragraph.addChildElement(new BrTag());
		paragraph.addInnerText("Departamento: " + professor.getDpto());
		
		if(!StringUtil.isNullOrEmpty(professor.getCargo())){
			paragraph.addChildElement(new BrTag());
			paragraph.addInnerText(professor.getCargo());
		}

		profInfoCell.addChildElement(img);
		profInfoCell.addChildElement(paragraph);

		return profInfoCell;
	}

	private HtmlElement createClassesCell(boolean isEven) {
		TdTag classCell = new TdTag();
		classCell.addCssClass(CssRulesForTableCreator.getProfessorDetailsCellClassName());
		for(Class c : classes){
			classCell.addChildElement(new BTag(c.completeName()));
			classCell.addInnerText(c.getCourse());
			classCell.addChildElement(new BrTag());

			addOthersProfessorsLine(classCell, c);
			classCell.addInnerText("Sala: " + getRooms(c));

			addSlotsLine(classCell, c);

			classCell.addChildElement(new BrTag()).addChildElement(new BrTag());
		}


		if(isEven)
			classCell.addCssClass(CssRulesForTableCreator.getEvenRowClassName());
		else
			classCell.addCssClass(CssRulesForTableCreator.getOddRowClassName());

		
		addChInformation(classCell);
		return classCell;
	}
	
	private void addChInformation(TdTag classCell) {
		classCell.addChildElement(new HtmlPlainContent("Carga Atual: ")).addChildElement(new BTag(professorWorkload.workload + ""));
		classCell.addChildElement(new BrTag());
		classCell.addChildElement(new HtmlPlainContent("Carga Anterior: ")).addChildElement(new BTag(professorWorkload.professor.getLastSemesterWorkload() + ""));
	}

	private void addSlotsLine(HtmlElement cell, Class c) {
		cell.addInnerText(" - Horários: ");
		for(SlotRange r : c.getSlots()){
			cell.addChildElement(new ITag(r.toString()));
		}
	}

	private void addOthersProfessorsLine(HtmlElement cell, Class c) {
		List<Professor> others = getOtherProfessors(c);
		if(!others.isEmpty()){
			cell.addInnerText(" [com ");
			for(Professor ot : others)
				cell.addChildElement(new ATag(ot.getName()).setLinkToOtherElement(ot.getEmail()));
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
