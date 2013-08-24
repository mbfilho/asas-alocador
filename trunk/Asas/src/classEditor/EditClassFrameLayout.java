package classEditor;

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

import services.ProfessorService;
import services.WarningGeneratorService;
import utilities.DisposableOnEscFrame;
import utilities.GuiUtil;
import basic.Class;
import basic.Professor;
import basic.SlotRange;
import java.awt.Color;

public class EditClassFrameLayout extends DisposableOnEscFrame{
	private static final long serialVersionUID = -996922228967294876L;
	
	protected JComboBox<NamedPair<Class>> classesComboBox;
	protected JPanel contentPane;
	protected JTextField nameText;
	protected JTextField codeText;
	protected JTextField ccText;
	protected JTextField ecText;
	protected JTextField courseText;
	protected JLabel lblEc;
	protected JLabel lblCc;
	protected JLabel lblSelecioneUmaDisciplina;
	protected JLabel lblCurso;
	protected EditableJList<SlotRange> slotList;
	protected EditableJList<Professor> professorList;
	protected JTabbedPane tabbedPane;
	protected JLabel lblCdigo;
	protected JLabel lblPerodo;
	protected JLabel lblNome;
	protected JButton btnOk;
	protected JButton btnRemover;	
	private JLabel changesHappenedwarningLabel;
	private JButton swapsButton;
	public EditClassFrameLayout(){
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
		
		
		classesComboBox = new JComboBox<NamedPair<Class>>();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 6;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 1;
		contentPane.add(classesComboBox, gbc_comboBox);
		
		lblSelecioneUmaDisciplina = new JLabel("Selecione uma disciplina:");
		GridBagConstraints gbc_lblSelecioneUmaDisciplina = new GridBagConstraints();
		gbc_lblSelecioneUmaDisciplina.anchor = GridBagConstraints.WEST;
		gbc_lblSelecioneUmaDisciplina.gridwidth = 5;
		gbc_lblSelecioneUmaDisciplina.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelecioneUmaDisciplina.gridx = 0;
		gbc_lblSelecioneUmaDisciplina.gridy = 0;
		contentPane.add(lblSelecioneUmaDisciplina, gbc_lblSelecioneUmaDisciplina);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.gridheight = 11;
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 6;
		gbc_tabbedPane.gridy = 0;
		contentPane.add(tabbedPane, gbc_tabbedPane);
				
		lblNome = new JLabel("Nome");
		GridBagConstraints gbc_lblNome = new GridBagConstraints();
		gbc_lblNome.anchor = GridBagConstraints.WEST;
		gbc_lblNome.insets = new Insets(0, 0, 5, 5);
		gbc_lblNome.gridx = 0;
		gbc_lblNome.gridy = 2;
		contentPane.add(lblNome, gbc_lblNome);
		
		nameText = new JTextField();
		GridBagConstraints gbc_txtName = new GridBagConstraints();
		gbc_txtName.gridwidth = 5;
		gbc_txtName.insets = new Insets(0, 0, 5, 5);
		gbc_txtName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtName.gridx = 1;
		gbc_txtName.gridy = 2;
		contentPane.add(nameText, gbc_txtName);
		nameText.setColumns(10);
		
		lblCdigo = new JLabel("Código");
		GridBagConstraints gbc_lblCdigo = new GridBagConstraints();
		gbc_lblCdigo.anchor = GridBagConstraints.WEST;
		gbc_lblCdigo.insets = new Insets(0, 0, 5, 5);
		gbc_lblCdigo.gridx = 0;
		gbc_lblCdigo.gridy = 3;
		contentPane.add(lblCdigo, gbc_lblCdigo);
		
		codeText = new JTextField();
		GridBagConstraints gbc_txtCode = new GridBagConstraints();
		gbc_txtCode.gridwidth = 5;
		gbc_txtCode.insets = new Insets(0, 0, 5, 5);
		gbc_txtCode.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCode.gridx = 1;
		gbc_txtCode.gridy = 3;
		contentPane.add(codeText, gbc_txtCode);
		codeText.setColumns(10);
		
		lblPerodo = new JLabel("Período");
		GridBagConstraints gbc_lblPerodo = new GridBagConstraints();
		gbc_lblPerodo.anchor = GridBagConstraints.WEST;
		gbc_lblPerodo.insets = new Insets(0, 0, 5, 5);
		gbc_lblPerodo.gridx = 0;
		gbc_lblPerodo.gridy = 4;
		contentPane.add(lblPerodo, gbc_lblPerodo);
		
		lblCc = new JLabel("cc");
		GridBagConstraints gbc_lblCc = new GridBagConstraints();
		gbc_lblCc.insets = new Insets(0, 0, 5, 5);
		gbc_lblCc.anchor = GridBagConstraints.EAST;
		gbc_lblCc.gridx = 1;
		gbc_lblCc.gridy = 4;
		contentPane.add(lblCc, gbc_lblCc);
		
		ccText = new JTextField();
		GridBagConstraints gbc_txtCc = new GridBagConstraints();
		gbc_txtCc.insets = new Insets(0, 0, 5, 5);
		gbc_txtCc.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCc.gridx = 2;
		gbc_txtCc.gridy = 4;
		contentPane.add(ccText, gbc_txtCc);
		ccText.setColumns(10);
		
		lblEc = new JLabel("ec");
		GridBagConstraints gbc_lblEc = new GridBagConstraints();
		gbc_lblEc.insets = new Insets(0, 0, 5, 5);
		gbc_lblEc.anchor = GridBagConstraints.EAST;
		gbc_lblEc.gridx = 3;
		gbc_lblEc.gridy = 4;
		contentPane.add(lblEc, gbc_lblEc);
		
		ecText = new JTextField();
		GridBagConstraints gbc_txtEc = new GridBagConstraints();
		gbc_txtEc.insets = new Insets(0, 0, 5, 5);
		gbc_txtEc.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEc.gridx = 4;
		gbc_txtEc.gridy = 4;
		contentPane.add(ecText, gbc_txtEc);
		ecText.setColumns(10);
		
		lblCurso = new JLabel("Curso");
		GridBagConstraints gbc_lblCurso = new GridBagConstraints();
		gbc_lblCurso.anchor = GridBagConstraints.WEST;
		gbc_lblCurso.insets = new Insets(0, 0, 5, 5);
		gbc_lblCurso.gridx = 0;
		gbc_lblCurso.gridy = 5;
		contentPane.add(lblCurso, gbc_lblCurso);
		
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
				return GuiUtil.getSelectedItem(classesComboBox);
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
		
		btnOk = new JButton("Salvar");
		
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.anchor = GridBagConstraints.SOUTH;
		gbc_btnOk.insets = new Insets(0, 0, 0, 5);
		gbc_btnOk.gridx = 0;
		gbc_btnOk.gridy = 10;
		contentPane.add(btnOk, gbc_btnOk);
		
		btnRemover = new JButton("Remover");
		
		GridBagConstraints gbc_btnRemover = new GridBagConstraints();
		gbc_btnRemover.anchor = GridBagConstraints.SOUTH;
		gbc_btnRemover.insets = new Insets(0, 0, 0, 5);
		gbc_btnRemover.gridx = 4;
		gbc_btnRemover.gridy = 10;
		contentPane.add(btnRemover, gbc_btnRemover);
	}
	protected JLabel getChangesHappenedwarningLabel() {
		return changesHappenedwarningLabel;
	}
	protected JButton getSwapsButton() {
		return swapsButton;
	}
}
