package logic.schedule.formatting.detailing;

import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import presentation.classes.edition.InitialEditState;

public class Details implements Iterable<FormattedDetail>{
	private List<FormattedDetail> details;
	
	public Details(){
		details = new LinkedList<FormattedDetail>();
	}
	
	public void addDetail(String content, Color bgColor, Color font){
		details.add(new FormattedDetail(content, bgColor, font, false));
	}
	
	public void addTitle(String title, Color bgColor, Color font){
		details.add(new FormattedDetail(title, bgColor, font, true));
	}
	
	public void addLink(String content, Color bgColor, Color font, InitialEditState initialState){
		details.add(new FormattedDetail(content, bgColor, font, initialState));
	}
	
	public Iterator<FormattedDetail> iterator() {
		return details.iterator();
	}
}
