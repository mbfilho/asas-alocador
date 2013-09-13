package logic.html;

public class HtmlDocument extends HtmlElement{
	private BodyTag body;
	private HeadTag head;
	
	public HtmlDocument(){
		super("html");
		
		body = new BodyTag();
		head = new HeadTag();
		super.addChildElement(head);
		super.addChildElement(body);
		
		MetaTag charset = new MetaTag();
		charset.setCharset("UTF-8");
		addToHead(charset);
	}
	
	public void setTitle(String title){
		addToHead(new TitleTag(title));
	}
	
	public HtmlElement addChildElement(HtmlElement element){
		body.addChildElement(element);
		return this;
	}
	
	public HtmlElement addToHead(HtmlElement element){
		head.addChildElement(element);
		return this;
	}
}
