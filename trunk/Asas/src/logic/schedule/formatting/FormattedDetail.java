package logic.schedule.formatting;

import java.awt.Color;

public class FormattedDetail {
	
	private boolean isTitle;
	private String content;
	private Color background;
	
	public FormattedDetail(String content, Color back, boolean isTitle){
		this.content = content;
		background = back;
		this.isTitle = isTitle;
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
