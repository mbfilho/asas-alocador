package validation;

import java.util.Vector;

public class WarningReport {
	public Vector<Warning> classesWithoutSlots;
	public Vector<Warning> classesWithoutRoom;
	public Vector<Warning> classesWithoutProf;
	public Vector<Warning> classesWithSameProf;
	public Vector<Warning> classesWithSameRoom;
	public Vector<Warning> professorsWithoutClass;
	
	public WarningReport(){
		classesWithoutProf = new Vector<Warning>();
		classesWithoutSlots = new Vector<Warning>();
		classesWithoutRoom = new Vector<Warning>();
		classesWithSameProf = new Vector<Warning>();
		classesWithSameRoom = new Vector<Warning>();
		professorsWithoutClass = new Vector<Warning>();
	}

	private String generateHtmlTable(String head, Vector<Warning> war){
		if(war.size() == 0) return "";
		String table = "<table style='border: 1px black solid;' cellspacing='0'>";
		table += "<th colspan=\"" + war.size() + "\">" + head + "</th>\n";
		for(Warning w : war) table += w.getHtmlMessage();
		table += "</table><br/><br/>";
		return table;
	}
	
	public String generateHtml(){
		String html = "<html><head>" +
				"<style type=\"text/css\"> " +
					"tr td {border:1px solid black;}" +
					"td{text-align:center;}\n" +
					"*{font-family:\"Times New Roman\"; font-size:110%}\n" + 
				"</style></head><body>\n";
		html += generateHtmlTable("Turmas sem horário", classesWithoutSlots);
		html += generateHtmlTable("Turmas sem sala", classesWithoutRoom);
		html += generateHtmlTable("Turmas sem professor", classesWithoutProf);
		html += generateHtmlTable("Professores desalocados", professorsWithoutClass);
		html += generateHtmlTable("Turmas conflitantes: mesmo horário e professor", classesWithSameProf);
		html +=  generateHtmlTable("Turmas conflitantes: mesmo horário e sala", classesWithSameRoom);
		html += "</body></html>";
		System.out.println(html);
		return html;
	}
	
	public int getWarningCount(){
		return classesWithoutSlots.size() + classesWithoutRoom.size() + 
				classesWithSameProf.size() + classesWithSameRoom.size() +
				classesWithoutProf.size() + professorsWithoutClass.size();
	}
}
