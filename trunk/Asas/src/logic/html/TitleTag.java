package logic.html;

public class TitleTag extends HtmlElement{

	public TitleTag(String theTitle){
		super("title");
		addInnerText(theTitle);
	}
}
