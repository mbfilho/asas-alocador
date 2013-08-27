package logic.schedule.formatting.detailing;

import java.awt.Color;

public class FormattedDetail {
	
	private boolean isTitle;
	private String content;
	private Color background;
	private Color foreground;
	
	public FormattedDetail(String content, Color back, Color font, boolean isTitle){
		this.content = content;
		background = back;
		this.isTitle = isTitle;
		foreground = font;
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
}
