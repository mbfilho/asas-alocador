package utilities;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class HtmlTableFrame extends DisposableOnEscFrame{
	private static final long serialVersionUID = 1157546977732530243L;
	private JTextPane panel;
	
	public HtmlTableFrame(String text){
		panel = new JTextPane();
		panel.setContentType("text/html");
		panel.setText(text);
		panel.setEditable(false);
		getContentPane().add(new JScrollPane(panel));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
