package groupMaker;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

import services.ClassroomService;
import services.ProfessorService;
import utilities.GuiUtil;

import basic.Classroom;
import basic.NamedEntity;
import basic.Professor;

import classEditor.NamedPair;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.Vector;

public abstract class FilterChooser extends JPanel {
	private static final long serialVersionUID = 6925830056459127771L;
	
	private JComboBox<NamedPair<String>> areaCBox;
	private JComboBox<NamedPair<Professor>> profCBox;
	private JComboBox<NamedPair<String>> periodoCBox;
	private JComboBox<NamedPair<Classroom>> roomCBox;
	private JCheckBox roomCheck;
	private JCheckBox periodoCheck;
	private JCheckBox profCheck;
	private JCheckBox areaCheck;
	public static int PROFESSOR = 1 << 0, AREA = 1 << 1, SEMESTER = 1 << 2, ROOM = 1 << 3;
	public static int ALL = PROFESSOR | AREA | SEMESTER | ROOM;
	private int configuration;
	
	public abstract void onChangeFilter(ClassFilter newFilter);

	public FilterChooser(){
		this(ALL, null);
	}
	
	public FilterChooser(int configuration, InitialFilterConfiguration initialConfiguration) {
		this.configuration = configuration;
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 507, 163, 308, 173, 0};
		gridBagLayout.rowHeights = new int[]{29, 15, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblFiltros = new JLabel("Filtros:");
		GridBagConstraints gbc_lblFiltros = new GridBagConstraints();
		gbc_lblFiltros.anchor = GridBagConstraints.WEST;
		gbc_lblFiltros.insets = new Insets(0, 0, 5, 5);
		gbc_lblFiltros.gridx = 0;
		gbc_lblFiltros.gridy = 0;
		add(lblFiltros, gbc_lblFiltros);
		
		if(isProfessorFilteringEnabled())
			configureProfessor();
		
		if(isAreaFilteringEnabled())
			configureArea();
		
		if(isSemesterFilteringEnabled())
			configureSemester();
		
		if(isRoomFilteringEnabled())
			configureRoom();
		
		setupInitialConfiguration(initialConfiguration);
		addDataChangeListeners();//importante que fique por ultimo
	}
	
	private void addDataChangeListeners() {
		ActionListener onChangeFilter = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				onChangeFilter(getFilter());
			}
		};	
		
		if(isRoomFilteringEnabled()){
			roomCBox.addActionListener(onChangeFilter);
			roomCheck.addActionListener(onChangeFilter);
		}
		
		if(isSemesterFilteringEnabled()){
			periodoCBox.addActionListener(onChangeFilter);
			periodoCheck.addActionListener(onChangeFilter);
		}
		
		if(isAreaFilteringEnabled()){
			areaCBox.addActionListener(onChangeFilter);
			areaCheck.addActionListener(onChangeFilter);
		}
		
		if(isProfessorFilteringEnabled()){
			profCBox.addActionListener(onChangeFilter);
			profCheck.addActionListener(onChangeFilter);
		}
	}

	private void setupInitialConfiguration(InitialFilterConfiguration initialConfig) {
		if(initialConfig != null){
			if(isProfessorFilteringEnabled() && initialConfig.theProfessor != null){
				profCheck.setSelected(true);
				profCBox.setEnabled(true);
				GuiUtil.setSelectedValue(profCBox, initialConfig.theProfessor);
			}
			
			if(isRoomFilteringEnabled() && initialConfig.theRoom != null){
				roomCheck.setSelected(true);
				roomCBox.setEnabled(true);
				GuiUtil.setSelectedValue(roomCBox, initialConfig.theRoom);
			}
			if(isAreaFilteringEnabled() && initialConfig.area != null){
				areaCheck.setSelected(true);
				GuiUtil.setSelectedValue(areaCBox, initialConfig.area);
			}
			if(isSemesterFilteringEnabled() && initialConfig.theSemester != -1){
				periodoCheck.setSelected(true);
				periodoCBox.setEnabled(true);
				GuiUtil.setSelectedValue(periodoCBox, initialConfig.theSemester);
			}
		}
	}

	private void configureRoom(){
		ClassroomService roomService = new ClassroomService();
		Vector<NamedPair<Classroom>> rooms = GuiUtil.createNamedPairs(roomService.all());

		roomCBox = new JComboBox<NamedPair<Classroom>>(rooms);
		roomCBox.setEnabled(false);
		
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 3;
		gbc_comboBox.gridy = 0;
		add(roomCBox, gbc_comboBox);
		
		roomCheck = new JCheckBox("Sala");
		GridBagConstraints gbc_chckbxSala = new GridBagConstraints();
		gbc_chckbxSala.insets = new Insets(0, 0, 0, 5);
		gbc_chckbxSala.gridx = 3;
		gbc_chckbxSala.gridy = 1;
		add(roomCheck, gbc_chckbxSala);
		roomCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				roomCBox.setEnabled(roomCheck.isSelected());
			}
		});
	}
	
	private void configureSemester(){
		Vector<NamedPair<String>> periodos = new Vector<NamedPair<String>>();
		for(int i = 1; i <= 10; ++i) periodos.add(new NamedPair<String>(i + "", i + ""));
		periodoCBox = new JComboBox<NamedPair<String>>(periodos);
		periodoCBox.setEnabled(false);
		
		GridBagConstraints gbc_periodoCBox = new GridBagConstraints();
		gbc_periodoCBox.insets = new Insets(0, 0, 5, 0);
		gbc_periodoCBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_periodoCBox.gridx = 4;
		gbc_periodoCBox.gridy = 0;
		add(periodoCBox, gbc_periodoCBox);
		
		periodoCheck = new JCheckBox("Período");
		
		periodoCheck.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				periodoCBox.setEnabled(periodoCheck.isSelected());
			}
		});
		
		GridBagConstraints gbc_periodoCheck = new GridBagConstraints();
		gbc_periodoCheck.gridx = 4;
		gbc_periodoCheck.gridy = 1;
		add(periodoCheck, gbc_periodoCheck);
	}
	
	private void configureArea(){
		areaCBox = new JComboBox<NamedPair<String>>();
		areaCBox.setEnabled(false);
		
		GridBagConstraints gbc_profileCBox = new GridBagConstraints();
		gbc_profileCBox.insets = new Insets(0, 0, 5, 5);
		gbc_profileCBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_profileCBox.gridx = 2;
		gbc_profileCBox.gridy = 0;
		add(areaCBox, gbc_profileCBox);
		
		areaCheck = new JCheckBox("Perfil");

		areaCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				profCBox.setEnabled(profCheck.isSelected());
			}
		});
		GridBagConstraints gbc_areaCheck = new GridBagConstraints();
		gbc_areaCheck.insets = new Insets(0, 0, 0, 5);
		gbc_areaCheck.gridx = 2;
		gbc_areaCheck.gridy = 1;
		add(areaCheck, gbc_areaCheck);

	}
	
	private void configureProfessor(){
		ProfessorService profService = new ProfessorService();
		Vector<NamedPair<Professor>> professors = GuiUtil.createNamedPairs(profService.all());
		
		profCBox = new JComboBox<NamedPair<Professor>>(professors);
		profCBox.setEnabled(false);
		GridBagConstraints gbc_profCBox = new GridBagConstraints();
		gbc_profCBox.insets = new Insets(0, 0, 5, 5);
		gbc_profCBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_profCBox.gridx = 1;
		gbc_profCBox.gridy = 0;
		add(profCBox, gbc_profCBox);

		profCheck = new JCheckBox("Professor");
		profCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				profCBox.setEnabled(profCheck.isSelected());
			}
		});
		
		GridBagConstraints gbc_profCheck = new GridBagConstraints();
		gbc_profCheck.insets = new Insets(0, 0, 0, 5);
		gbc_profCheck.gridx = 1;
		gbc_profCheck.gridy = 1;
		add(profCheck, gbc_profCheck);
	}
	
	private boolean isProfessorFilteringEnabled(){
		return (configuration & PROFESSOR) != 0;
	}
	
	private boolean isAreaFilteringEnabled(){
		return (configuration & AREA) != 0;
	}
	
	private boolean isSemesterFilteringEnabled(){
		return (configuration & SEMESTER) != 0;
	}
	
	private boolean isRoomFilteringEnabled(){
		return (configuration & ROOM) != 0;
	}
	
	public ClassFilter getFilter(){
		ClassFilter filter = new ClassFilter();
		if(isProfessorFilteringEnabled() && profCheck.isSelected()) filter.setProfessor(GuiUtil.getSelectedItem(profCBox));
		if(isAreaFilteringEnabled() && areaCheck.isSelected()) filter.setArea(GuiUtil.getSelectedItem(areaCBox));
		if(isSemesterFilteringEnabled() && periodoCheck.isSelected()) filter.setSemester(GuiUtil.getSelectedItem(periodoCBox));
		if(isRoomFilteringEnabled() && roomCheck.isSelected()) filter.setRoom(GuiUtil.getSelectedItem(roomCBox));
		return filter;
	}
	
	private <T extends NamedEntity> void reloadCBox(JComboBox<NamedPair<T>> cbox, Collection<T> allElements){
		T selected = GuiUtil.getSelectedItem(cbox);
		DefaultComboBoxModel<NamedPair<T>> model = (DefaultComboBoxModel<NamedPair<T>>) cbox.getModel();
		model.removeAllElements();
		NamedPair<T> newSelected = null;
		for(T elementToAdd : allElements){
			NamedPair<T> toAdd = new NamedPair<T>(elementToAdd.getName(), elementToAdd);
			if(elementToAdd == selected) newSelected = toAdd;
			model.addElement(toAdd);
		}
		cbox.setSelectedItem(newSelected);
	}
	
	private void refreshProfessors(){
		ProfessorService profService = new ProfessorService();
		reloadCBox(profCBox, profService.all());
	}
	
	private void refreshRooms(){
		ClassroomService roomService = new ClassroomService();
		reloadCBox(roomCBox, roomService.all());
	}
	
	public void refresh() {
		refreshProfessors();
		refreshRooms();
	}
	
}
