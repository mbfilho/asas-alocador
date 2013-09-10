package logic.html;

public class BTag extends HtmlElement{
	
	public BTag(String content){
		this(new HtmlPlainContent(content));
	}
	
	public BTag(HtmlElement content){
		super("b");
		addChildElement(content);
	}
	
}
