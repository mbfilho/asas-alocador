package logic.html;

public class HtmlPlainContent extends HtmlElement{
	private String content;
	
	public HtmlPlainContent(){
		this("");
	}
	
	public HtmlPlainContent(String content){
		setContent(content);
	}
	
	public HtmlElement setContent(String content){
		if(content == null)
			content = "";
		this.content = content;
		return this;
	}
	
	public String getContent(){
		return content;
	}
	
	public StringBuffer getHtmlString(){
		return new StringBuffer(content);
	}
}
