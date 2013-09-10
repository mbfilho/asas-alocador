package logic.html;

public class ImgTag extends HtmlElement{

	public ImgTag(){
		super("img");
	}

	public void setBorder(int i) {
		addAttribute("boder", i + "");
	}

	public void setWidth(int i) {
		addAttribute("width", i + "");
	}

	public void setHeight(int i) {
		addAttribute("height", i + "");
	}

	public void setSrc(String src) {
		addAttribute("src", src);
	}
}
