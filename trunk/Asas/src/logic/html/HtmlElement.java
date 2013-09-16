package logic.html;

import java.awt.Color;
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
	
	public HtmlElement setId(String id){
		return addAttribute("id", id);
	}
	
	public HtmlElement addChildElement(HtmlElement child){
		childElements.add(child);
		return this;
	}
	
	public HtmlElement addCssClass(String cssClass){
		classes.add(cssClass);
		return this;
	}
	
	public HtmlElement addStyle(String style, String value){
		this.style.put(style, value);
		return this;
	}
	
	public HtmlElement setTextAlign(CssConstants align){
		return addStyle("text-align", align.getValue());
	}
	
	public HtmlElement setFloating(CssConstants flt){
		return addStyle("float", flt.getValue());
	
	}
	
	public HtmlElement setMarginBottom(String value){
		return setMargin("bottom", value);
	}
	
	public HtmlElement setMarginRight(String value){
		return setMargin("right", value);
	}
	
	public HtmlElement setMarginTop(String value) {
		return setMargin("top", value);
	}
	
	private HtmlElement setMargin(String margin, String value){
		return addStyle("margin-" + margin, value);
	}
	
	public HtmlElement setMinWidth(String width){
		return addStyle("min-width", width);
	}
	
	public HtmlElement setVisible(boolean visibility){
		if(visibility)
			return addStyle("display", CssConstants.DISPLAY_BLOCK.getValue());
		return addStyle("display", CssConstants.DISPLAY_NONE.getValue());
	}
	
	public HtmlElement setBackgroundColor(Color color){
		if(color != null)
			return setBackgroundColor(colorToHex(color));
		return this;
	}
	
	public HtmlElement setBackgroundColor(String hexColor){
		return addStyle("background-color", hexColor);
	}
	
	public HtmlElement addAttribute(String att, String value){
		attributes.put(att, value);
		return this;
	}
	
	public HtmlElement addInnerText(String text){
		addChildElement(new HtmlPlainContent(text));
		return this;
	}
	
	public HtmlElement setBorderOff() {
		return addStyle("border", "none");
	}
	
	public HtmlElement setBorderRight(Color color, CssConstants style, String width) {
		addStyle("border-right-color", colorToHex(color));
		addStyle("border-right-width", width);
		return addStyle("border-right-style", style.getValue());
	}
	
	public HtmlElement setBorderTop(Color color, CssConstants style, String width) {
		addStyle("border-top-color", colorToHex(color));
		addStyle("border-top-width", width);
		return addStyle("border-top-style", style.getValue());
	}
	
	public HtmlElement setFloatClear(CssConstants clear){
		return addStyle("clear", clear.getValue());
	}
	
	protected String colorToHex(Color color){
		return Integer.toHexString(color.getRGB()).substring(2);
	}
	
	public StringBuffer getHtmlString(){
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("<" + tag + " ");
		buffer.append(getCssClasses());
		buffer.append(getStyleString());
		buffer.append(getAttributeString());
		
		buffer.append(">\n");
		for(HtmlElement child : childElements)
			buffer.append(child.getHtmlString()).append("\n");
		
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
