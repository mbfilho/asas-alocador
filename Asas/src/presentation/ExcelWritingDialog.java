package presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import logic.services.ExcelWritingService;
import logic.services.FileBackupingService;

import data.configurations.ExcelPreferences;
import exceptions.WritingException;

public class ExcelWritingDialog extends ExcelWritingDialogLayout{
	private static final long serialVersionUID = 2227889935716635144L;
	private FileBackupingService backupService;
	ExcelWritingService excelService;
	private JFileChooser fileChooser, backupChooser;
	
	public ExcelWritingDialog(JFrame parentFrame, ExcelPreferences prefs) {
		super(parentFrame);
		excelService = new ExcelWritingService();
		backupService = new FileBackupingService();
		configureFileChooser(prefs);
		configureBackupFileChooser(prefs);
		
		configureCancelButton();
		configureBrowseButton();
		configureBackupBrowseButton();
		configureOkButton();
		
		setVisible(true);
	}

	private void configureBackupFileChooser(ExcelPreferences prefs) {
		backupChooser = new JFileChooser();
		if(prefs.getFileLocation() != null){
			File backupFile = backupService.getBackupFile(prefs.getFileLocation());
			backupChooser.setSelectedFile(backupFile);
			getBackupFilePath().setText(backupFile.getAbsolutePath());
		}
	}

	private void configureFileChooser(ExcelPreferences prefs) {
		fileChooser = new JFileChooser();
		if(prefs.getFileLocation() != null){
			fileChooser.setSelectedFile(prefs.getFileLocation());
			getFilePath().setText(prefs.getFileLocation().getAbsolutePath());
			checkForCompatibility();
		}
	}

	private void configureOkButton() {
		getOkButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					boolean canWrite = true;
					
					if(getSaveBackupCheck().isSelected()){
						if(backupChooser.getSelectedFile() == null){
							JOptionPane.showMessageDialog(ExcelWritingDialog.this, 
									"Escolha um local para salvar o backup.", 
									"Erro", 
									JOptionPane.ERROR_MESSAGE);
							canWrite = false;
						}else
							backupService.createBackup(fileChooser.getSelectedFile(), backupChooser.getSelectedFile());
					}
					
					if(canWrite){
						excelService.open(fileChooser.getSelectedFile());
						excelService.writeAll();
						excelService.save();
						dispose();
					}
				} catch (WritingException e) {
					JOptionPane.showMessageDialog(ExcelWritingDialog.this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(ExcelWritingDialog.this, 
							"Ocorreu um erro ao criar um backup do arquivo Excel.",
							"Erro",
							JOptionPane.ERROR_MESSAGE);
					
					e.printStackTrace();
				}catch(OutOfMemoryError e){
					JOptionPane.showMessageDialog(ExcelWritingDialog.this,
							"OutOfMemoryError: não foi possível abrir o arquivo.\nPossivelmente ele é muito grande.",
							"Erro",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	private void configureBackupBrowseButton() {
		getBackupBrowse().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(backupChooser.showSaveDialog(ExcelWritingDialog.this) == JFileChooser.APPROVE_OPTION){
					File selected = backupChooser.getSelectedFile();
					getBackupFilePath().setText(selected.getAbsolutePath());
				}
			}
		});
	}
	
	private void configureBrowseButton() {
		getBrowserButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fileChooser.showOpenDialog(ExcelWritingDialog.this) == JFileChooser.APPROVE_OPTION){
					File selected = fileChooser.getSelectedFile();
					getFilePath().setText(selected.getAbsolutePath());
					
					checkForCompatibility();
				}
			}
		});
	}
	
	private void checkForCompatibility(){
		File selected = fileChooser.getSelectedFile();
		
		if(selected == null)
			return;
		
		if(excelService.isFileCompatible(selected))
			getWarningTextScrollPane().setVisible(false);
		else
			getWarningTextScrollPane().setVisible(true);
		getContentPane().revalidate();
		getContentPane().repaint();
	}

	private void configureCancelButton() {
		getCancelButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				dispose();
			}
		});
	}
}
