package logic.html;

public enum CssConstants {

	TEXT_ALIGN_CENTER("center"),
	TEXT_ALIGN_LEFT("left"),
	FLOAT_LEFT("left"),
	FLOAT_RIGHT("right"),
	FLOAT_NONE("none"),
	DISPLAY_BLOCK("block"),
	DISPLAY_NONE("none"), 
	BORDER_SOLID("solid"), 
	CLEAR_BOTH("both"),
	TABLE_BORDER_COLLAPSE("collapse");
	
	String value;
	CssConstants(String a){
		value = a;
	}
	
	public String getValue(){
		return value;
	}
}
