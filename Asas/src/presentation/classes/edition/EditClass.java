package presentation.classes.edition;


import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashSet;
import java.util.Vector;

import schedule.table.models.DisponibilityModel;
import utilities.GuiUtil;
import utilities.Pair;

import javax.swing.JScrollPane;

import presentation.NamedPair;
import presentation.PeriodicClassComparator;
import presentation.grouping.GroupSelectorConfiguration;
import presentation.schedule.ScheduleTable;

import logic.dataUpdateSystem.CustomerType;
import logic.dataUpdateSystem.DataUpdateCentral;
import logic.dataUpdateSystem.Updatable;
import logic.dataUpdateSystem.UpdateDescription;
import logic.services.ClassService;


import data.persistentEntities.Class;
import data.persistentEntities.Classroom;
import data.persistentEntities.Professor;
import data.persistentEntities.SlotRange;


public class EditClass extends EditClassLayout implements Updatable{

	private static final long serialVersionUID = 679979857489504936L;
	private ClassService classService;
	private PeriodicClassComparator classComparator;
	
	public EditClass(){
		this(null);
	}
	
	public EditClass(InitialEditState initialState) {
		DataUpdateCentral.signIn(this, CustomerType.Gui);
		
		classService = new ClassService();
		
		configureElementsData();
		configureElementsActions();
		setupInitialState(initialState);
		classComparator = new PeriodicClassComparator() {
			protected void onChangeState(boolean isDirty) {
				getChangesHappenedwarningLabel().setVisible(isDirty);
			}
			
			protected Pair<Class, Class> getClassesToCompare() {
				Class one = new Class(), another = GuiUtil.getSelectedItem(classesCBox);
				setValuesToClass(one);
				return new Pair<Class, Class>(one, another);
			}
		};
		setVisible(true);
	}
	
	public void dispose(){
		super.dispose();
		DataUpdateCentral.signOut(this);
		classComparator.stopThread();
	}
	
	private void setupInitialState(InitialEditState initialState) {
		if(initialState == null) return;
		GuiUtil.setSelectedValue(classesCBox, initialState.theClass);
		createAdditionalWindows(initialState);
	}
	
	private void createAdditionalWindows(InitialEditState initialState){
		new AdditionalVisualizationFrame("Disponibilidade de professores.",	GroupSelectorConfiguration.onlyProfessor(initialState.theProfessor), 0);
		new AdditionalVisualizationFrame("Alocação de semestre", GroupSelectorConfiguration.onlySemester(initialState.theSemester), 1);
		new AdditionalVisualizationFrame("Disponibilidade de Salas.", GroupSelectorConfiguration.onlyRoom(initialState.theRoom), 2);
	}
	
	private void configureElementsData() {
		Vector<NamedPair<Class>> classesData = GuiUtil.createNamedPairs(classService.all());
		classesData.add(0, new NamedPair<Class>("Selecione uma disciplina", null));
		DefaultComboBoxModel<NamedPair<Class>> model = (DefaultComboBoxModel<NamedPair<Class>>) classesCBox.getModel();
		for(NamedPair<Class> pair : classesData)
			model.addElement(pair);
	}
	
	private void configureElementsActions() {
		ActionListener updateTableOnChange = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				generateDisponibilityTable();
			}
		};
		
		classesCBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				fillWithSelectedClass(GuiUtil.getSelectedItem(classesCBox));
			}
		});
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveEdition();
			}
		});
		
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(GuiUtil.getSelectedItem(classesCBox) == null) return;
				
				int op = JOptionPane.showConfirmDialog(
							EditClass.this, 
							"Tem certeza que deseja excluir a turma \"" + GuiUtil.getSelectedItem(classesCBox).getName() + "\"?",
							"Confirmar exclusão de turma",
							JOptionPane.YES_NO_OPTION
						);
				if(op == JOptionPane.YES_OPTION){
					Class toDelete = GuiUtil.getSelectedItem(classesCBox);
					classesCBox.removeItem(classesCBox.getSelectedItem());
					saveRemotion(toDelete);
				}
			}
		});
		
		professorList.setChangeListener(updateTableOnChange);
		slotList.setChangeListener(updateTableOnChange);
		
		getSwapsButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Swapper(EditClass.this, GuiUtil.getSelectedItem(classesCBox)){

					protected void onOkButton(Collection<Professor> profs, Collection<SlotRange> slots) {
						
					}
				};
			}
		});
	}
	
	private void saveEdition(){
		Class selected = GuiUtil.getSelectedItem(classesCBox);
		if(selected != null){
			setValuesToClass(selected);
			classService.update(selected);
		}
		getChangesHappenedwarningLabel().setVisible(false);
	}
	
	
	private void saveRemotion(Class toDelete){
		classService.remove(toDelete);
		getChangesHappenedwarningLabel().setVisible(false);
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
		disponibilityTabbedPane.removeAll();
	}
	
	private void generateDisponibilityTable(){
		Class selected = new Class(), fromCBox = GuiUtil.getSelectedItem(classesCBox);
		setValuesToClass(selected);
		if(fromCBox != null) selected.setId(fromCBox.getId());
		
		Iterable<SlotRange> slots = slotList.getItens();
		HashSet<Classroom> rooms = new HashSet<Classroom>();
		for(SlotRange s : slots) if(s.getClassroom() != null) rooms.add(s.getClassroom());
		disponibilityTabbedPane.removeAll();
		
		ScheduleTable table;
		
		for(Classroom r : rooms){
			table = new ScheduleTable(new DisponibilityModel(selected, r));
			disponibilityTabbedPane.addTab(r.getName(), new JScrollPane(table));
		}
		
		if(rooms.isEmpty()){
			Classroom dummyRoom = new Classroom("", -1);
			table = new ScheduleTable(new DisponibilityModel(selected, dummyRoom));
			disponibilityTabbedPane.addTab("Turma sem sala", new JScrollPane(table));
		}
		
	}
	
	public void onDataUpdate(UpdateDescription desc){
		generateDisponibilityTable();
	}
}
