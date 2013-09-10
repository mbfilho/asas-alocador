package logic.html;

import java.awt.Color;

public class TableTag extends HtmlElement{

	public TableTag(){
		super("table");
	}
	
	public static TableTag defaulTable(){
		TableTag table = new TableTag();
		table.setBorder(1);
		table.setWidth("100%");
		table.setBorderColor(Color.black);
		table.addStyle("text-align", "center");
		table.addStyle("color", "blue");
		table.addStyle("font-family", "Arial");
		return table;
	}
	
	public void setBorder(int val){
		addAttribute("border", val + "");
	}
	
	public void setWidth(String val){
		addAttribute("width", val);
	}

	public void setBorderColor(Color color) {
		String hexVal = Integer.toHexString(color.getRGB()).substring(2);
		addAttribute("bordercolor", "#" + hexVal);
	}
}
