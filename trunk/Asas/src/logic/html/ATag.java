package logic.html;

public class ATag extends HtmlElement {

	public ATag(){
		super("a");
	}
	
	public ATag(String content){
		this();
		addInnerText(content);
	}

	public HtmlElement setLinkToOtherElement(String otherId) {
		return addAttribute("href", "#" + otherId);
	}
	
	
}
