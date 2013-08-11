package htmlGenerator;

public class ScheduleTable {
	private String title;
	private String slots[][];
	
	public ScheduleTable(String tableTitle){
		title = tableTitle;
		slots = new String[15][7];
	}
	
	public String generateHtml(){
		String html = "<h2>" + title + "</h2>\n";
		html += "<table class=\"weekTable\" cellspacing=\"0\">\n"+
			  	"	<tr>\n" +
			  	"		<th style=\"width:150px\">Horário</th>\n" +
			  	"		<th>Domingo</th>\n" +
			  	"		<th>Segunda</th>\n"+
			  	"		<th>Terça</th>\n" +
			  	"		<th>Quarta</th>\n" +
			  	"		<th>Quinta</th>\n" +
			  	"		<th>Sexta</th>\n" +
			  	"		<th>Sábado</th>\n" +
			  	"	</tr>\n";
		
		for(int i = 0; i < 15; ++i){
			String row = "<tr>\n";
			row += "		<td>" + (i+7) + ":00h - " + (i+8) + ":00h</td>\n";
			for(int j = 0; j < 7; ++j){
				row += "<td>" + slots[i][j] + "</td>";
			}
			row += "</tr>";
			html += row;
		}
		html += "</table>";
		return html;
	}
	
	public void setSlot(int slot, int day, String html){
		slots[slot][day] = new String(html);
	}
}
