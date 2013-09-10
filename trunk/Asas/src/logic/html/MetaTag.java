package logic.html;

public class MetaTag extends HtmlElement{

	public MetaTag(){
		super("meta");
	}
	
	public void setCharset(String charset){
		addAttribute("http-equiv","Content-Type");
		addAttribute("content", "text/html");
		addAttribute("charset", charset);
	}
}
