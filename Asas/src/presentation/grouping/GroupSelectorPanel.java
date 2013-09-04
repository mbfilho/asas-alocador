package presentation.grouping;


import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;

import presentation.NamedPair;

import utilities.GuiUtil;

import data.NamedEntity;
import data.persistentEntities.Classroom;
import data.persistentEntities.Professor;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.Vector;

import logic.dataUpdateSystem.CustomerType;
import logic.dataUpdateSystem.DataUpdateCentral;
import logic.dataUpdateSystem.Updatable;
import logic.dataUpdateSystem.UpdateDescription;
import logic.dto.GroupsSelector;
import logic.services.ClassroomService;
import logic.services.ProfessorService;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;

public abstract class GroupSelectorPanel extends JPanel implements Updatable{
	private static final long serialVersionUID = 6925830056459127771L;
	
	private JComboBox<NamedPair<String>> areaCBox;
	private JComboBox<NamedPair<Professor>> professorCBox;
	private JComboBox<NamedPair<String>> semesterCBox;
	private JComboBox<NamedPair<Classroom>> roomCBox;
	private JCheckBox roomCheck;
	private JCheckBox semesterCheck;
	private JCheckBox professorCheck;
	private JCheckBox areaCheck;
	private GroupSelectorConfiguration configuration;
	
	//TODO:Será que existe uma maneira melhor de fazer isso?
	private boolean _updatingProgrammatically;
	
	public abstract void onChangeSelector(GroupsSelector newSeletor);

	public GroupSelectorPanel(){
		this(GroupSelectorConfiguration.configurationForFullSelector());
	}
	
	public GroupSelectorPanel(GroupSelectorConfiguration selectorConfiguration) {
		_updatingProgrammatically = false;
		setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Agrupamento", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.configuration = selectorConfiguration;
		DataUpdateCentral.signIn(this, CustomerType.Gui);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 507, 163, 308, 173, 0};
		gridBagLayout.rowHeights = new int[]{29, 15, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		int column = 1;
		
		if(configuration.isProfessorGroupingEnabled())
			configureProfessorSelector(column++);
		
		if(configuration.isAreaGroupingEnabled())
			configureArea(column++);
		
		if(configuration.isRoomGroupingEnabled())
			configureRoom(column++);

		if(configuration.isSemesterGroupingEnabled())
			configureSemester(column++);
		
		setupInitialValues();
		addDataChangeListeners();//importante que fique por ultimo
	}
	
	private void configureProfessorSelector(int column){
		ProfessorService profService = new ProfessorService();
		Vector<NamedPair<Professor>> professors = GuiUtil.createNamedPairs(profService.all());
		
		professorCBox = new JComboBox<NamedPair<Professor>>(professors);
		professorCBox.setEnabled(false);
		GridBagConstraints gbc_profCBox = new GridBagConstraints();
		gbc_profCBox.insets = new Insets(0, 0, 5, 5);
		gbc_profCBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_profCBox.gridx = column;
		gbc_profCBox.gridy = 0;
		add(professorCBox, gbc_profCBox);

		professorCheck = new JCheckBox("Professor");
		professorCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				professorCBox.setEnabled(professorCheck.isSelected());
			}
		});
		
		GridBagConstraints gbc_profCheck = new GridBagConstraints();
		gbc_profCheck.insets = new Insets(0, 0, 0, 5);
		gbc_profCheck.gridx = column;
		gbc_profCheck.gridy = 1;
		add(professorCheck, gbc_profCheck);
	}
	
	private void configureArea(int column){
		areaCBox = new JComboBox<NamedPair<String>>();
		areaCBox.setEnabled(false);
		
		GridBagConstraints gbc_profileCBox = new GridBagConstraints();
		gbc_profileCBox.insets = new Insets(0, 0, 5, 5);
		gbc_profileCBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_profileCBox.gridx = column;
		gbc_profileCBox.gridy = 0;
		add(areaCBox, gbc_profileCBox);
		
		areaCheck = new JCheckBox("Perfil");

		areaCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				professorCBox.setEnabled(professorCheck.isSelected());
			}
		});
		GridBagConstraints gbc_areaCheck = new GridBagConstraints();
		gbc_areaCheck.insets = new Insets(0, 0, 0, 5);
		gbc_areaCheck.gridx = column;
		gbc_areaCheck.gridy = 1;
		add(areaCheck, gbc_areaCheck);

	}
	
	private void configureRoom(int column){
		ClassroomService roomService = new ClassroomService();
		Vector<NamedPair<Classroom>> rooms = GuiUtil.createNamedPairs(roomService.all());

		roomCBox = new JComboBox<NamedPair<Classroom>>(rooms);
		roomCBox.setEnabled(false);
		
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = column;
		gbc_comboBox.gridy = 0;
		add(roomCBox, gbc_comboBox);
		
		roomCheck = new JCheckBox("Sala");
		GridBagConstraints gbc_roomCheck = new GridBagConstraints();
		gbc_roomCheck.insets = new Insets(0, 0, 0, 5);
		gbc_roomCheck.gridx = column;
		gbc_roomCheck.gridy = 1;
		add(roomCheck, gbc_roomCheck);
		roomCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				roomCBox.setEnabled(roomCheck.isSelected());
			}
		});
	}
	
	private void configureSemester(int column){
		Vector<NamedPair<String>> periodos = new Vector<NamedPair<String>>();
		for(int i = 1; i <= 10; ++i) periodos.add(new NamedPair<String>(i + "", i + ""));
		semesterCBox = new JComboBox<NamedPair<String>>(periodos);
		semesterCBox.setEnabled(false);
		
		GridBagConstraints gbc_semesterCBox = new GridBagConstraints();
		gbc_semesterCBox.insets = new Insets(0, 0, 5, 0);
		gbc_semesterCBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_semesterCBox.gridx = column;
		gbc_semesterCBox.gridy = 0;
		add(semesterCBox, gbc_semesterCBox);
		
		semesterCheck = new JCheckBox("Período");
		
		semesterCheck.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				semesterCBox.setEnabled(semesterCheck.isSelected());
			}
		});
		
		GridBagConstraints gbc_periodoCheck = new GridBagConstraints();
		gbc_periodoCheck.gridx = column;
		gbc_periodoCheck.gridy = 1;
		add(semesterCheck, gbc_periodoCheck);
	}
	
	private void setupInitialValues() {
		if(configuration != null){
			if(configuration.isProfessorGroupingEnabled() && configuration.getProfessor() != null){
				professorCheck.setSelected(true);
				professorCBox.setEnabled(true);
				GuiUtil.setSelectedValue(professorCBox, configuration.getProfessor());
			}
			
			if(configuration.isRoomGroupingEnabled() && configuration.getRoom() != null){
				roomCheck.setSelected(true);
				roomCBox.setEnabled(true);
				GuiUtil.setSelectedValue(roomCBox, configuration.getRoom() );
			}
			if(configuration.isAreaGroupingEnabled() && configuration.getArea() != null){
				areaCheck.setSelected(true);
				GuiUtil.setSelectedValue(areaCBox, configuration.getArea());
			}
			if(configuration.isSemesterGroupingEnabled() && configuration.getSemester() != -1){
				semesterCheck.setSelected(true);
				semesterCBox.setEnabled(true);
				GuiUtil.setSelectedValue(semesterCBox, "" + configuration.getSemester());
			}
		}
	}
	
	private void addDataChangeListeners() {
		ActionListener onChangeFilter = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(!_updatingProgrammatically)
					onChangeSelector(getSelector());
			}
		};	
		
		if(configuration.isRoomGroupingEnabled()){
			roomCBox.addActionListener(onChangeFilter);
			roomCheck.addActionListener(onChangeFilter);
		}
		
		if(configuration.isSemesterGroupingEnabled()){
			semesterCBox.addActionListener(onChangeFilter);
			semesterCheck.addActionListener(onChangeFilter);
		}
		
		if(configuration.isAreaGroupingEnabled()){
			areaCBox.addActionListener(onChangeFilter);
			areaCheck.addActionListener(onChangeFilter);
		}
		
		if(configuration.isProfessorGroupingEnabled()){
			professorCBox.addActionListener(onChangeFilter);
			professorCheck.addActionListener(onChangeFilter);
		}
	}

	public GroupsSelector getSelector(){
		GroupsSelector selector = new GroupsSelector();
		if(configuration.isProfessorGroupingEnabled() && professorCheck.isSelected()) 
			selector.setProfessor(GuiUtil.getSelectedItem(professorCBox));
		if(configuration.isAreaGroupingEnabled() && areaCheck.isSelected()) 
			selector.setArea(GuiUtil.getSelectedItem(areaCBox));
		if(configuration.isSemesterGroupingEnabled() && semesterCheck.isSelected()) 
			selector.setSemester(GuiUtil.getSelectedItem(semesterCBox));
		if(configuration.isRoomGroupingEnabled() && roomCheck.isSelected()) 
			selector.setRoom(GuiUtil.getSelectedItem(roomCBox));
		return selector;
	}
	
	public void refresh() {
		_updatingProgrammatically = true;
		if(configuration.isProfessorGroupingEnabled())
			refreshProfessors();
		if(configuration.isRoomGroupingEnabled())
			refreshRooms();
		_updatingProgrammatically = false;
		onChangeSelector(getSelector());
	}
	
	public void onDataUpdate(UpdateDescription desc) {
		refresh();
	}
	
	private void refreshProfessors(){
		ProfessorService profService = new ProfessorService();
		reloadCBox(professorCBox, profService.all());
	}
	
	private void refreshRooms(){
		ClassroomService roomService = new ClassroomService();
		reloadCBox(roomCBox, roomService.all());
	}
	
	private <T extends NamedEntity> void reloadCBox(JComboBox<NamedPair<T>> cbox, Collection<T> allElements){
		T selected = GuiUtil.getSelectedItem(cbox);
		DefaultComboBoxModel<NamedPair<T>> model = (DefaultComboBoxModel<NamedPair<T>>) cbox.getModel();
		model.removeAllElements();
		NamedPair<T> newSelected = null;
		for(T elementToAdd : allElements){
			NamedPair<T> toAdd = new NamedPair<T>(elementToAdd.getName(), elementToAdd);
			if(selected != null && elementToAdd.getName().equals(selected.getName())) newSelected = toAdd;
			model.addElement(toAdd);
		}
		cbox.setSelectedItem(newSelected);
	}

	public void dispose(){
		DataUpdateCentral.signOut(this);
	}
}
