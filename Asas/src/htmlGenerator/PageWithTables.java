package htmlGenerator;

import java.util.Vector;

public class PageWithTables {

	private Vector<ScheduleTable> tables;
	private Vector<String> styles;
	private int height, width;
	
	public PageWithTables(int width, int height){
		tables = new Vector<ScheduleTable>();
		styles = new Vector();
		this.height = height;
		this.width = width;
	}
	
	public PageWithTables(){
		this(100,50);
	}
	
	public void addTable(ScheduleTable table){
		tables.add(table);
	}
	
	public void addStyle(String style){
		styles.add(style);
	}
	
	public String generateHtml(){
		String html = "<!DOCTYPE html>\n<html>\n" +
				"	<meta charset='utf-8'/>\n" +
				"	<head>\n"+
				"		<style type=\"text/css\">\n"+
				"			table{empty-cells:show; table-layout:fixed;}\n"+
				"			.turma{height:50px;}\n"+
				"			*{font-family:\"Times New Roman\";}\n"+
				"			th, td{text-align:center;}\n"+
				"			.weekTable{border:1px solid black;}\n" +
				"			.weekTable tr td{border:1px solid black;height:" + height +"px;width:" + width + "px}\n" +
				"			.weekTable td table{width:100%}\n"+
				"			.weekTable td table td{width:auto;}\n";
		for(String style : styles) html += style + "\n";
		html +=	"		</style>\n" +
				"	</head>\n" +
				"	<body>\n";
		for(ScheduleTable table : tables) html += table.generateHtml() + "\n";
		html += "	</body>\n" +
				"</html>";
		return html;
	}
}
