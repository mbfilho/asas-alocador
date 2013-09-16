package logic.html;

public class DivTag extends HtmlElement{

	public DivTag(){
		super("div");
	}
	
	public static DivTag getClearFix(){
		DivTag div = new DivTag();
		div.setFloatClear(CssConstants.CLEAR_BOTH);
		return div;
	}
}
