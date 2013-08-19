package excelPreferences.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JScrollPane;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.border.TitledBorder;
import java.awt.Component;
import javax.swing.Box;

/**
 * Apenas código de configuração de Layout.
 * Gerada através do WindowBuilder
 * @author Marcio Barbosa
 *
 */
public class EditExcelPreferencesLayout extends JFrame {

	private JSpinner cntSlotText;
	private JSpinner cntProfessorText;
	private JTextField semesterSeparatorText;
	private JTextField filePathText;
	private JTextField classesSheetText;
	private JTable mappingTable;
	private JButton loadButton;
	private JCheckBox requiredByOthersCentersCheck;
	private JCheckBox electivesFromGraduationCheck;
	private JCheckBox requiredByPosGraduationCheck;
	private JCheckBox requiredByGraduationCheck;
	private JCheckBox electivesFromPosGraduationCheck;
	private JButton searchButton;
	private JTextField requiredByGraduationSeparatorText;
	private JTextField requiredByOtherCentersText;
	private JTextField electivesFromGraduationText;
	private JTextField requiredByPosGraduationText;
	private JTextField electivesFromPosGraduationText;
	private JTextField endOfFileMarkerText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditExcelPreferencesLayout frame = new EditExcelPreferencesLayout();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EditExcelPreferencesLayout() {
		setTitle("Editar preferências do excel.");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 711, 572);
		
		JPanel panel = new JPanel();
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{89, 125, 178, 82, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		setContentPane(panel);
		
		JPanel filePanel = new JPanel();
		filePanel.setBorder(new TitledBorder(null, "Arquivo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_filePanel = new GridBagConstraints();
		gbc_filePanel.insets = new Insets(0, 0, 5, 0);
		gbc_filePanel.fill = GridBagConstraints.BOTH;
		gbc_filePanel.gridx = 0;
		gbc_filePanel.gridy = 0;
		panel.add(filePanel, gbc_filePanel);
		GridBagLayout gbl_filePanel = new GridBagLayout();
		gbl_filePanel.columnWidths = new int[]{112, 242, 271, 0};
		gbl_filePanel.rowHeights = new int[]{16, 0, 0, 0};
		gbl_filePanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_filePanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		filePanel.setLayout(gbl_filePanel);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.gridwidth = 3;
		gbc_horizontalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut.gridx = 0;
		gbc_horizontalStrut.gridy = 0;
		filePanel.add(horizontalStrut, gbc_horizontalStrut);
		
		searchButton = new JButton("Procurar...");
		GridBagConstraints gbc_searchButton = new GridBagConstraints();
		gbc_searchButton.insets = new Insets(0, 0, 5, 5);
		gbc_searchButton.gridx = 0;
		gbc_searchButton.gridy = 1;
		filePanel.add(searchButton, gbc_searchButton);
		
		filePathText = new JTextField();
		GridBagConstraints gbc_filePathText = new GridBagConstraints();
		gbc_filePathText.insets = new Insets(0, 0, 5, 5);
		gbc_filePathText.fill = GridBagConstraints.HORIZONTAL;
		gbc_filePathText.gridx = 1;
		gbc_filePathText.gridy = 1;
		filePanel.add(filePathText, gbc_filePathText);
		filePathText.setColumns(10);
		
		JLabel classesSheetLabel = new JLabel("Planilha de disciplinas");
		GridBagConstraints gbc_classesSheetLabel = new GridBagConstraints();
		gbc_classesSheetLabel.insets = new Insets(0, 0, 0, 5);
		gbc_classesSheetLabel.gridx = 0;
		gbc_classesSheetLabel.gridy = 2;
		filePanel.add(classesSheetLabel, gbc_classesSheetLabel);
		
		classesSheetText = new JTextField();
		GridBagConstraints gbc_classesSheetText = new GridBagConstraints();
		gbc_classesSheetText.insets = new Insets(0, 0, 0, 5);
		gbc_classesSheetText.fill = GridBagConstraints.HORIZONTAL;
		gbc_classesSheetText.gridx = 1;
		gbc_classesSheetText.gridy = 2;
		filePanel.add(classesSheetText, gbc_classesSheetText);
		classesSheetText.setColumns(10);
		
		JPanel readingPanel = new JPanel();
		readingPanel.setBorder(new TitledBorder(null, "Leitura", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_readingPanel = new GridBagConstraints();
		gbc_readingPanel.insets = new Insets(0, 0, 5, 0);
		gbc_readingPanel.fill = GridBagConstraints.BOTH;
		gbc_readingPanel.gridx = 0;
		gbc_readingPanel.gridy = 1;
		panel.add(readingPanel, gbc_readingPanel);
		GridBagLayout gbl_readingPanel = new GridBagLayout();
		gbl_readingPanel.columnWidths = new int[]{106, 55, 0, 0, 179, 94, 135, 0};
		gbl_readingPanel.rowHeights = new int[]{27, 0, 0, 0, 0};
		gbl_readingPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_readingPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		readingPanel.setLayout(gbl_readingPanel);
		
		JLabel sectionsSeparatorsLabel = new JLabel("Título das Seções");
		sectionsSeparatorsLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_sectionsSeparatorsLabel = new GridBagConstraints();
		gbc_sectionsSeparatorsLabel.anchor = GridBagConstraints.NORTH;
		gbc_sectionsSeparatorsLabel.insets = new Insets(0, 0, 5, 5);
		gbc_sectionsSeparatorsLabel.gridx = 3;
		gbc_sectionsSeparatorsLabel.gridy = 0;
		readingPanel.add(sectionsSeparatorsLabel, gbc_sectionsSeparatorsLabel);
		
		JLabel cntProfessorLabel = new JLabel("Qtd. de Professores");
		GridBagConstraints gbc_cntProfessorLabel = new GridBagConstraints();
		gbc_cntProfessorLabel.anchor = GridBagConstraints.EAST;
		gbc_cntProfessorLabel.insets = new Insets(0, 0, 5, 5);
		gbc_cntProfessorLabel.gridx = 0;
		gbc_cntProfessorLabel.gridy = 1;
		readingPanel.add(cntProfessorLabel, gbc_cntProfessorLabel);
		
		cntProfessorText = new JSpinner();
		GridBagConstraints gbc_cntProfessorText = new GridBagConstraints();
		gbc_cntProfessorText.insets = new Insets(0, 0, 5, 5);
		gbc_cntProfessorText.fill = GridBagConstraints.HORIZONTAL;
		gbc_cntProfessorText.gridx = 1;
		gbc_cntProfessorText.gridy = 1;
		readingPanel.add(cntProfessorText, gbc_cntProfessorText);
		
		JLabel requiredByGraduationSeparatorLabel = new JLabel("Obrigatórias Grad.");
		GridBagConstraints gbc_requiredByGraduationSeparatorLabel = new GridBagConstraints();
		gbc_requiredByGraduationSeparatorLabel.anchor = GridBagConstraints.EAST;
		gbc_requiredByGraduationSeparatorLabel.insets = new Insets(0, 0, 5, 5);
		gbc_requiredByGraduationSeparatorLabel.gridx = 3;
		gbc_requiredByGraduationSeparatorLabel.gridy = 1;
		readingPanel.add(requiredByGraduationSeparatorLabel, gbc_requiredByGraduationSeparatorLabel);
		
		requiredByGraduationSeparatorText = new JTextField();
		GridBagConstraints gbc_requiredByGraduationSeparatorText = new GridBagConstraints();
		gbc_requiredByGraduationSeparatorText.insets = new Insets(0, 0, 5, 5);
		gbc_requiredByGraduationSeparatorText.fill = GridBagConstraints.HORIZONTAL;
		gbc_requiredByGraduationSeparatorText.gridx = 4;
		gbc_requiredByGraduationSeparatorText.gridy = 1;
		readingPanel.add(requiredByGraduationSeparatorText, gbc_requiredByGraduationSeparatorText);
		requiredByGraduationSeparatorText.setColumns(10);
		
		JLabel requiredByPosGraduationLabel = new JLabel("Obrigatórias Pos.");
		GridBagConstraints gbc_requiredByPosGraduationLabel = new GridBagConstraints();
		gbc_requiredByPosGraduationLabel.anchor = GridBagConstraints.EAST;
		gbc_requiredByPosGraduationLabel.insets = new Insets(0, 0, 5, 5);
		gbc_requiredByPosGraduationLabel.gridx = 5;
		gbc_requiredByPosGraduationLabel.gridy = 1;
		readingPanel.add(requiredByPosGraduationLabel, gbc_requiredByPosGraduationLabel);
		
		requiredByPosGraduationText = new JTextField();
		GridBagConstraints gbc_requiredByPosGraduationText = new GridBagConstraints();
		gbc_requiredByPosGraduationText.insets = new Insets(0, 0, 5, 0);
		gbc_requiredByPosGraduationText.fill = GridBagConstraints.HORIZONTAL;
		gbc_requiredByPosGraduationText.gridx = 6;
		gbc_requiredByPosGraduationText.gridy = 1;
		readingPanel.add(requiredByPosGraduationText, gbc_requiredByPosGraduationText);
		requiredByPosGraduationText.setColumns(10);
		
		JLabel slotCntLabel = new JLabel("Qtd. de Horários");
		GridBagConstraints gbc_slotCntLabel = new GridBagConstraints();
		gbc_slotCntLabel.anchor = GridBagConstraints.EAST;
		gbc_slotCntLabel.insets = new Insets(0, 0, 5, 5);
		gbc_slotCntLabel.gridx = 0;
		gbc_slotCntLabel.gridy = 2;
		readingPanel.add(slotCntLabel, gbc_slotCntLabel);
		
		cntSlotText = new JSpinner();
		GridBagConstraints gbc_cntSlotText = new GridBagConstraints();
		gbc_cntSlotText.insets = new Insets(0, 0, 5, 5);
		gbc_cntSlotText.fill = GridBagConstraints.HORIZONTAL;
		gbc_cntSlotText.gridx = 1;
		gbc_cntSlotText.gridy = 2;
		readingPanel.add(cntSlotText, gbc_cntSlotText);
		
		JLabel requiredByOtherCentersLabel = new JLabel("Obrigatórias Externas");
		GridBagConstraints gbc_requiredByOtherCentersLabel = new GridBagConstraints();
		gbc_requiredByOtherCentersLabel.anchor = GridBagConstraints.EAST;
		gbc_requiredByOtherCentersLabel.insets = new Insets(0, 0, 5, 5);
		gbc_requiredByOtherCentersLabel.gridx = 3;
		gbc_requiredByOtherCentersLabel.gridy = 2;
		readingPanel.add(requiredByOtherCentersLabel, gbc_requiredByOtherCentersLabel);
		
		requiredByOtherCentersText = new JTextField();
		GridBagConstraints gbc_requiredByOtherCentersText = new GridBagConstraints();
		gbc_requiredByOtherCentersText.insets = new Insets(0, 0, 5, 5);
		gbc_requiredByOtherCentersText.fill = GridBagConstraints.HORIZONTAL;
		gbc_requiredByOtherCentersText.gridx = 4;
		gbc_requiredByOtherCentersText.gridy = 2;
		readingPanel.add(requiredByOtherCentersText, gbc_requiredByOtherCentersText);
		requiredByOtherCentersText.setColumns(10);
		
		JLabel eletivesFromPosGraduationLabel = new JLabel("Eletivas Pós.");
		GridBagConstraints gbc_eletivesFromPosGraduationLabel = new GridBagConstraints();
		gbc_eletivesFromPosGraduationLabel.anchor = GridBagConstraints.EAST;
		gbc_eletivesFromPosGraduationLabel.insets = new Insets(0, 0, 5, 5);
		gbc_eletivesFromPosGraduationLabel.gridx = 5;
		gbc_eletivesFromPosGraduationLabel.gridy = 2;
		readingPanel.add(eletivesFromPosGraduationLabel, gbc_eletivesFromPosGraduationLabel);
		
		electivesFromPosGraduationText = new JTextField();
		GridBagConstraints gbc_electivesFromPosGraduationText = new GridBagConstraints();
		gbc_electivesFromPosGraduationText.insets = new Insets(0, 0, 5, 0);
		gbc_electivesFromPosGraduationText.fill = GridBagConstraints.HORIZONTAL;
		gbc_electivesFromPosGraduationText.gridx = 6;
		gbc_electivesFromPosGraduationText.gridy = 2;
		readingPanel.add(electivesFromPosGraduationText, gbc_electivesFromPosGraduationText);
		electivesFromPosGraduationText.setColumns(10);
		
		JLabel semesterSeparatorLabel = new JLabel("Separador de Semestres");
		GridBagConstraints gbc_semesterSeparatorLabel = new GridBagConstraints();
		gbc_semesterSeparatorLabel.anchor = GridBagConstraints.EAST;
		gbc_semesterSeparatorLabel.insets = new Insets(0, 0, 0, 5);
		gbc_semesterSeparatorLabel.gridx = 0;
		gbc_semesterSeparatorLabel.gridy = 3;
		readingPanel.add(semesterSeparatorLabel, gbc_semesterSeparatorLabel);
		
		semesterSeparatorText = new JTextField();
		GridBagConstraints gbc_semesterSeparatorText = new GridBagConstraints();
		gbc_semesterSeparatorText.insets = new Insets(0, 0, 0, 5);
		gbc_semesterSeparatorText.fill = GridBagConstraints.HORIZONTAL;
		gbc_semesterSeparatorText.gridx = 1;
		gbc_semesterSeparatorText.gridy = 3;
		readingPanel.add(semesterSeparatorText, gbc_semesterSeparatorText);
		semesterSeparatorText.setColumns(10);
		
		JLabel electivesFromGraduationLabel = new JLabel("Eletivas Grad.");
		GridBagConstraints gbc_electivesFromGraduationLabel = new GridBagConstraints();
		gbc_electivesFromGraduationLabel.anchor = GridBagConstraints.EAST;
		gbc_electivesFromGraduationLabel.insets = new Insets(0, 0, 0, 5);
		gbc_electivesFromGraduationLabel.gridx = 3;
		gbc_electivesFromGraduationLabel.gridy = 3;
		readingPanel.add(electivesFromGraduationLabel, gbc_electivesFromGraduationLabel);
		
		electivesFromGraduationText = new JTextField();
		GridBagConstraints gbc_electivesFromGraduationText = new GridBagConstraints();
		gbc_electivesFromGraduationText.insets = new Insets(0, 0, 0, 5);
		gbc_electivesFromGraduationText.fill = GridBagConstraints.HORIZONTAL;
		gbc_electivesFromGraduationText.gridx = 4;
		gbc_electivesFromGraduationText.gridy = 3;
		readingPanel.add(electivesFromGraduationText, gbc_electivesFromGraduationText);
		electivesFromGraduationText.setColumns(10);
		
		JLabel endOfFileMarkerLabel = new JLabel("Fim de Arquivo");
		GridBagConstraints gbc_endOfFileMarkerLabel = new GridBagConstraints();
		gbc_endOfFileMarkerLabel.anchor = GridBagConstraints.EAST;
		gbc_endOfFileMarkerLabel.insets = new Insets(0, 0, 0, 5);
		gbc_endOfFileMarkerLabel.gridx = 5;
		gbc_endOfFileMarkerLabel.gridy = 3;
		readingPanel.add(endOfFileMarkerLabel, gbc_endOfFileMarkerLabel);
		
		endOfFileMarkerText = new JTextField();
		GridBagConstraints gbc_endOfFileMarkerText = new GridBagConstraints();
		gbc_endOfFileMarkerText.fill = GridBagConstraints.HORIZONTAL;
		gbc_endOfFileMarkerText.gridx = 6;
		gbc_endOfFileMarkerText.gridy = 3;
		readingPanel.add(endOfFileMarkerText, gbc_endOfFileMarkerText);
		endOfFileMarkerText.setColumns(10);
		
		JPanel roomMappingPanel = new JPanel();
		roomMappingPanel.setBorder(new TitledBorder(null, "Mapeamento de Salas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_roomMappingPanel = new GridBagConstraints();
		gbc_roomMappingPanel.insets = new Insets(0, 0, 5, 0);
		gbc_roomMappingPanel.fill = GridBagConstraints.BOTH;
		gbc_roomMappingPanel.gridx = 0;
		gbc_roomMappingPanel.gridy = 2;
		panel.add(roomMappingPanel, gbc_roomMappingPanel);
		GridBagLayout gbl_roomMappingPanel = new GridBagLayout();
		gbl_roomMappingPanel.columnWidths = new int[]{0, 0};
		gbl_roomMappingPanel.rowHeights = new int[]{152, 0};
		gbl_roomMappingPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_roomMappingPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		roomMappingPanel.setLayout(gbl_roomMappingPanel);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 0;
		roomMappingPanel.add(scrollPane_1, gbc_scrollPane_1);
		
		mappingTable = new JTable(new RoomMappingTableModel());
		mappingTable.getColumnModel().getColumn(1).setCellEditor(new RoomMappingTableCellEditor(this));
		scrollPane_1.setViewportView(mappingTable);
		
		JPanel classesSelectorsPanel = new JPanel();
		classesSelectorsPanel.setBorder(new TitledBorder(null, "Turmas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_classesSelectorsPanel = new GridBagConstraints();
		gbc_classesSelectorsPanel.insets = new Insets(0, 0, 5, 0);
		gbc_classesSelectorsPanel.fill = GridBagConstraints.BOTH;
		gbc_classesSelectorsPanel.gridx = 0;
		gbc_classesSelectorsPanel.gridy = 3;
		panel.add(classesSelectorsPanel, gbc_classesSelectorsPanel);
		GridBagLayout gbl_classesSelectorsPanel = new GridBagLayout();
		gbl_classesSelectorsPanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_classesSelectorsPanel.rowHeights = new int[]{15, 0, 0, 0};
		gbl_classesSelectorsPanel.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_classesSelectorsPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		classesSelectorsPanel.setLayout(gbl_classesSelectorsPanel);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_1 = new GridBagConstraints();
		gbc_horizontalStrut_1.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut_1.gridx = 0;
		gbc_horizontalStrut_1.gridy = 0;
		classesSelectorsPanel.add(horizontalStrut_1, gbc_horizontalStrut_1);
		
		requiredByGraduationCheck = new JCheckBox("Obrigatórias da Graduação");
		GridBagConstraints gbc_requiredByGraduationCheck = new GridBagConstraints();
		gbc_requiredByGraduationCheck.anchor = GridBagConstraints.WEST;
		gbc_requiredByGraduationCheck.insets = new Insets(0, 0, 5, 5);
		gbc_requiredByGraduationCheck.gridx = 0;
		gbc_requiredByGraduationCheck.gridy = 1;
		classesSelectorsPanel.add(requiredByGraduationCheck, gbc_requiredByGraduationCheck);
		
		electivesFromGraduationCheck = new JCheckBox("Eletivas da Graduação");
		GridBagConstraints gbc_electivesFromGraduationCheck = new GridBagConstraints();
		gbc_electivesFromGraduationCheck.anchor = GridBagConstraints.WEST;
		gbc_electivesFromGraduationCheck.insets = new Insets(0, 0, 5, 5);
		gbc_electivesFromGraduationCheck.gridx = 1;
		gbc_electivesFromGraduationCheck.gridy = 1;
		classesSelectorsPanel.add(electivesFromGraduationCheck, gbc_electivesFromGraduationCheck);
		
		electivesFromPosGraduationCheck = new JCheckBox("Eletivas da Pós-Graduação");
		GridBagConstraints gbc_electivesFromPosGraduationCheck = new GridBagConstraints();
		gbc_electivesFromPosGraduationCheck.anchor = GridBagConstraints.WEST;
		gbc_electivesFromPosGraduationCheck.insets = new Insets(0, 0, 5, 0);
		gbc_electivesFromPosGraduationCheck.gridx = 2;
		gbc_electivesFromPosGraduationCheck.gridy = 1;
		classesSelectorsPanel.add(electivesFromPosGraduationCheck, gbc_electivesFromPosGraduationCheck);
		
		requiredByOthersCentersCheck = new JCheckBox("Obrigatórias de Outros Centros");
		GridBagConstraints gbc_requiredByOthersCentersCheck = new GridBagConstraints();
		gbc_requiredByOthersCentersCheck.anchor = GridBagConstraints.WEST;
		gbc_requiredByOthersCentersCheck.insets = new Insets(0, 0, 0, 5);
		gbc_requiredByOthersCentersCheck.gridx = 0;
		gbc_requiredByOthersCentersCheck.gridy = 2;
		classesSelectorsPanel.add(requiredByOthersCentersCheck, gbc_requiredByOthersCentersCheck);
		
		requiredByPosGraduationCheck = new JCheckBox("Obrigatórias da Pós-Graduação");
		GridBagConstraints gbc_requiredByPosGraduationCheck = new GridBagConstraints();
		gbc_requiredByPosGraduationCheck.anchor = GridBagConstraints.WEST;
		gbc_requiredByPosGraduationCheck.insets = new Insets(0, 0, 0, 5);
		gbc_requiredByPosGraduationCheck.gridx = 1;
		gbc_requiredByPosGraduationCheck.gridy = 2;
		classesSelectorsPanel.add(requiredByPosGraduationCheck, gbc_requiredByPosGraduationCheck);
		
		loadButton = new JButton("Carregar");
		GridBagConstraints gbc_loadButton = new GridBagConstraints();
		gbc_loadButton.anchor = GridBagConstraints.WEST;
		gbc_loadButton.gridx = 0;
		gbc_loadButton.gridy = 4;
		panel.add(loadButton, gbc_loadButton);
	}
	protected JButton getLoadButton() {
		return loadButton;
	}
	protected JCheckBox getRequiredByOthersCentersCheck() {
		return requiredByOthersCentersCheck;
	}
	protected JCheckBox getElectivesFromGraduationCheck() {
		return electivesFromGraduationCheck;
	}
	protected JCheckBox getRequiredByPosGraduationCheck() {
		return requiredByPosGraduationCheck;
	}
	protected JCheckBox getRequiredByGraduationCheck() {
		return requiredByGraduationCheck;
	}
	protected JCheckBox getElectivesFromPosGraduationCheck() {
		return electivesFromPosGraduationCheck;
	}
	protected JTextField getSemesterSeparatorText() {
		return semesterSeparatorText;
	}
	protected JSpinner getCntSlotText() {
		return cntSlotText;
	}
	protected JSpinner getCntProfessorText() {
		return cntProfessorText;
	}
	protected JTextField getFilePathText() {
		return filePathText;
	}
	protected JTextField getClassesSheetText() {
		return classesSheetText;
	}
	protected JButton getSearchButton() {
		return searchButton;
	}
	protected JTable getMappingTable() {
		return mappingTable;
	}
	protected JTextField getRequiredByOtherCentersText() {
		return requiredByOtherCentersText;
	}
	protected JTextField getRequiredByGraduationSeparatorText() {
		return requiredByGraduationSeparatorText;
	}
	protected JTextField getElectivesFromPosGraduationText() {
		return electivesFromPosGraduationText;
	}
	protected JTextField getRequiredByPosGraduationText() {
		return requiredByPosGraduationText;
	}
	protected JTextField getElectivesFromGraduationText() {
		return electivesFromGraduationText;
	}
	protected JTextField getEndOfFileMarkerText() {
		return endOfFileMarkerText;
	}
}
