package scheduleTable;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class CellState{
	private Color back, fore;
	private Border border;
	private String toolTip, value;
	
	public void setValue(String val){
		value = val;
	}
	
	public String getValue(){
		return value;
	}
	
	public void setBorder(Border b){
		border = b;
	}
	
	public void setBorder(Color c, int width){
		border = BorderFactory.createLineBorder(c, width);
	}
	
	public Border getBorder(){
		return border;
	}
	
	public void setBackColor(Color c){
		back = c;
	}
	
	public Color getBackColor(){
		return back;
	}
	
	public void setFontColor(Color c){
		fore = c;
	}
	
	public Color getFontColor(){
		return fore;
	}
	
	public String getTooltip(){
		return toolTip;
	}
	
	public void setTooltip(String s){
		toolTip = s;
	}
}

