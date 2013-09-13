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
	
	public HtmlElement setSize(int size){
		return addAttribute("size", size + "");
	}
	
	public HtmlElement setFace(String face){
		return addAttribute("face", face);
	}

	public HtmlElement setColor(Color color) {
		String hexValue = Integer.toHexString(color.getRGB()).substring(2);
		return addAttribute("color", hexValue);
	}
}
