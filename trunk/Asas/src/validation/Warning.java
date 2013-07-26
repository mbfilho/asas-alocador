package validation;

import java.util.Vector;

public class Warning {
	private Vector<String> messages;
	
	public Warning(){
		messages = new Vector<String>();
	}
	
	public Warning addMessage(String a){
		messages.add(a);
		return this;
	}
	
	public String getHtmlMessage(){
		String html = "<tr>";
		for(String item : messages){
			html += "<td>" + item + "</td>";
		}
		html += "</tr>";
		return html;
	}
}
