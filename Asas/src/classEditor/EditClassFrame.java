package classEditor;

import groupMaker.FilterChooser;
import groupMaker.InitialFilterConfiguration;

import javax.swing.ComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;

import javax.swing.JTabbedPane;

import java.awt.GridBagConstraints;

import javax.swing.JLabel;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashSet;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import basic.Class;
import basic.Classroom;
import basic.NamedEntity;
import basic.Professor;
import basic.SlotRange;
import scheduleTable.DisponibilityModel;
import scheduleTable.FilteredScheduleVisualization;
import scheduleTable.ScheduleVisualizationTable;
import services.ClassService;
import services.ProfessorService;
import services.WarningGeneratorService;
import utilities.DisposableOnEscFrame;
import utilities.GuiUtil;
import utilities.StringUtil;

import javax.swing.JScrollPane;
import javax.swing.JButton;


public abstract class EditClassFrame extends DisposableOnEscFrame {

	private static final long serialVersionUID = 679979857489504936L;
	private JComboBox<NamedPair<Class>> classesComboBox;
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
	protected ClassService classService;
	protected ProfessorService professorService;
	protected JButton btnOk;
		
	public EditClassFrame(){
		this(null);
	}
	
	public EditClassFrame(InitialEditState initialState) {
		classService = new ClassService();
		professorService = new ProfessorService();
		
		configureElements(new WarningGeneratorService());
		configureElementsActions();
		setupInitialState(initialState);
		setVisible(true);
	}
	
	private void setupInitialState(InitialEditState initialState) {
		if(initialState == null) return;
		GuiUtil.setSelectedValue(classesComboBox, initialState.theClass);
		createAdditionalWindows(initialState);
	}
	
	private void createAdditionalWindows(InitialEditState initialState){
		InitialFilterConfiguration initialFiltering = initialState.deriveInitialFilterConfiguration();
		
		createSemesterWindow(initialFiltering);
		createClassroomWindow(initialFiltering);
		createProfessorWindow(initialFiltering);
	}
	
	private JFrame createBasicAdditionalFrame(){
		JFrame frame = new DisposableOnEscFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(30, 30, 500, 500);
		frame.setVisible(true);
		return frame;
	}

	private void createProfessorWindow(InitialFilterConfiguration initialFiltering) {
		JFrame frame = createBasicAdditionalFrame();
		frame.setTitle("Disponibilidade de professores.");
		frame.setContentPane(new FilteredScheduleVisualization(FilterChooser.PROFESSOR, initialFiltering));
	}

	private void createClassroomWindow(InitialFilterConfiguration initialFiltering) {
		JFrame frame = createBasicAdditionalFrame();
		frame.setTitle("Disponibilidade de Salas");
		frame.setContentPane(new FilteredScheduleVisualization(FilterChooser.ROOM, initialFiltering)); 
	}

	private void createSemesterWindow(InitialFilterConfiguration initialFiltering) {
		JFrame frame = createBasicAdditionalFrame();
		frame.setTitle("Alocação de semestre");
		frame.setContentPane(new FilteredScheduleVisualization(FilterChooser.SEMESTER, initialFiltering));		
	}

	private void configureElements(final WarningGeneratorService warningService) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 658, 579);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{95, 0, 44, 0, 47, 161, 302, 0};
		gbl_contentPane.rowHeights = new int[]{26, -48, 0, 0, 0, 0, 148, 161, 95, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		Vector<NamedPair<Class>> classesData = GuiUtil.createNamedPairs(classService.all());
		classesData.add(0, new NamedPair<Class>("Selecione uma disciplina", null));
		classesComboBox = new JComboBox<NamedPair<Class>>(classesData);
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
		gbc_tabbedPane.gridheight = 9;
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
		
		professorList = new EditableJList<Professor>("Professores", professorService.all());
		GridBagConstraints gbc_profList = new GridBagConstraints();
		gbc_profList.gridwidth = 6;
		gbc_profList.fill = GridBagConstraints.BOTH;
		gbc_profList.insets = new Insets(0, 0, 5, 5);
		gbc_profList.gridx = 0;
		gbc_profList.gridy = 6;
		contentPane.add(professorList, gbc_profList);
		
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
		gbc_slotList.gridy = 7;
		contentPane.add(slotList, gbc_slotList);
		
		btnOk = new JButton("Salvar");
		
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.anchor = GridBagConstraints.SOUTH;
		gbc_btnOk.insets = new Insets(0, 0, 0, 5);
		gbc_btnOk.gridx = 0;
		gbc_btnOk.gridy = 8;
		contentPane.add(btnOk, gbc_btnOk);
		
		JButton btnRemover = new JButton("Remover");
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(GuiUtil.getSelectedItem(classesComboBox) == null) return;
				
				int op = JOptionPane.showConfirmDialog(
							EditClassFrame.this, 
							"Tem certeza que deseja excluir a turma \"" + GuiUtil.getSelectedItem(classesComboBox).getName() + "\"?",
							"Confirmar exclusão de turma",
							JOptionPane.YES_NO_OPTION
						);
				if(op == JOptionPane.YES_OPTION){
					classService.remove(GuiUtil.getSelectedItem(classesComboBox));
					classesComboBox.removeItem(classesComboBox.getSelectedItem());
					saveChanges();
				}
			}
		});
		GridBagConstraints gbc_btnRemover = new GridBagConstraints();
		gbc_btnRemover.anchor = GridBagConstraints.SOUTH;
		gbc_btnRemover.insets = new Insets(0, 0, 0, 5);
		gbc_btnRemover.gridx = 4;
		gbc_btnRemover.gridy = 8;
		contentPane.add(btnRemover, gbc_btnRemover);
	}
	
	private void configureElementsActions() {
		ActionListener updateTableOnChange = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				generateDisponibilityTable();
			}
		};
		
		classesComboBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				fillWithSelectedClass(GuiUtil.getSelectedItem(classesComboBox));
			}
		});
		
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveChanges();
			}
		});
		
		professorList.setChangeListener(updateTableOnChange);
		slotList.setChangeListener(updateTableOnChange);
	}
	
	private void saveChanges(){
		Class selected = GuiUtil.getSelectedItem(classesComboBox);
		if(selected != null){
			setValuesToClass(selected);
			classService.update(selected);
		} 
		classInformationEdited();
	}
	
	private void setValuesToClass(Class toBeSeted){
		toBeSeted.setName(nameText.getText());
		toBeSeted.setCode(codeText.getText());
		toBeSeted.setCcSemester(ccText.getText());
		toBeSeted.setEcSemester(ecText.getText());
		toBeSeted.setCourse(courseText.getText());
		
		toBeSeted.setProfessors(professorList.getItens());
		toBeSeted.setSlots(slotList.getItens());
	}
	
	private void fillWithSelectedClass(Class selected){
		if(selected == null)
			clearFields();
		else{
			nameText.setText(selected.getName());
			codeText.setText(selected.getCode());
			ccText.setText(selected.getCcSemester() == -1 ? "" : selected.getCcSemester() + "");
			ecText.setText(selected.getEcSemester() == -1 ? "" : selected.getEcSemester() + "");
			courseText.setText(selected.getCourse());
			
			slotList.clear();
			slotList.addElements(selected.getSlots());
	
			professorList.clear();
			professorList.addElements(selected.getProfessors());
			
			generateDisponibilityTable();
		}
	}
	
	private void clearFields(){
		nameText.setText("");
		codeText.setText("");
		ccText.setText("");
		ecText.setText("");
		courseText.setText("");
		
		slotList.clear();
		professorList.clear();
		tabbedPane.removeAll();
	}
	
	private void generateDisponibilityTable(){
		Class selected = new Class(), fromCBox = GuiUtil.getSelectedItem(classesComboBox);
		setValuesToClass(selected);
		if(fromCBox != null) selected.setId(fromCBox.getId());
		
		Iterable<SlotRange> slots = slotList.getItens();
		HashSet<Classroom> rooms = new HashSet<Classroom>();
		for(SlotRange s : slots) if(s.getClassroom() != null) rooms.add(s.getClassroom());
		tabbedPane.removeAll();
		
		ScheduleVisualizationTable table;
		
		for(Classroom r : rooms){
			table = new ScheduleVisualizationTable(new DisponibilityModel(selected, r));
			tabbedPane.addTab(r.getName(), new JScrollPane(table));
		}
		
		if(rooms.isEmpty()){
			Classroom dummyRoom = new Classroom("", -1);
			table = new ScheduleVisualizationTable(new DisponibilityModel(selected, dummyRoom));
			tabbedPane.addTab("Turma sem sala", new JScrollPane(table));
		}
		
	}
	
	public abstract void classInformationEdited();
}
