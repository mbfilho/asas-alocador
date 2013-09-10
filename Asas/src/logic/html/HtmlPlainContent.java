package logic.html;

public class HtmlPlainContent extends HtmlElement{
	private String content;
	
	public HtmlPlainContent(){
		this("");
	}
	
	public HtmlPlainContent(String content){
		setContent(content);
	}
	
	public void setContent(String content){
		this.content = content;
	}
	
	public String getContent(){
		return content;
	}
	
	public StringBuffer getHtmlString(){
		return new StringBuffer(content);
	}
}
