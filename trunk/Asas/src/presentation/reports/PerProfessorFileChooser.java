package presentation.reports;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import logic.reports.perProfessor.ProfessorSorting;



public class PerProfessorFileChooser extends PerProfessorFileChooserLayout {

	private static final long serialVersionUID = -5836441091435271900L;

	public PerProfessorFileChooser(){
		
	}
	
	public ProfessorSorting getSortingPreferences(){
		return new ProfessorSorting(sortByName(), sortByLoad(), sortAscending());
	}
	
	public File openSaveDialog(JFrame parent, File selected) {
		setVisible(true);
		getFileChooser().setSelectedFile(selected);
		/*if(getFileChooser().showSaveDialog(parent) == JFileChooser.APPROVE_OPTION){
			File toSave = getFileWithExtension();
			return toSave;
		}//*/
	
		return null;
	}
	
	private File getFileWithExtension(){
		File original = getFileChooser().getSelectedFile();;
		String name = original.getAbsolutePath();
		if(!(name.endsWith(".html") || name.endsWith(".html")))
			original = new File(name + ".html");
		return original;
	}
}
