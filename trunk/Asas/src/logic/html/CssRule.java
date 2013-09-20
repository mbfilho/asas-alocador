package logic.html;

import java.util.LinkedList;
import java.util.List;

import utilities.StringUtil;

public class CssRule {

	private List<String> selectors;
	private List<String> styles;
	
	public CssRule(){
		selectors = new LinkedList<String>();
		styles = new LinkedList<String>();
	}
	
	public CssRule addClassSelector(String c){
		return addSelector("." + c);
	}
	
	public CssRule addTagSelector(String tag){
		return addSelector(tag);
	}
	
	public CssRule addSelector(String selector){
		selectors.add(selector);
		return this;
	}
	
	public CssRule addStyle(String style, String value){
		styles.add(style + ":" + value + ";\n");
		return this;
	}
	
	public HtmlPlainContent getHtmlRepresentation(){
		String content = String.format("%s {\n%s}\n", 
				StringUtil.joinListWithSeparator(selectors, ","),
				StringUtil.joinListWithSeparator(styles, "")
			);
		return new HtmlPlainContent(content);
	}
}
