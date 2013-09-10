package logic.html;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

public abstract class HtmlElement {
	private HashMap<String, String> style;
	private HashSet<String> classes;
	private HashMap<String,String> attributes;
	private List<HtmlElement> childElements;
	private String tag;
	
	public HtmlElement(){
		this(null);
	}
	
	public HtmlElement(String tag){
		this.tag = tag;
		style = new HashMap<String, String>();
		classes = new HashSet<String>();
		attributes = new HashMap<String, String>();
		childElements = new LinkedList<HtmlElement>();
	}
	
	public void setId(String id){
		addAttribute("id", id);
	}
	
	public void addChildElement(HtmlElement child){
		childElements.add(child);
	}
	
	public void addCssClass(String cssClass){
		classes.add(cssClass);
	}
	
	public void addStyle(String style, String value){
		this.style.put(style, value);
	}
	
	public void addAttribute(String att, String value){
		attributes.put(att, value);
	}
	
	public void addInnerText(String text){
		addChildElement(new HtmlPlainContent(text));
	}
	
	public StringBuffer getHtmlString(){
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("<" + tag + " ");
		buffer.append(getCssClasses());
		buffer.append(getStyleString());
		buffer.append(getAttributeString());
		
		buffer.append(">\n");
		for(HtmlElement child : childElements)
			buffer.append("\t").append(child.getHtmlString()).append("\n");
		
		buffer.append("</" + tag + ">\n");
		
		return buffer;
	}
	
	private StringBuffer getCssClasses(){
		StringBuffer buffer = new StringBuffer();
		
		if(!classes.isEmpty()){
			buffer.append(" class = \" ");
			for(String css : classes) 
				buffer.append(css + " ");
			buffer.append("\"");
		}
		return buffer;
	}
	
	public StringBuffer getStyleString(){
		StringBuffer buffer = new StringBuffer();
		
		if(!style.isEmpty()){
			buffer.append(" style = \"");
			for(Entry<String, String> pair : style.entrySet())
				buffer.append(String.format("%s:%s;", pair.getKey(), pair.getValue()));
			buffer.append("\"");
		}
		return buffer;
	}
	
	public StringBuffer getAttributeString(){
		StringBuffer buffer = new StringBuffer();
		for(Entry<String, String> pair : attributes.entrySet())
			buffer.append(String.format("%s=\"%s\" ", pair.getKey(), pair.getValue()));
		return buffer;
	}
}
