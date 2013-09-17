package presentation.classes.edition;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.List;

import javax.swing.JScrollPane;

import logic.dataUpdateSystem.CustomerType;
import logic.dataUpdateSystem.DataUpdateCentral;
import logic.dataUpdateSystem.Updatable;
import logic.dataUpdateSystem.UpdateDescription;
import logic.schedule.formatting.formatters.SlotDisponibilityFormatter;
import logic.services.ClassService;
import presentation.NamedPair;
import presentation.PeriodicClassComparator;
import presentation.grouping.GroupSelectorConfiguration;
import presentation.schedule.ScheduleTable;
import presentation.schedule.ScheduleTableModel;
import utilities.GuiUtil;
import utilities.Pair;
import data.persistentEntities.Class;
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
		
		classService = ClassService.createServiceFromCurrentState();
		
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
		if(initialState.openAdditionalWindows())
			createAdditionalWindows(initialState);
	}
	
	private void createAdditionalWindows(InitialEditState initialState){
		new AdditionalVisualizationFrame("Disponibilidade de professores.",	GroupSelectorConfiguration.onlyProfessor(initialState.theProfessor), 0);
		new AdditionalVisualizationFrame("Alocação de semestre", GroupSelectorConfiguration.onlySemester(initialState.theSemester), 1);
		new AdditionalVisualizationFrame("Disponibilidade de Salas.", GroupSelectorConfiguration.onlyRoom(initialState.theRoom), 2);
	}
	
	private void configureElementsData() {
		GuiUtil.reloadCBox(classesCBox, classService.all(), new NamedPair<Class>("Selecione uma disciplina", null));
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
		
		professorList.setChangeListener(updateTableOnChange);
		slotList.setChangeListener(updateTableOnChange);
		
		getSwapsButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Class fromFields = new Class();
				setValuesToClass(fromFields);
				
				new Swapper(EditClass.this, fromFields){
					private static final long serialVersionUID = 1L;

					protected void onOkButton(Collection<Professor> profs, Collection<SlotRange> slots) {
						slotList.clear();
						professorList.clear();
						slotList.addElements(slots);
						professorList.addElements(profs);
						generateDisponibilityTable();
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
	
	private void setValuesToClass(Class toBeSeted){
		Class fromCBox = GuiUtil.getSelectedItem(classesCBox);
		if(fromCBox != null) toBeSeted.setId(fromCBox.getId());
		
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
		Class selected = new Class(); 
		setValuesToClass(selected);
		
		Iterable<SlotRange> slots = slotList.getItens();
		
		List<SlotDisponibilityFormatter> formatters = 
				SlotDisponibilityFormatter.getFormatterForThisClassAndSlots(selected, slots);
		
		String selectedTitle = GuiUtil.getSelectedTabTitle(disponibilityTabbedPane);
		disponibilityTabbedPane.removeAll();
		ScheduleTable table;
		for(SlotDisponibilityFormatter formatter : formatters){
			table = new ScheduleTable(new ScheduleTableModel(formatter));
			disponibilityTabbedPane.addTab(formatter.getRoom().getName(), new JScrollPane(table));
		}
		
		GuiUtil.selectTabWithThisTitle(disponibilityTabbedPane, selectedTitle);
	}
	
	public void onDataUpdate(UpdateDescription desc){
		configureElementsData();
	}
}
