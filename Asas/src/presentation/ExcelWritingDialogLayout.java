package presentation;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.border.TitledBorder;
import javax.swing.JEditorPane;
import java.awt.Font;

public class ExcelWritingDialogLayout extends JDialog {

	private static final long serialVersionUID = -5252198373083756035L;
	
	private final JPanel contentPanel = new JPanel();
	private JTextField filePath;
	private JTextField backupFilePath;
	private JButton cancelButton;
	private JButton backupBrowse;
	private JButton browserButton;
	private JButton okButton;
	private JCheckBox saveBackupCheck;
	private JEditorPane diffFilesWarning;
	private JScrollPane warningTextScrollPane;

	public ExcelWritingDialogLayout(JFrame parentFrame) {
		super(parentFrame, true);
		
		setTitle("Salvar dados no Excel.");
		setBounds(100, 100, 547, 351);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{102, 40, 75, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JScrollPane scrollPane = new JScrollPane();
			GridBagConstraints gbc_scrollPane = new GridBagConstraints();
			gbc_scrollPane.gridwidth = 2;
			gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
			gbc_scrollPane.fill = GridBagConstraints.BOTH;
			gbc_scrollPane.gridx = 0;
			gbc_scrollPane.gridy = 0;
			contentPanel.add(scrollPane, gbc_scrollPane);
			{
				JTextArea warningLabel = new JTextArea("O salvamento de informações na planilha Excel é um processo demorado e pode levar alguns segundos ou até 1 minuto a depender do tamanho total do arquivo. É recomendado que um backup do arquivo seja feito antes de prosseguir com essa operação.");
				warningLabel.setEditable(false);
				warningLabel.setLineWrap(true);
				scrollPane.setViewportView(warningLabel);
			}
		}
		{
			filePath = new JTextField();
			filePath.setEditable(false);
			GridBagConstraints gbc_filePath = new GridBagConstraints();
			gbc_filePath.insets = new Insets(0, 0, 5, 5);
			gbc_filePath.fill = GridBagConstraints.HORIZONTAL;
			gbc_filePath.gridx = 0;
			gbc_filePath.gridy = 1;
			contentPanel.add(filePath, gbc_filePath);
			filePath.setColumns(10);
		}
		{
			browserButton = new JButton("Procurar...");
			GridBagConstraints gbc_browserButton = new GridBagConstraints();
			gbc_browserButton.insets = new Insets(0, 0, 5, 0);
			gbc_browserButton.gridx = 1;
			gbc_browserButton.gridy = 1;
			contentPanel.add(browserButton, gbc_browserButton);
		}
		{
			{
				warningTextScrollPane = new JScrollPane();
				warningTextScrollPane.setVisible(false);
				GridBagConstraints gbc_warningTextScrollPane = new GridBagConstraints();
				gbc_warningTextScrollPane.fill = GridBagConstraints.BOTH;
				gbc_warningTextScrollPane.gridwidth = 2;
				gbc_warningTextScrollPane.insets = new Insets(0, 0, 5, 5);
				gbc_warningTextScrollPane.gridx = 0;
				gbc_warningTextScrollPane.gridy = 2;
				contentPanel.add(warningTextScrollPane, gbc_warningTextScrollPane);
				diffFilesWarning = new JEditorPane();
				warningTextScrollPane.setViewportView(diffFilesWarning);
				diffFilesWarning.setEditable(false);
				diffFilesWarning.setFont(new Font("Monospaced", Font.PLAIN, 13));
				diffFilesWarning.setContentType("text/html");
				diffFilesWarning.setText("<html><b style=\"color:red\">Atenção! </b>O arquivo selecionado parece ser diferente do arquivo original utilizado para carregar os dados na aplicação. Prossiga apenas se tiver certeza de que as informações do Excel não são inconsistentes com as da aplicação.</html>");
			}
		}
		{
			JPanel backupPanel = new JPanel();
			backupPanel.setBorder(new TitledBorder(null, "Backup", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			GridBagConstraints gbc_backupPanel = new GridBagConstraints();
			gbc_backupPanel.gridwidth = 2;
			gbc_backupPanel.fill = GridBagConstraints.BOTH;
			gbc_backupPanel.gridx = 0;
			gbc_backupPanel.gridy = 3;
			contentPanel.add(backupPanel, gbc_backupPanel);
			GridBagLayout gbl_backupPanel = new GridBagLayout();
			gbl_backupPanel.columnWidths = new int[]{0, 0, 0};
			gbl_backupPanel.rowHeights = new int[]{0, 0, 0};
			gbl_backupPanel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
			gbl_backupPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			backupPanel.setLayout(gbl_backupPanel);
			{
				saveBackupCheck = new JCheckBox("Ok, salve um backup por precaução.");
				saveBackupCheck.setSelected(true);
				GridBagConstraints gbc_saveBackupCheck = new GridBagConstraints();
				gbc_saveBackupCheck.gridwidth = 2;
				gbc_saveBackupCheck.fill = GridBagConstraints.HORIZONTAL;
				gbc_saveBackupCheck.insets = new Insets(0, 0, 5, 5);
				gbc_saveBackupCheck.gridx = 0;
				gbc_saveBackupCheck.gridy = 0;
				backupPanel.add(saveBackupCheck, gbc_saveBackupCheck);
			}
			{
				backupFilePath = new JTextField();
				backupFilePath.setEditable(false);
				GridBagConstraints gbc_backupFilePath = new GridBagConstraints();
				gbc_backupFilePath.fill = GridBagConstraints.HORIZONTAL;
				gbc_backupFilePath.insets = new Insets(0, 0, 0, 5);
				gbc_backupFilePath.gridx = 0;
				gbc_backupFilePath.gridy = 1;
				backupPanel.add(backupFilePath, gbc_backupFilePath);
				backupFilePath.setColumns(10);
			}
			{
				backupBrowse = new JButton("Procurar...");
				GridBagConstraints gbc_backupBrowse = new GridBagConstraints();
				gbc_backupBrowse.gridx = 1;
				gbc_backupBrowse.gridy = 1;
				backupPanel.add(backupBrowse, gbc_backupBrowse);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancelar");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	protected JButton getCancelButton() {
		return cancelButton;
	}
	protected JTextField getBackupFilePath() {
		return backupFilePath;
	}
	protected JButton getBackupBrowse() {
		return backupBrowse;
	}
	protected JButton getBrowserButton() {
		return browserButton;
	}
	protected JTextField getFilePath() {
		return filePath;
	}
	protected JButton getOkButton() {
		return okButton;
	}
	protected JCheckBox getSaveBackupCheck() {
		return saveBackupCheck;
	}
	protected JScrollPane getWarningTextScrollPane() {
		return warningTextScrollPane;
	}
}
