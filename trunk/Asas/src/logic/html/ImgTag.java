package logic.html;

public class ImgTag extends HtmlElement{

	public ImgTag(){
		super("img");
	}

	public HtmlElement setBorder(int i) {
		return addAttribute("boder", i + "");
	}

	public HtmlElement setWidth(int i) {
		return addAttribute("width", i + "");
	}

	public HtmlElement setHeight(int i) {
		return addAttribute("height", i + "");
	}

	public HtmlElement setSrc(String src) {
		return addAttribute("src", src);
	}
}
