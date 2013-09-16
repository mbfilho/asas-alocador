package logic.html;

public class TdTag extends HtmlElement{

	public TdTag(){
		super("td");
	}
	
	public TdTag(String content){
		this();
		addInnerText(content);
	}

	public static TdTag emptyCell() {
		return new TdTag("&nbsp;");
	}
}
