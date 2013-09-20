package logic.html;

public class StyleTag extends HtmlElement{
	
	public StyleTag(){
		super("style");
		addAttribute("type", "text/css");
	}
	
	public StyleTag addCssRule(CssRule rule){
		addChildElement(rule.getHtmlRepresentation());
		return this;
	}
}
