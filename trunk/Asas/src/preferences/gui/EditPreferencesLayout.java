package preferences.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JScrollPane;
import java.awt.Insets;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTable;
import com.jgoodies.forms.factories.DefaultComponentFactory;

public class EditPreferencesLayout extends JFrame {

	private JPanel contentPane;
	private JTextField slotCntText;
	private JTextField slotHourSeparatorText;
	private JTextField slotDaySeparatorText;
	private JTextField cntProfessorText;
	private JTextField ccMarkerText;
	private JTextField ecMarkerText;
	private JTextField semesterSeparatorText;
	private JTextField okMarkerText;
	private JTextField filePathText;
	private JTextField classesSheetText;
	private JTable mappingTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditPreferencesLayout frame = new EditPreferencesLayout();
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
	public EditPreferencesLayout() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 711, 527);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		scrollPane.setViewportView(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Excel", null, panel, null);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{103, 105, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JPanel filePanel = new JPanel();
		filePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_filePanel = new GridBagConstraints();
		gbc_filePanel.insets = new Insets(0, 0, 5, 0);
		gbc_filePanel.fill = GridBagConstraints.BOTH;
		gbc_filePanel.gridx = 0;
		gbc_filePanel.gridy = 0;
		panel.add(filePanel, gbc_filePanel);
		GridBagLayout gbl_filePanel = new GridBagLayout();
		gbl_filePanel.columnWidths = new int[]{112, 242, 271, 0};
		gbl_filePanel.rowHeights = new int[]{26, 0, 0, 0, 0};
		gbl_filePanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_filePanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		filePanel.setLayout(gbl_filePanel);
		
		JLabel lblArquivo = new JLabel("Arquivo");
		lblArquivo.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_lblArquivo = new GridBagConstraints();
		gbc_lblArquivo.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblArquivo.insets = new Insets(0, 0, 5, 5);
		gbc_lblArquivo.gridx = 0;
		gbc_lblArquivo.gridy = 0;
		filePanel.add(lblArquivo, gbc_lblArquivo);
		
		JButton searchButton = new JButton("Procurar...");
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
		gbc_classesSheetLabel.insets = new Insets(0, 0, 5, 5);
		gbc_classesSheetLabel.gridx = 0;
		gbc_classesSheetLabel.gridy = 2;
		filePanel.add(classesSheetLabel, gbc_classesSheetLabel);
		
		classesSheetText = new JTextField();
		GridBagConstraints gbc_classesSheetText = new GridBagConstraints();
		gbc_classesSheetText.insets = new Insets(0, 0, 5, 5);
		gbc_classesSheetText.fill = GridBagConstraints.HORIZONTAL;
		gbc_classesSheetText.gridx = 1;
		gbc_classesSheetText.gridy = 2;
		filePanel.add(classesSheetText, gbc_classesSheetText);
		classesSheetText.setColumns(10);
		
		JPanel readingPanel = new JPanel();
		readingPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_readingPanel = new GridBagConstraints();
		gbc_readingPanel.insets = new Insets(0, 0, 5, 0);
		gbc_readingPanel.fill = GridBagConstraints.BOTH;
		gbc_readingPanel.gridx = 0;
		gbc_readingPanel.gridy = 1;
		panel.add(readingPanel, gbc_readingPanel);
		GridBagLayout gbl_readingPanel = new GridBagLayout();
		gbl_readingPanel.columnWidths = new int[]{0, 149, 93, 111, 0, 0, 0};
		gbl_readingPanel.rowHeights = new int[]{27, 0, 0, 0, 27, 0};
		gbl_readingPanel.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_readingPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		readingPanel.setLayout(gbl_readingPanel);
		
		JLabel readingLabel = new JLabel("Leitura");
		readingLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		readingLabel.setLabelFor(readingPanel);
		GridBagConstraints gbc_lblLeitura = new GridBagConstraints();
		gbc_lblLeitura.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblLeitura.insets = new Insets(0, 0, 5, 5);
		gbc_lblLeitura.gridx = 0;
		gbc_lblLeitura.gridy = 0;
		readingPanel.add(readingLabel, gbc_lblLeitura);
		
		JLabel slotCntLabel = new JLabel("Qtd. de Horários");
		GridBagConstraints gbc_slotCntLabel = new GridBagConstraints();
		gbc_slotCntLabel.anchor = GridBagConstraints.EAST;
		gbc_slotCntLabel.insets = new Insets(0, 0, 5, 5);
		gbc_slotCntLabel.gridx = 0;
		gbc_slotCntLabel.gridy = 1;
		readingPanel.add(slotCntLabel, gbc_slotCntLabel);
		
		slotCntText = new JTextField();
		GridBagConstraints gbc_slotCntText = new GridBagConstraints();
		gbc_slotCntText.insets = new Insets(0, 0, 5, 5);
		gbc_slotCntText.fill = GridBagConstraints.HORIZONTAL;
		gbc_slotCntText.gridx = 1;
		gbc_slotCntText.gridy = 1;
		readingPanel.add(slotCntText, gbc_slotCntText);
		slotCntText.setColumns(10);
		
		JLabel ccMarkerLabel = new JLabel("Marcador de CC");
		GridBagConstraints gbc_ccMarkerLabel = new GridBagConstraints();
		gbc_ccMarkerLabel.anchor = GridBagConstraints.EAST;
		gbc_ccMarkerLabel.insets = new Insets(0, 0, 5, 5);
		gbc_ccMarkerLabel.gridx = 2;
		gbc_ccMarkerLabel.gridy = 1;
		readingPanel.add(ccMarkerLabel, gbc_ccMarkerLabel);
		
		ccMarkerText = new JTextField();
		GridBagConstraints gbc_ccMarkerText = new GridBagConstraints();
		gbc_ccMarkerText.insets = new Insets(0, 0, 5, 5);
		gbc_ccMarkerText.fill = GridBagConstraints.HORIZONTAL;
		gbc_ccMarkerText.gridx = 3;
		gbc_ccMarkerText.gridy = 1;
		readingPanel.add(ccMarkerText, gbc_ccMarkerText);
		ccMarkerText.setColumns(10);
		
		JLabel semesterSeparatorLabel = new JLabel("Separador de Semestres");
		GridBagConstraints gbc_semesterSeparatorLabel = new GridBagConstraints();
		gbc_semesterSeparatorLabel.anchor = GridBagConstraints.EAST;
		gbc_semesterSeparatorLabel.insets = new Insets(0, 0, 5, 5);
		gbc_semesterSeparatorLabel.gridx = 4;
		gbc_semesterSeparatorLabel.gridy = 1;
		readingPanel.add(semesterSeparatorLabel, gbc_semesterSeparatorLabel);
		
		semesterSeparatorText = new JTextField();
		GridBagConstraints gbc_semesterSeparatorText = new GridBagConstraints();
		gbc_semesterSeparatorText.insets = new Insets(0, 0, 5, 0);
		gbc_semesterSeparatorText.fill = GridBagConstraints.HORIZONTAL;
		gbc_semesterSeparatorText.gridx = 5;
		gbc_semesterSeparatorText.gridy = 1;
		readingPanel.add(semesterSeparatorText, gbc_semesterSeparatorText);
		semesterSeparatorText.setColumns(10);
		
		JLabel slotHourSeparatorLabel = new JLabel("Separador das horas");
		GridBagConstraints gbc_slotHourSeparatorLabel = new GridBagConstraints();
		gbc_slotHourSeparatorLabel.anchor = GridBagConstraints.EAST;
		gbc_slotHourSeparatorLabel.insets = new Insets(0, 0, 5, 5);
		gbc_slotHourSeparatorLabel.gridx = 0;
		gbc_slotHourSeparatorLabel.gridy = 2;
		readingPanel.add(slotHourSeparatorLabel, gbc_slotHourSeparatorLabel);
		
		slotHourSeparatorText = new JTextField();
		GridBagConstraints gbc_slotHourSeparatorText = new GridBagConstraints();
		gbc_slotHourSeparatorText.insets = new Insets(0, 0, 5, 5);
		gbc_slotHourSeparatorText.fill = GridBagConstraints.HORIZONTAL;
		gbc_slotHourSeparatorText.gridx = 1;
		gbc_slotHourSeparatorText.gridy = 2;
		readingPanel.add(slotHourSeparatorText, gbc_slotHourSeparatorText);
		slotHourSeparatorText.setColumns(10);
		
		JLabel ecMarkerLabel = new JLabel("Marcador de EC");
		GridBagConstraints gbc_ecMarkerLabel = new GridBagConstraints();
		gbc_ecMarkerLabel.anchor = GridBagConstraints.EAST;
		gbc_ecMarkerLabel.insets = new Insets(0, 0, 5, 5);
		gbc_ecMarkerLabel.gridx = 2;
		gbc_ecMarkerLabel.gridy = 2;
		readingPanel.add(ecMarkerLabel, gbc_ecMarkerLabel);
		
		ecMarkerText = new JTextField();
		GridBagConstraints gbc_ecMarkerText = new GridBagConstraints();
		gbc_ecMarkerText.insets = new Insets(0, 0, 5, 5);
		gbc_ecMarkerText.fill = GridBagConstraints.HORIZONTAL;
		gbc_ecMarkerText.gridx = 3;
		gbc_ecMarkerText.gridy = 2;
		readingPanel.add(ecMarkerText, gbc_ecMarkerText);
		ecMarkerText.setColumns(10);
		
		JLabel okMakerLabel = new JLabel("Seleção de disciplinas");
		GridBagConstraints gbc_okMakerLabel = new GridBagConstraints();
		gbc_okMakerLabel.anchor = GridBagConstraints.EAST;
		gbc_okMakerLabel.insets = new Insets(0, 0, 5, 5);
		gbc_okMakerLabel.gridx = 4;
		gbc_okMakerLabel.gridy = 2;
		readingPanel.add(okMakerLabel, gbc_okMakerLabel);
		
		okMarkerText = new JTextField();
		GridBagConstraints gbc_okMarkerText = new GridBagConstraints();
		gbc_okMarkerText.insets = new Insets(0, 0, 5, 0);
		gbc_okMarkerText.fill = GridBagConstraints.HORIZONTAL;
		gbc_okMarkerText.gridx = 5;
		gbc_okMarkerText.gridy = 2;
		readingPanel.add(okMarkerText, gbc_okMarkerText);
		okMarkerText.setColumns(10);
		
		JLabel slotDaySeparatorLabel = new JLabel("Separador do dia");
		GridBagConstraints gbc_slotDaySeparatorLabel = new GridBagConstraints();
		gbc_slotDaySeparatorLabel.anchor = GridBagConstraints.EAST;
		gbc_slotDaySeparatorLabel.insets = new Insets(0, 0, 5, 5);
		gbc_slotDaySeparatorLabel.gridx = 0;
		gbc_slotDaySeparatorLabel.gridy = 3;
		readingPanel.add(slotDaySeparatorLabel, gbc_slotDaySeparatorLabel);
		
		slotDaySeparatorText = new JTextField();
		GridBagConstraints gbc_slotDaySeparatorText = new GridBagConstraints();
		gbc_slotDaySeparatorText.insets = new Insets(0, 0, 5, 5);
		gbc_slotDaySeparatorText.fill = GridBagConstraints.HORIZONTAL;
		gbc_slotDaySeparatorText.gridx = 1;
		gbc_slotDaySeparatorText.gridy = 3;
		readingPanel.add(slotDaySeparatorText, gbc_slotDaySeparatorText);
		slotDaySeparatorText.setColumns(10);
		
		JLabel cntProfessorLabel = new JLabel("Qtd. de Professores");
		GridBagConstraints gbc_cntProfessorLabel = new GridBagConstraints();
		gbc_cntProfessorLabel.anchor = GridBagConstraints.EAST;
		gbc_cntProfessorLabel.insets = new Insets(0, 0, 0, 5);
		gbc_cntProfessorLabel.gridx = 0;
		gbc_cntProfessorLabel.gridy = 4;
		readingPanel.add(cntProfessorLabel, gbc_cntProfessorLabel);
		
		cntProfessorText = new JTextField();
		GridBagConstraints gbc_cntProfessorText = new GridBagConstraints();
		gbc_cntProfessorText.insets = new Insets(0, 0, 0, 5);
		gbc_cntProfessorText.fill = GridBagConstraints.HORIZONTAL;
		gbc_cntProfessorText.gridx = 1;
		gbc_cntProfessorText.gridy = 4;
		readingPanel.add(cntProfessorText, gbc_cntProfessorText);
		cntProfessorText.setColumns(10);
		
		JPanel roomMappingPanel = new JPanel();
		roomMappingPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_roomMappingPanel = new GridBagConstraints();
		gbc_roomMappingPanel.fill = GridBagConstraints.BOTH;
		gbc_roomMappingPanel.gridx = 0;
		gbc_roomMappingPanel.gridy = 2;
		panel.add(roomMappingPanel, gbc_roomMappingPanel);
		GridBagLayout gbl_roomMappingPanel = new GridBagLayout();
		gbl_roomMappingPanel.columnWidths = new int[]{0, 0};
		gbl_roomMappingPanel.rowHeights = new int[]{27, 115, 0};
		gbl_roomMappingPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_roomMappingPanel.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		roomMappingPanel.setLayout(gbl_roomMappingPanel);
		
		JLabel roomMappingLabel = DefaultComponentFactory.getInstance().createLabel("Mapeamento de Salas");
		roomMappingLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_roomMappingLabel = new GridBagConstraints();
		gbc_roomMappingLabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_roomMappingLabel.insets = new Insets(0, 0, 5, 0);
		gbc_roomMappingLabel.gridx = 0;
		gbc_roomMappingLabel.gridy = 0;
		roomMappingPanel.add(roomMappingLabel, gbc_roomMappingLabel);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 1;
		roomMappingPanel.add(scrollPane_1, gbc_scrollPane_1);
		
		mappingTable = new JTable(new RoomMappingTableModel());
		mappingTable.getColumnModel().getColumn(1).setCellEditor(new RoomMappingTableCellEditor(this));
		scrollPane_1.setViewportView(mappingTable);
		
		setVisible(true);
	}
}
