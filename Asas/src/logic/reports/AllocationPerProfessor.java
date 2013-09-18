package logic.reports;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import data.persistentEntities.Professor;
import data.persistentEntities.Class;
import data.readers.ProfessorPictureDictionary;

import logic.dto.WorkloadReportList;
import logic.html.BTag;
import logic.html.BrTag;
import logic.html.FontTag;
import logic.html.HTag;
import logic.html.HtmlDocument;
import logic.html.ImgTag;
import logic.html.TableTag;
import logic.html.TdTag;
import logic.html.TrTag;
import logic.services.ClassService;
import logic.services.ProfessorService;
import logic.services.ProfessorWorkLoadService;

public class AllocationPerProfessor {
	private List<ProfessorAllocation> allocatedProfessors;
	private List<Professor> notAllocatedProfessors;
	private ClassService classService;
	private ProfessorService professorService;
	ProfessorPictureDictionary profPictures;
	
	public AllocationPerProfessor(){
		profPictures = new ProfessorPictureDictionary();
		classService = ClassService.createServiceFromCurrentState();
		professorService = ProfessorService.createServiceFromCurrentState();
		fillProfessorAllocation();
	}

	private void fillProfessorAllocation() {
		allocatedProfessors = new LinkedList<ProfessorAllocation>();
		notAllocatedProfessors = new LinkedList<Professor>();
		WorkloadReportList workloadList = ProfessorWorkLoadService.createServiceFromCurrentState()
					.calculateProfessorWorkload();
		
		for(Professor prof : professorService.all()){
			ProfessorAllocation alloc = new ProfessorAllocation(profPictures, workloadList.getReportFor(prof));
			for(Class c : classService.all()){
				if(c.getProfessors().contains(prof))
					alloc.addClass(c);
			}
			
			if(!alloc.getClasses().isEmpty())
				allocatedProfessors.add(alloc);
			else
				notAllocatedProfessors.add(prof);
		}
	}
		
	
	public HtmlDocument getHtmlRepresentation(){
		HtmlDocument html = new HtmlDocument();
		html.setTitle("Universidade Federal de Pernambuco");
		addHead(html);
		createAllocatedTable(html);
		createNotAllocatedTable(html);
		return html;
	}
	
	private void createNotAllocatedTable(HtmlDocument html) {
		createTableTitle(html, "Relatório de Docentes Ausentes e Não Alocados");
		TableTag table = TableTag.defaulTable();
		table.addChildElement(createTableHeader("Professor", "Situação"));
		
		for(Professor p : notAllocatedProfessors){
			TrTag profRow = new TrTag();
			profRow.setBackgroundColor("FFFF99");
			TdTag profCell = new TdTag();
			
			ImgTag profImg = new ImgTag();
			profImg.setHeight(123);
			profImg.setWidth(97);
			profImg.setSrc(profPictures.getPicture(p.getEmail()));
			profCell.addChildElement(profImg);
			
			profCell.addChildElement(new BrTag());
			profCell.addChildElement(new BTag(p.getName()));
			
			
			TdTag situationCell = new TdTag();
			if(p.isAway()) 
				situationCell.addInnerText("Afastado(a) temporariamente");
			else if(p.isTemporary())
				situationCell.addInnerText("Professor(a) temporário");
			else
				situationCell.addInnerText("Não alocado!");
			
			profRow.addChildElement(profCell);
			profRow.addChildElement(situationCell);
			
			table.addChildElement(profRow);
		}
		html.addChildElement(table);
	}

	private void addHead(HtmlDocument html){
		
	}
	
	private void createTableTitle(HtmlDocument doc, String titleText){
		HTag title = new HTag(2);
		title.addAttribute("align", "center");
		FontTag font = new FontTag();
		font.setFace("Arial");
		font.setSize(4);
		font.addInnerText(titleText);
		title.addChildElement(font);
		doc.addChildElement(title);
	}
	
	private void createAllocatedTable(HtmlDocument doc) {
		createTableTitle(doc, "Relatório de Alocação de Disciplinas");
		
		TableTag table = TableTag.defaulTable();
		doc.addChildElement(table);
		
		table.addChildElement(createTableHeader("Professor", "Alocação"));
		
		for(ProfessorAllocation alloc : allocatedProfessors)
			table.addChildElement(alloc.getHtmlRepresentation());
	}

	private TrTag createTableHeader(String leftCol, String rightCol) {
		TrTag title = new TrTag();
		title.addStyle("color", "yellow");
		title.addStyle("background-color", "gray");
		
		TdTag profCell = new TdTag();
		TdTag allocCell = new TdTag();
		title.addChildElement(profCell);
		title.addChildElement(allocCell);
		
		profCell.addInnerText(leftCol);
		allocCell.addInnerText(rightCol);
		return title;
	}

	public void saveToFile(File f){
		HtmlDocument doc = getHtmlRepresentation();
		
		try{
			PrintWriter writer = new PrintWriter(f);
			writer.print(doc.getHtmlString().toString());
			writer.close();
		}catch(FileNotFoundException ex){
			ex.printStackTrace();
		}
	}
}
