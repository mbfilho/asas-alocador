package logic.html;

public class TdTag extends HtmlElement{

	public TdTag(){
		super("td");
	}
	
	public TdTag(String content){
		super("td");
		addInnerText(content);
	}
}
