package presentation.reports;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import utilities.GuiUtil;

import logic.reports.perProfessor.AllocationPerProfessor;
import logic.services.ConfigurationService;

public class CreateAllocationReportPerProfessor implements ActionListener{
	private JFrame parent;
	
	public CreateAllocationReportPerProfessor(JFrame parent){
		this.parent = parent;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		File previous = ConfigurationService.getLastFileLocationForProfessorReport();
		File choosen = GuiUtil.promptForHtmlFileCreation(parent, previous);
		
		if(choosen != null){
			try {
				ReportSortingPrompt sortingPrefsPrompt = new ReportSortingPrompt(parent);
				if(sortingPrefsPrompt.getSortingPreferences() != null){
					new AllocationPerProfessor(sortingPrefsPrompt.getSortingPreferences()).saveToFile(choosen);
					ConfigurationService.setLastFileLocationForProfessorReport(choosen);
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(
						parent, 
						"Ocorreu um erro ao salvar o arquivo. Certifique-se de ter inserido um nome válido.",
						"Erro ao salvar o relatório",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
