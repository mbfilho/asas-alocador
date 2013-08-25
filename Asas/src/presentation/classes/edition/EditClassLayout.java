package presentation.classes.edition;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import classEditor.EditableJList;
import classEditor.EditableSlotList;

import presentation.NamedPair;

import data.persistentEntities.Class;
import data.persistentEntities.Professor;
import data.persistentEntities.SlotRange;

import utilities.DisposableOnEscFrame;
import utilities.GuiUtil;
import java.awt.Color;

import logic.services.ProfessorService;
import logic.services.WarningGeneratorService;

public class EditClassLayout extends DisposableOnEscFrame{
	private static final long serialVersionUID = -996922228967294876L;
	
	protected JComboBox<NamedPair<Class>> classesCBox;
	protected JPanel contentPane;
	protected JTextField nameText;
	protected JTextField codeText;
	protected JTextField ccText;
	protected JTextField ecText;
	protected JTextField courseText;
	protected JLabel ecLabel;
	protected JLabel ccLabel;
	protected JLabel selectAClassLabel;
	protected JLabel courseLabel;
	protected EditableJList<SlotRange> slotList;
	protected EditableJList<Professor> professorList;
	protected JTabbedPane disponibilityTabbedPane;
	protected JLabel codeLabel;
	protected JLabel semesterLabel;
	protected JLabel nameLabel;
	protected JButton okButton;
	protected JButton removeButton;	
	private JLabel changesHappenedwarningLabel;
	private JButton swapsButton;
	public EditClassLayout(){
		setTitle("Edição de disciplinas/turmas");
		WarningGeneratorService warningService = new WarningGeneratorService();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 658, 579);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{95, 0, 44, 0, 47, 161, 302, 0};
		gbl_contentPane.rowHeights = new int[]{26, -48, 0, 0, 0, 0, 148, 0, 161, 0, 95, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		
		classesCBox = new JComboBox<NamedPair<Class>>();
		classesCBox.setPrototypeDisplayValue(new NamedPair<Class>("Algoritmos e estruturas de dados", null));
		GridBagConstraints gbc_classesCBox = new GridBagConstraints();
		gbc_classesCBox.gridwidth = 6;
		gbc_classesCBox.insets = new Insets(0, 0, 5, 5);
		gbc_classesCBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_classesCBox.gridx = 0;
		gbc_classesCBox.gridy = 1;
		contentPane.add(classesCBox, gbc_classesCBox);
		
		selectAClassLabel = new JLabel("Selecione uma disciplina:");
		GridBagConstraints gbc_selectAClassLabel = new GridBagConstraints();
		gbc_selectAClassLabel.anchor = GridBagConstraints.WEST;
		gbc_selectAClassLabel.gridwidth = 5;
		gbc_selectAClassLabel.insets = new Insets(0, 0, 5, 5);
		gbc_selectAClassLabel.gridx = 0;
		gbc_selectAClassLabel.gridy = 0;
		contentPane.add(selectAClassLabel, gbc_selectAClassLabel);
		
		disponibilityTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_disponibilityTabbedPane = new GridBagConstraints();
		gbc_disponibilityTabbedPane.gridheight = 11;
		gbc_disponibilityTabbedPane.fill = GridBagConstraints.BOTH;
		gbc_disponibilityTabbedPane.gridx = 6;
		gbc_disponibilityTabbedPane.gridy = 0;
		contentPane.add(disponibilityTabbedPane, gbc_disponibilityTabbedPane);
				
		nameLabel = new JLabel("Nome");
		GridBagConstraints gbc_nameLabel = new GridBagConstraints();
		gbc_nameLabel.anchor = GridBagConstraints.WEST;
		gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nameLabel.gridx = 0;
		gbc_nameLabel.gridy = 2;
		contentPane.add(nameLabel, gbc_nameLabel);
		
		nameText = new JTextField();
		GridBagConstraints gbc_txtName = new GridBagConstraints();
		gbc_txtName.gridwidth = 5;
		gbc_txtName.insets = new Insets(0, 0, 5, 5);
		gbc_txtName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtName.gridx = 1;
		gbc_txtName.gridy = 2;
		contentPane.add(nameText, gbc_txtName);
		nameText.setColumns(10);
		
		codeLabel = new JLabel("Código");
		GridBagConstraints gbc_codeLabel = new GridBagConstraints();
		gbc_codeLabel.anchor = GridBagConstraints.WEST;
		gbc_codeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_codeLabel.gridx = 0;
		gbc_codeLabel.gridy = 3;
		contentPane.add(codeLabel, gbc_codeLabel);
		
		codeText = new JTextField();
		GridBagConstraints gbc_txtCode = new GridBagConstraints();
		gbc_txtCode.gridwidth = 5;
		gbc_txtCode.insets = new Insets(0, 0, 5, 5);
		gbc_txtCode.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCode.gridx = 1;
		gbc_txtCode.gridy = 3;
		contentPane.add(codeText, gbc_txtCode);
		codeText.setColumns(10);
		
		semesterLabel = new JLabel("Período");
		GridBagConstraints gbc_semesterLabel = new GridBagConstraints();
		gbc_semesterLabel.anchor = GridBagConstraints.WEST;
		gbc_semesterLabel.insets = new Insets(0, 0, 5, 5);
		gbc_semesterLabel.gridx = 0;
		gbc_semesterLabel.gridy = 4;
		contentPane.add(semesterLabel, gbc_semesterLabel);
		
		ccLabel = new JLabel("cc");
		GridBagConstraints gbc_ccLabel = new GridBagConstraints();
		gbc_ccLabel.insets = new Insets(0, 0, 5, 5);
		gbc_ccLabel.anchor = GridBagConstraints.EAST;
		gbc_ccLabel.gridx = 1;
		gbc_ccLabel.gridy = 4;
		contentPane.add(ccLabel, gbc_ccLabel);
		
		ccText = new JTextField();
		GridBagConstraints gbc_txtCc = new GridBagConstraints();
		gbc_txtCc.insets = new Insets(0, 0, 5, 5);
		gbc_txtCc.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCc.gridx = 2;
		gbc_txtCc.gridy = 4;
		contentPane.add(ccText, gbc_txtCc);
		ccText.setColumns(10);
		
		ecLabel = new JLabel("ec");
		GridBagConstraints gbc_ecLabel = new GridBagConstraints();
		gbc_ecLabel.insets = new Insets(0, 0, 5, 5);
		gbc_ecLabel.anchor = GridBagConstraints.EAST;
		gbc_ecLabel.gridx = 3;
		gbc_ecLabel.gridy = 4;
		contentPane.add(ecLabel, gbc_ecLabel);
		
		ecText = new JTextField();
		GridBagConstraints gbc_txtEc = new GridBagConstraints();
		gbc_txtEc.insets = new Insets(0, 0, 5, 5);
		gbc_txtEc.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEc.gridx = 4;
		gbc_txtEc.gridy = 4;
		contentPane.add(ecText, gbc_txtEc);
		ecText.setColumns(10);
		
		courseLabel = new JLabel("Curso");
		GridBagConstraints gbc_courseLabel = new GridBagConstraints();
		gbc_courseLabel.anchor = GridBagConstraints.WEST;
		gbc_courseLabel.insets = new Insets(0, 0, 5, 5);
		gbc_courseLabel.gridx = 0;
		gbc_courseLabel.gridy = 5;
		contentPane.add(courseLabel, gbc_courseLabel);
		
		courseText = new JTextField();
		GridBagConstraints gbc_txtCourse = new GridBagConstraints();
		gbc_txtCourse.gridwidth = 5;
		gbc_txtCourse.insets = new Insets(0, 0, 5, 5);
		gbc_txtCourse.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCourse.gridx = 1;
		gbc_txtCourse.gridy = 5;
		contentPane.add(courseText, gbc_txtCourse);
		courseText.setColumns(10);
		
		professorList = new EditableJList<Professor>("Professores", new ProfessorService().all());
		GridBagConstraints gbc_profList = new GridBagConstraints();
		gbc_profList.gridwidth = 6;
		gbc_profList.fill = GridBagConstraints.BOTH;
		gbc_profList.insets = new Insets(0, 0, 5, 5);
		gbc_profList.gridx = 0;
		gbc_profList.gridy = 6;
		contentPane.add(professorList, gbc_profList);
		
		swapsButton = new JButton("Swaps...");
		GridBagConstraints gbc_swapsButton = new GridBagConstraints();
		gbc_swapsButton.insets = new Insets(0, 0, 5, 5);
		gbc_swapsButton.gridx = 0;
		gbc_swapsButton.gridy = 7;
		contentPane.add(swapsButton, gbc_swapsButton);
		
		slotList = new EditableSlotList("Horários (duplo clique para editar)", warningService){
			private static final long serialVersionUID = 7730149789169337564L;

			public Class getSelectedClass() {
				return GuiUtil.getSelectedItem(classesCBox);
			}
		};
		GridBagConstraints gbc_slotList = new GridBagConstraints();
		gbc_slotList.gridwidth = 6;
		gbc_slotList.fill = GridBagConstraints.BOTH;
		gbc_slotList.insets = new Insets(0, 0, 5, 5);
		gbc_slotList.gridx = 0;
		gbc_slotList.gridy = 8;
		contentPane.add(slotList, gbc_slotList);
		
		changesHappenedwarningLabel = new JLabel("* Existem alterações não salvas ainda. Clique em Salvar.");
		changesHappenedwarningLabel.setForeground(Color.RED);
		GridBagConstraints gbc_changesHappenedwarningLabel = new GridBagConstraints();
		gbc_changesHappenedwarningLabel.anchor = GridBagConstraints.WEST;
		gbc_changesHappenedwarningLabel.gridwidth = 6;
		gbc_changesHappenedwarningLabel.insets = new Insets(0, 0, 5, 5);
		gbc_changesHappenedwarningLabel.gridx = 0;
		gbc_changesHappenedwarningLabel.gridy = 9;
		changesHappenedwarningLabel.setVisible(false);
		contentPane.add(changesHappenedwarningLabel, gbc_changesHappenedwarningLabel);
		
		okButton = new JButton("Salvar");
		
		GridBagConstraints gbc_okButton = new GridBagConstraints();
		gbc_okButton.anchor = GridBagConstraints.SOUTH;
		gbc_okButton.insets = new Insets(0, 0, 0, 5);
		gbc_okButton.gridx = 0;
		gbc_okButton.gridy = 10;
		contentPane.add(okButton, gbc_okButton);
		
		removeButton = new JButton("Remover");
		
		GridBagConstraints gbc_removeButton = new GridBagConstraints();
		gbc_removeButton.anchor = GridBagConstraints.SOUTH;
		gbc_removeButton.insets = new Insets(0, 0, 0, 5);
		gbc_removeButton.gridx = 4;
		gbc_removeButton.gridy = 10;
		contentPane.add(removeButton, gbc_removeButton);
	}
	protected JLabel getChangesHappenedwarningLabel() {
		return changesHappenedwarningLabel;
	}
	protected JButton getSwapsButton() {
		return swapsButton;
	}
}
