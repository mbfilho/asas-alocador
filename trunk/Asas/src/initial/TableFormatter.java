package initial;

import java.awt.Component;

import javax.swing.JLabel;

public interface TableFormatter {

	public void formatCell(JLabel cell, int day, int slot);
	
	public Component getPopupContent(int day, int slot);
}
