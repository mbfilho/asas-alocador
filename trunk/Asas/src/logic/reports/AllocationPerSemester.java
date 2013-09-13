package logic.reports;

import java.awt.Color;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import utilities.CollectionUtil;
import utilities.StringUtil;

import logic.html.CssConstants;
import logic.html.HTag;
import logic.html.HtmlDocument;
import logic.html.HtmlElement;
import logic.html.TableTag;
import logic.html.TdTag;
import logic.html.TrTag;
import logic.services.ClassService;
import data.persistentEntities.Class;
import data.persistentEntities.Classroom;
import data.persistentEntities.SlotRange;

public class AllocationPerSemester {
	private TreeMap<Integer, List<Class>> classesPerSemester;
	private boolean isCc;
	
	public AllocationPerSemester(boolean cc){
		isCc = cc;
		classesPerSemester = new TreeMap<Integer, List<Class>>();
		createReport();
	}
	
	private void createReport(){
		ClassService classService = new ClassService();
		
		for(Class c : classService.all()){
			addToMapping(getSemesterForReport(c), c);
		}
	}
	
	private void addToMapping(int semester, Class c) {
		if(semester == -1) return;
		if(!classesPerSemester.containsKey(semester))
			classesPerSemester.put(semester, new LinkedList<Class>());
		
		classesPerSemester.get(semester).add(c);
	}

	private int getSemesterForReport(Class theClass){
		if(isCc) return theClass.getCcSemester();
		else return theClass.getEcSemester();
	}
	
	public HtmlDocument getHtmlRepresentation(){
		HtmlDocument document = new HtmlDocument();
		createRepresentationForSemesters(document);
		return document;
	}

	private void createRepresentationForSemesters(HtmlDocument document) {
		for(Entry<Integer, List<Class>> pair : classesPerSemester.entrySet()){
			document.addChildElement(createSemesterTableTitle(pair.getKey()));
			document.addChildElement(createTableForSemester(pair.getKey(), pair.getValue()));
			document.addChildElement(createSmallTable(pair.getValue()));
		}
	}

	private HtmlElement createSemesterTableTitle(int semester) {
		HTag title = new HTag(2);
		title.addInnerText(semester + "º período");
		return title;
	}

	private HtmlElement createTableForSemester(int semester, List<Class> classes) {
		TableTag table = new TableTag();
		table.setTextAlign(CssConstants.TEXT_ALIGN_CENTER);
		table.setFloating(CssConstants.FLOAT_LEFT);
		table.setMarginRight("20px");
		table.setCellSpacing(0);
		table.setBorder(0);
		
		TrTag head = createRow("Disciplina", "Horário", "Professor", "Sala");
		head.setBackgroundColor("DDD9D9");
		
		table.addChildElement(head);
		
		for(Class c : classes)
			table.addChildElement(createRowForClass(c));
		
		return table;
	}

	private HtmlElement createRowForClass(Class c) {
		TrTag row = new TrTag();
		row.addChildElement(createCellWithTopAndRightBorder(c.completeName()).setBackgroundColor(c.getColor()));
		row.addChildElement(createCellWithTopAndRightBorder(formatSlots(c)));
		row.addChildElement(createCellWithTopAndRightBorder(formatProfessors(c)));
		row.addChildElement(new TdTag(formatRooms(c)).setBorderTop(Color.black, CssConstants.BORDER_SOLID, "1px"));
		return row;
	}
	
	private HtmlElement createCellWithTopAndRightBorder(String content){
		return new TdTag(content)
					.setBorderRight(Color.black, CssConstants.BORDER_SOLID, "1px")
					.setBorderTop(Color.black, CssConstants.BORDER_SOLID, "1px");
	}

	private String formatRooms(Class c) {
		List<Classroom> rooms = c.getRoomsOrderedBySlot();
		if(CollectionUtil.distinct(rooms).size() == 1)
			return rooms.get(0).getName();
		
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
	
	private HtmlElement createSmallTable(List<Class> classes) {
		TableTag table = new TableTag();
		table.setTextAlign(CssConstants.TEXT_ALIGN_CENTER);
		table.setWidth("350px");
		table.setBorder(1);
		table.setCellSpacing(0);
		
		table.addChildElement(createRow("", "seg", "ter", "qua", "qui", "sex"));
		String rowLabels[] = {"8-10", "10-12", "13-15", "15-17"};
		HashSet<Color> smallTable[][] = calculateColorsForSmallTable(classes);
		
		for(int i = 0; i < smallTable.length; ++i){
			TrTag row = new TrTag();
			row.addChildElement(new TdTag(rowLabels[i]));
			for(int j = 0; j < smallTable[i].length; ++j){
				TdTag cell = new TdTag();
				cell.addChildElement(createTableOfColors(smallTable[i][j]));
				row.addChildElement(cell);
			}
			table.addChildElement(row);
		}
		
		return table;
	}

	private HtmlElement createTableOfColors(HashSet<Color> colors) {
		TableTag tableOfColors = new TableTag();
		tableOfColors.setWidth("100%");
		tableOfColors.setBorder(0);
		tableOfColors.setCellSpacing(0);
		TrTag singleRow = new TrTag();
		
		for(Color c : colors){
			TdTag cell = new TdTag("&nbsp;");
			cell.setBackgroundColor(c);
			singleRow.addChildElement(cell);
		}
		tableOfColors.addChildElement(singleRow);
		return tableOfColors;
	}

	private HashSet<Color>[][] calculateColorsForSmallTable(List<Class> classes) {
		HashSet<Color> smallTable[][] = new HashSet[4][5];
		for(int i = 0; i < smallTable.length; ++i){
			for(int j = 0; j < smallTable[i].length; ++j)
				smallTable[i][j] = new HashSet<Color>();
		}
		
		for(Class c : classes){
			for(SlotRange range : c.getSlots()){
				for(int i = range.getStartSlot(); i <= range.getEndSlot(); ++i)
					smallTable[getSmallTableRow(i)][getSmallTableColumn(range)].add(c.getColor());
			}
		}
		
		return smallTable;
	}
	
	//Nao suporta o domingo
	private int getSmallTableColumn(SlotRange slot){
		return slot.getDay() - 1;
	}

	//Não suporta o slot das 7-8 nem o das 12-13
	private int getSmallTableRow(int slot){
		if(slot > 5) //pular 12-13
			return (slot - 2) / 2;
		else
			return (slot - 1) / 2;
	}
	
	private TrTag createRow(String ... cells){
		TrTag row = new TrTag();
		for(String cell : cells)
			row.addChildElement(new TdTag(cell));
		
		return row;
	}
	
}
