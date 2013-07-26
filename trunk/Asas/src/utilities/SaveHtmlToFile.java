package utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

public class SaveHtmlToFile {

	public static void SaveHtml(String html){
		JFileChooser chooser = new JFileChooser();
		int result = chooser.showSaveDialog(null);
		if(result == JFileChooser.APPROVE_OPTION){
			File out = chooser.getSelectedFile();
			if(out.getName().endsWith(".htm") == false && out.getName().endsWith(".html") == false)
				out = new File(out + ".html");
			PrintWriter pw;
			try {
				pw = new PrintWriter(out);
				pw.print(html);
				pw.close();
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "Arquivo não encontrado.");
			}
		}
	}
}
