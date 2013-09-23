package utilities;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

public class DisposableOnEscFrame extends JFrame{

	private static final long serialVersionUID = 8227084079529788625L;

	public DisposableOnEscFrame() {
		KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
		AbstractAction escapeAction = new AbstractAction() {
			private static final long serialVersionUID = 5525788963509917486L;

			public void actionPerformed(ActionEvent e) {
		        dispose();
		    }
		}; 
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
		getRootPane().getActionMap().put("ESCAPE", escapeAction);
		
		setMinimumSize(new Dimension(400, 400));
	}
}
