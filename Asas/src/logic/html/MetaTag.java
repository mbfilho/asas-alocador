package logic.html;

public class MetaTag extends HtmlElement{

	public MetaTag(){
		super("meta");
	}
	
	public HtmlElement setCharset(String charset){
		addAttribute("http-equiv","Content-Type");
		addAttribute("content", "text/html");
		return addAttribute("charset", charset);
	}
}
