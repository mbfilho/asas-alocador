package logic.reports;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.Map.Entry;

import logic.ColorPoolForNames;
import logic.html.CssConstants;
import logic.html.HTag;
import logic.html.HtmlDocument;
import logic.html.HtmlElement;
import logic.html.TableTag;
import logic.html.TdTag;
import logic.html.TrTag;
import utilities.CollectionUtil;
import utilities.StringUtil;

import data.persistentEntities.Class;
import data.persistentEntities.Classroom;
import data.persistentEntities.SlotRange;

public class PerSemesterHtmlCreator {
	private TreeMap<Integer, List<Class>> classesPerSemester;
	private ColorPoolForNames colorsToClasses;
	private String course;
	
	public PerSemesterHtmlCreator(String course, TreeMap<Integer, List<Class>> mapping) {
		this.classesPerSemester = mapping;
		this.course = course;
	}
	
	public HtmlDocument getHtmlRepresentation(){
		HtmlDocument document = new HtmlDocument();
		HTag title = new HTag(2);
		title.addInnerText("Relatório de Alocação de Disciplinas Obrigatórias do Curso de " + course);
		title.setTextAlign(CssConstants.TEXT_ALIGN_CENTER);
		document.addChildElement(title);
		document.addChildElement(createBigTable());
		return document;
	}

	private TableTag createBigTable() {
		TableTag bigTable = new TableTag();
		bigTable.showEmptyCells(true).setTextAlign(CssConstants.TEXT_ALIGN_CENTER);
		bigTable.setCellSpacing(0).setMarginRight("20px");
		bigTable.setBorderCollapse(CssConstants.TABLE_BORDER_COLLAPSE).setBorder(1);
		bigTable.addStyle("border", "none");
		
		for(Entry<Integer, List<Class>> pair : classesPerSemester.entrySet()){
			bigTable.addChildElement(createSemesterTitle(pair.getKey()));
			createRowsForSemester(pair.getKey(), pair.getValue(), bigTable);
		}
		return bigTable;
	}

	private HtmlElement createSemesterTitle(int semester) {
		HtmlElement title = new HTag(2).addInnerText(semester + "º período");
		title.setMarginTop("10px").setMarginBottom("0").setTextAlign(CssConstants.TEXT_ALIGN_LEFT);
		
		HtmlElement cell = new TdTag().addChildElement(title).setBorderOff();
		return new TrTag().addChildElement(cell);
	}

	private void createRowsForSemester(int semester, List<Class> classes, TableTag bigTable) {
		colorsToClasses = createPoolOfColors(classes); 
		
		TrTag head = createRow("Disciplina", "Horário", "Professor", "Sala", "", "Horário", "seg", "ter", "qua", "qui", "sex");
		head.setBackgroundColor("DDD9D9");
		bigTable.addChildElement(head);
		
		SmallSlotTable smallTable = new SmallSlotTable(classes, colorsToClasses);
		int rows = Math.max(classes.size(), smallTable.getRowCount());
		
		for(int r = 0; r < rows; ++r){
			HtmlElement row;
			if(r < classes.size())
				row = createRowForClass(classes.get(r));
			else
				row = createRow("", "", "", "");
			
			row.addChildElement(TdTag.emptyCell().setBorderOff());
			
			if(r < smallTable.getRowCount()){
				for(TdTag cell : smallTable.getNthRow(r))
					row.addChildElement(cell);
			}
			bigTable.addChildElement(row);
		}
	}

	private ColorPoolForNames createPoolOfColors(List<Class> classes) {
		return new ColorPoolForNames(CollectionUtil.createNameList(classes));
	}

	private HtmlElement createRowForClass(Class c) {
		TrTag row = new TrTag();
		row.addChildElement(new TdTag(c.completeName()).setBackgroundColor(colorsToClasses.getColor(c.getName())));
		row.addChildElement(new TdTag(formatSlots(c)));
		row.addChildElement(new TdTag(formatProfessors(c)));
		row.addChildElement(new TdTag(formatRooms(c)));
		return row;
	}

	private String formatRooms(Class c) {
		List<Classroom> rooms = c.getRoomsOrderedBySlot();
		
		return StringUtil.joinListWithSeparator(rooms, "/");
	}

	private String formatProfessors(Class c) {
		return StringUtil.joinListWithSeparator(c.getProfessors(), "/");
	}

	private String formatSlots(Class c) {
		List<String> slotsNames = new LinkedList<String>();
		for(SlotRange slot : c.getSlots())
			slotsNames.add(slot.getNameWithoutRoom());
		
		return StringUtil.joinListWithSeparator(slotsNames, "/");
	}
	
	private TrTag createRow(String ... cells){
		TrTag row = new TrTag();
		for(String cell : cells)
			row.addChildElement(new TdTag(cell));
		
		return row;
	}
}