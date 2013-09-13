package logic.schedule.formatting.detailing;

import java.awt.Color;

import presentation.classes.edition.InitialEditState;

public class FormattedDetail {
	
	private boolean isTitle;
	private String content;
	private Color background;
	private Color foreground;
	private InitialEditState initialEditState;
	
	public FormattedDetail(String content, Color back, Color font, boolean isTitle){
		this.content = content;
		background = back;
		this.isTitle = isTitle;
		foreground = font;
	}
	
	public FormattedDetail(String content, Color back, Color font, InitialEditState initialState){
		this(content, back, font, false);
		initialEditState = initialState;
	}

	public Color getForegroundColor(){
		return foreground == null ? Color.black : foreground;
	}
	
	public boolean isTitle() {
		return isTitle;
	}

	public String getContent() {
		return content;
	}

	public Color getBackgroundColor() {
		return background;
	}
	
	public InitialEditState getInitialEditState(){
		return initialEditState;
	}
	
	public boolean isLinkToClassEdition(){
		return initialEditState != null;
	}
}
