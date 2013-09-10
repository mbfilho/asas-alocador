package logic.html;

import java.awt.Color;

public class FontTag extends HtmlElement{

	public FontTag(){
		super("font");
	}
	
	public static FontTag defaultFontTag(){
		FontTag theTag = new FontTag();
		theTag.setFace("Arial");
		theTag.setColor(Color.blue);
		return theTag;
	}
	
	public void setSize(int size){
		addAttribute("size", size + "");
	}
	
	public void setFace(String face){
		addAttribute("face", face);
	}

	public void setColor(Color color) {
		String hexValue = Integer.toHexString(color.getRGB()).substring(2);
		addAttribute("color", hexValue);
	}
}
