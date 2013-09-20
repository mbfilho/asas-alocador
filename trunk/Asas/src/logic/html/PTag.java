package logic.html;

public class PTag extends HtmlElement{

	public PTag(){
		super("p");
	}
	
	public PTag(String content){
		this();
		addInnerText(content);
	}
}
