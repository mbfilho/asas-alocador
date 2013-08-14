package classEditor;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dataUpdateSystem.RegistrationCentral;
import dataUpdateSystem.Updatable;
import dataUpdateSystem.UpdateDescription;

import scheduleTable.DisponibilityModel;
import scheduleTable.ScheduleVisualizationTable;
import services.ClassService;
import services.ProfessorService;
import services.WarningGeneratorService;
import utilities.DisposableOnEscFrame;
import basic.Classroom;
import basic.Professor;
import basic.SlotRange;
import basic.Class;

public abstract class AddClassFrame extends DisposableOnEscFrame implements Updatable{
	private static final long serialVersionUID = 679979857489504936L;
	protected JPanel contentPane;
	protected JTextField nameText;
	protected JTextField codeText;
	protected JTextField ccText;
	protected JTextField ecText;
	protected JTextField courseText;
	protected JLabel lblEc;
	protected JLabel lblCc;
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
	
	public AddClassFrame(WarningGeneratorService warningService) {
		RegistrationCentral.register(this);
		
		classService = new ClassService();
		professorService = new ProfessorService();
		if(professorService == null) throw new RuntimeException();
		configureElements(warningService);
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

	private void configureElements(WarningGeneratorService warningService) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 658, 579);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{95, 0, 44, 0, 47, 161, 302, 0};
		gbl_contentPane.rowHeights = new int[]{26, 0, 0, 0, 0, 148, 161, 95, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.gridheight = 8;
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 6;
		gbc_tabbedPane.gridy = 0;
		contentPane.add(tabbedPane, gbc_tabbedPane);
				
		lblNome = new JLabel("Nome");
		GridBagConstraints gbc_lblNome = new GridBagConstraints();
		gbc_lblNome.anchor = GridBagConstraints.WEST;
		gbc_lblNome.insets = new Insets(0, 0, 5, 5);
		gbc_lblNome.gridx = 0;
		gbc_lblNome.gridy = 1;
		contentPane.add(lblNome, gbc_lblNome);
		
		nameText = new JTextField();
		GridBagConstraints gbc_txtName = new GridBagConstraints();
		gbc_txtName.gridwidth = 5;
		gbc_txtName.insets = new Insets(0, 0, 5, 5);
		gbc_txtName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtName.gridx = 1;
		gbc_txtName.gridy = 1;
		contentPane.add(nameText, gbc_txtName);
		nameText.setColumns(10);
		
		lblCdigo = new JLabel("Código");
		GridBagConstraints gbc_lblCdigo = new GridBagConstraints();
		gbc_lblCdigo.anchor = GridBagConstraints.WEST;
		gbc_lblCdigo.insets = new Insets(0, 0, 5, 5);
		gbc_lblCdigo.gridx = 0;
		gbc_lblCdigo.gridy = 2;
		contentPane.add(lblCdigo, gbc_lblCdigo);
		
		codeText = new JTextField();
		GridBagConstraints gbc_txtCode = new GridBagConstraints();
		gbc_txtCode.gridwidth = 5;
		gbc_txtCode.insets = new Insets(0, 0, 5, 5);
		gbc_txtCode.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCode.gridx = 1;
		gbc_txtCode.gridy = 2;
		contentPane.add(codeText, gbc_txtCode);
		codeText.setColumns(10);
		
		lblPerodo = new JLabel("Período");
		GridBagConstraints gbc_lblPerodo = new GridBagConstraints();
		gbc_lblPerodo.anchor = GridBagConstraints.WEST;
		gbc_lblPerodo.insets = new Insets(0, 0, 5, 5);
		gbc_lblPerodo.gridx = 0;
		gbc_lblPerodo.gridy = 3;
		contentPane.add(lblPerodo, gbc_lblPerodo);
		
		lblCc = new JLabel("cc");
		GridBagConstraints gbc_lblCc = new GridBagConstraints();
		gbc_lblCc.insets = new Insets(0, 0, 5, 5);
		gbc_lblCc.anchor = GridBagConstraints.EAST;
		gbc_lblCc.gridx = 1;
		gbc_lblCc.gridy = 3;
		contentPane.add(lblCc, gbc_lblCc);
		
		ccText = new JTextField();
		GridBagConstraints gbc_txtCc = new GridBagConstraints();
		gbc_txtCc.insets = new Insets(0, 0, 5, 5);
		gbc_txtCc.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCc.gridx = 2;
		gbc_txtCc.gridy = 3;
		contentPane.add(ccText, gbc_txtCc);
		ccText.setColumns(10);
		
		lblEc = new JLabel("ec");
		GridBagConstraints gbc_lblEc = new GridBagConstraints();
		gbc_lblEc.insets = new Insets(0, 0, 5, 5);
		gbc_lblEc.anchor = GridBagConstraints.EAST;
		gbc_lblEc.gridx = 3;
		gbc_lblEc.gridy = 3;
		contentPane.add(lblEc, gbc_lblEc);
		
		ecText = new JTextField();
		GridBagConstraints gbc_txtEc = new GridBagConstraints();
		gbc_txtEc.insets = new Insets(0, 0, 5, 5);
		gbc_txtEc.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEc.gridx = 4;
		gbc_txtEc.gridy = 3;
		contentPane.add(ecText, gbc_txtEc);
		ecText.setColumns(10);
		
		lblCurso = new JLabel("Curso");
		GridBagConstraints gbc_lblCurso = new GridBagConstraints();
		gbc_lblCurso.anchor = GridBagConstraints.WEST;
		gbc_lblCurso.insets = new Insets(0, 0, 5, 5);
		gbc_lblCurso.gridx = 0;
		gbc_lblCurso.gridy = 4;
		contentPane.add(lblCurso, gbc_lblCurso);
		
		courseText = new JTextField();
		GridBagConstraints gbc_txtCourse = new GridBagConstraints();
		gbc_txtCourse.gridwidth = 5;
		gbc_txtCourse.insets = new Insets(0, 0, 5, 5);
		gbc_txtCourse.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCourse.gridx = 1;
		gbc_txtCourse.gridy = 4;
		contentPane.add(courseText, gbc_txtCourse);
		courseText.setColumns(10);
		
		professorList = new EditableJList<Professor>("Professores", professorService.all());
		GridBagConstraints gbc_profList = new GridBagConstraints();
		gbc_profList.gridwidth = 6;
		gbc_profList.fill = GridBagConstraints.BOTH;
		gbc_profList.insets = new Insets(0, 0, 5, 5);
		gbc_profList.gridx = 0;
		gbc_profList.gridy = 5;
		contentPane.add(professorList, gbc_profList);
		
		slotList = new EditableSlotList("Horários (duplo clique para editar)", warningService){
			public Class getSelectedClass() {
				Class dummy = new Class();
				setValuesToClass(dummy);
				dummy.setId(-1);
				return dummy;
			}
		};
		GridBagConstraints gbc_slotList = new GridBagConstraints();
		gbc_slotList.gridwidth = 6;
		gbc_slotList.fill = GridBagConstraints.BOTH;
		gbc_slotList.insets = new Insets(0, 0, 5, 5);
		gbc_slotList.gridx = 0;
		gbc_slotList.gridy = 6;
		contentPane.add(slotList, gbc_slotList);
		
		btnOk = new JButton("Salvar");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveChanges();
			}
		});
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.anchor = GridBagConstraints.SOUTH;
		gbc_btnOk.insets = new Insets(0, 0, 0, 5);
		gbc_btnOk.gridx = 0;
		gbc_btnOk.gridy = 7;
		contentPane.add(btnOk, gbc_btnOk);	
		configureElementsActions();
		setVisible(true);
	}
	
	private void configureElementsActions() {
		ActionListener updateTableOnChange = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				generateDisponibilityTable();
			}
		};
		professorList.setChangeListener(updateTableOnChange);
		slotList.setChangeListener(updateTableOnChange);
	}

	protected void saveChanges(){
		Class selected = getClassFromFields();
		classService.add(selected);
		onAddClass();
	}
	
	private void generateDisponibilityTable(){
		Class selected = getClassFromFields();
		Iterable<SlotRange> slots = slotList.getItens();
		HashSet<Classroom> rooms = new HashSet<Classroom>();
		for(SlotRange s : slots) if(s.getClassroom() != null) rooms.add(s.getClassroom());
		tabbedPane.removeAll();
		
		for(Classroom r : rooms){
			ScheduleVisualizationTable table = new ScheduleVisualizationTable(new DisponibilityModel(selected, r));
			tabbedPane.addTab(r.getName(), new JScrollPane(table));
		}
	}
			
	private Class getClassFromFields(){
		Class filledClass = new Class();
		setValuesToClass(filledClass);
		filledClass.setId(-1);
		return filledClass;
	}
	
	public abstract void onAddClass();

	public void onDataUpdate(UpdateDescription desc) {
		generateDisponibilityTable();
	}
}
