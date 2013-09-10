package logic.html;

public class ITag extends HtmlElement{

	public ITag(String content){
		super("i");
		addInnerText(content);
	}
}
