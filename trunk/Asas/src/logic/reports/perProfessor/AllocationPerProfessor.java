package logic.reports.perProfessor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import data.persistentEntities.Professor;
import data.persistentEntities.Class;
import data.readers.ProfessorPictureDictionary;

import logic.dto.WorkloadReportList;
import logic.html.CssConstants;
import logic.html.DivTag;
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
		html.addToHead(new CssRulesForTableCreator().getStyleTag());
		addDocumentHead(html);
		createAllocatedTable(html);
		createNotAllocatedTable(html);
		return html;
	}
	
	private void createNotAllocatedTable(HtmlDocument html) {
		createTableTitle(html, "Relatório de Docentes Ausentes e Não Alocados");
		TableTag table = new TableTag();
		boolean isEvenRow = true;
		
		for(Professor p : notAllocatedProfessors){
			isEvenRow = !isEvenRow;
			
			TrTag profRow = new TrTag();
			TdTag profCell = ProfessorAllocation.createProfessorCell(p, profPictures);
			
			TdTag situationCell = new TdTag();
			if(p.isAway()) 
				situationCell.addInnerText("Afastado(a) temporariamente.");
			else if(p.isTemporary())
				situationCell.addInnerText("Professor(a) temporário.");
			else
				situationCell.addInnerText("Professor(a) não alocado(a).").setFontWeight(CssConstants.FONT_WEIGHT_BOLD);
			
			situationCell.addCssClass(CssRulesForTableCreator.getProfessorDetailsCellClassName());
			if(isEvenRow) situationCell.addCssClass(CssRulesForTableCreator.getEvenRowClassName());
			else situationCell.addCssClass(CssRulesForTableCreator.getOddRowClassName());
			
			profRow.addChildElement(profCell);
			profRow.addChildElement(situationCell);
			
			table.addChildElement(profRow);
		}
		html.addChildElement(table);
	}

	private void addDocumentHead(HtmlDocument html){
		html.addChildElement(new HTag(1).addInnerText("Universidade Federal de Pernambuco - UFPE").setTextAlign(CssConstants.TEXT_ALIGN_CENTER));
		html.addChildElement(new HTag(2).addInnerText("Centro de Informática - CIn").setTextAlign(CssConstants.TEXT_ALIGN_CENTER));
		DivTag logoWrapper = new DivTag();
		logoWrapper.setOverflow(CssConstants.OVERFLOW_HIDDEN).setWidth("285px").setMargin(CssConstants.MARGIN_AUTO);
		ImgTag cinLogo = new ImgTag();
		cinLogo.setSrc("http://www2.cin.ufpe.br/site/uploads/arquivos/18/20090831100653_marca_cin_producao.jpg");
		cinLogo.setWidth(300).setMargin("-10px");
		logoWrapper.addChildElement(cinLogo);
		html.addChildElement(logoWrapper);
	}
	
	private void createTableTitle(HtmlDocument doc, String titleText){
		HTag title = new HTag(2);
		title.setTextAlign(CssConstants.TEXT_ALIGN_CENTER);
		title.addInnerText(titleText);
		doc.addChildElement(title);
	}
	
	private void createAllocatedTable(HtmlDocument doc) {
		createTableTitle(doc, "Relatório de Alocação de Disciplinas");
		
		TableTag table = new TableTag();
		table.setBorder(0);
		table.addStyle("text-align", "center");
		
		doc.addChildElement(table);
		
		int count = 0;
		for(ProfessorAllocation alloc : allocatedProfessors){
			table.addChildElement(alloc.getHtmlRepresentation(count % 2 == 0));
			++count;
		}
	}

	public void saveToFile(File f) throws FileNotFoundException{
		HtmlDocument doc = getHtmlRepresentation();
		PrintWriter writer = new PrintWriter(f);
		writer.print(doc.getHtmlString().toString());
		writer.close();
	}
}
