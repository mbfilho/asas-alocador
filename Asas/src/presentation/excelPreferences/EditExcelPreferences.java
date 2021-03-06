package presentation.excelPreferences;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JDialog;
import javax.swing.JFileChooser;

import data.configurations.ExcelPreferences;
import data.configurations.StateDescription;

import logic.services.ConfigurationService;
import logic.services.StateService;

import exceptions.StateIOException;


import utilities.ColorUtil;

public class EditExcelPreferences extends EditExcelPreferencesLayout {
	private static final long serialVersionUID = -2557069007741819454L;
	
	private JFileChooser fileChooser;
	private RoomMappingTableModel mapTableModel;
	
	public EditExcelPreferences(){
		mapTableModel = (RoomMappingTableModel) getMappingTable().getModel();
		fileChooser = new JFileChooser();
		configureFileSelectionActions();
		configureOkButtonAction();
		fillFieldsWithThesePreferences(ConfigurationService.getInstance().loadExcelPreferencesOrDefault());
		setVisible(true);
	}
	
	private void configureFileSelectionActions(){
		getSearchButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(fileChooser.showOpenDialog(EditExcelPreferences.this) == JFileChooser.APPROVE_OPTION)
					setFilePathToFileTextBox(fileChooser.getSelectedFile());
			}
		});
	}
	
	private void configureOkButtonAction() {
		getLoadButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ExcelPreferences prefs = getPreferencesFromFields();
				try {
					StateDescription description = new StateDescription(prefs.getFileLocation(), prefs.getClassesSheet());
					List<String> result = StateService.getInstance().loadStateFromExcel(prefs, description);
					JDialog report = new ExcelReadingResults(EditExcelPreferences.this, result);
					report.setVisible(true);
					dispose();
				} catch (StateIOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void fillFieldsWithThesePreferences(ExcelPreferences prefs){
		fillFilePreferences(prefs);
		fillReadingPreferencesFields(prefs);
		fillMappingTable(prefs);
		fillClassesSelectionPreferences(prefs);
	}
	
	private ExcelPreferences getPreferencesFromFields(){
		ExcelPreferences fromFields = new ExcelPreferences();
		
		getFilePreferencesFromFields(fromFields);
		getReadingPreferencesFromFields(fromFields);
		getMappingFromFields(fromFields);
		getClassesSelectionFromFields(fromFields);
		
		return fromFields;
	}

	private void getFilePreferencesFromFields(ExcelPreferences fromFields) {
		fromFields.setFileLocation(fileChooser.getSelectedFile());
		fromFields.setClassesSheet(getClassesSheetText().getText());
	}

	@SuppressWarnings("unchecked")
	private void getMappingFromFields(ExcelPreferences fromFields) {
		for(int i = 0; i < mapTableModel.getRowCount(); ++i){
			fromFields.addMappingCorrespondence((String) mapTableModel.getValueAt(i, 0), 
					(List<String>) mapTableModel.getValueAt(i, 1));
		}
	}

	private void getClassesSelectionFromFields(ExcelPreferences fromFields) {
		fromFields.setRequiredByGraduation(getRequiredByGraduationCheck().isSelected());
		fromFields.setRequiredByOtherCenters(getRequiredByOthersCentersCheck().isSelected());
		fromFields.setRequiredByPosGraduation(getRequiredByPosGraduationCheck().isSelected());
		fromFields.setElectivesFromGraduation(getElectivesFromGraduationCheck().isSelected());
		fromFields.setElectivesFromPosGraduation(getElectivesFromPosGraduationCheck().isSelected());
	}

	private void getReadingPreferencesFromFields(ExcelPreferences fromFields) {
		fromFields.setProfessorCount((Integer)getCntProfessorText().getValue());
		fromFields.setSlotCount((Integer)getCntSlotText().getValue());
		fromFields.setSemesterSeparator(getSemesterSeparatorText().getText());
		fromFields.setElectivesFromPosGraduationMaker(getElectivesFromPosGraduationText().getText());
		fromFields.setElectivesFromGraduationMarker(getElectivesFromGraduationText().getText());
		fromFields.setRequiredByGraduationMarker(getRequiredByGraduationSeparatorText().getText());
		fromFields.setRequiredByOtherCentersMaker(getRequiredByOtherCentersText().getText());
		fromFields.setRequiredByPosGraduationMaker(getRequiredByPosGraduationText().getText());
		fromFields.setEndOfFileMarker(getEndOfFileMarkerText().getText());
		fromFields.setAwayProfessorMarker(getAwayProfessorText().getText());
		fromFields.setTemporaryProfessorMarker(getTemporaryProfessorText().getText());
	}
	
	private void fillFilePreferences(ExcelPreferences prefs) {
		File selectedFile = prefs.getFileLocation();
		setFilePathToFileTextBox(selectedFile);
		fileChooser.setSelectedFile(selectedFile);
		getClassesSheetText().setText(prefs.getClassesSheet());
		getProfessorsSheetText().setText(prefs.getProfessorsSheet());
	}

	private void fillMappingTable(ExcelPreferences prefs) {
		for(Entry<String, List<String>> correspondence : prefs.getCodeToRoomMapping())
			mapTableModel.addNewRow(correspondence.getKey(), correspondence.getValue());
	}

	private void fillClassesSelectionPreferences(ExcelPreferences prefs) {
		getRequiredByGraduationCheck().setSelected(prefs.isRequiredByGraduationEnabled());
		getRequiredByOthersCentersCheck().setSelected(prefs.isRequiredByOtherCentersEnabled());
		getRequiredByPosGraduationCheck().setSelected(prefs.isRequiredByPosGraduationEnabled());
		getElectivesFromGraduationCheck().setSelected(prefs.isElectivesFromGraduationEnabled());
		getElectivesFromPosGraduationCheck().setSelected(prefs.isElectivesFromPosGraduationEnabled());
	}

	private void fillReadingPreferencesFields(ExcelPreferences prefs) {
		getCntProfessorText().setValue(prefs.getProfessorCount());
		getCntSlotText().setValue(prefs.getSlotCount());
		getSemesterSeparatorText().setText(prefs.getSemesterSeparator());
		getRequiredByGraduationSeparatorText().setText(prefs.getRequiredByGraduationMarker());
		getRequiredByOtherCentersText().setText(prefs.getRequiredByOtherCentersMarker());
		getRequiredByPosGraduationText().setText(prefs.getRequiredByPosGraduationMarker());
		getElectivesFromGraduationText().setText(prefs.getElectivesFromGraduationMaker());
		getElectivesFromPosGraduationText().setText(prefs.getElectivesFromPosGraduationMarker());
		getEndOfFileMarkerText().setText(prefs.getEndOfFileMarker());
		getAwayProfessorText().setText(prefs.getAwayProfessorMarker());
		getTemporaryProfessorText().setText(prefs.getTemporaryProfessorMarker());
	}
	
	private void setFilePathToFileTextBox(File theFile){
		Color error = ColorUtil.mixWithWhite(Color.red), fine = Color.white, bg = error;
		
		if(theFile == null)	getFilePathText().setText("");
		else{
			getFilePathText().setText(theFile.getAbsolutePath());
			if(theFile.exists()) bg = fine;
		}
		getFilePathText().setBackground(bg);
	}
	
}
