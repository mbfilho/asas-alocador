package classEditor;

import group.filtering.FilterChooser;
import group.filtering.InitialFilterConfiguration;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Vector;

import basic.Class;
import basic.Classroom;
import basic.SlotRange;
import schedule.table.ScheduleVisualizationTable;
import schedule.table.models.DisponibilityModel;
import services.ClassService;
import services.ProfessorService;
import utilities.GuiUtil;

import javax.swing.JScrollPane;

import dataUpdateSystem.RegistrationCentral;
import dataUpdateSystem.Updatable;
import dataUpdateSystem.UpdateDescription;


public class EditClassFrame extends EditClassFrameLayout implements Updatable{

	private static final long serialVersionUID = 679979857489504936L;
	private ClassService classService;
	
	public EditClassFrame(){
		this(null);
	}
	
	public EditClassFrame(InitialEditState initialState) {
		RegistrationCentral.signIn(this);
		
		classService = new ClassService();
		
		configureElementsData();
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
		
		new AdditionalVisualizationFrame("Disponibilidade de professores.",	FilterChooser.PROFESSOR, 
				initialFiltering, 0);
		new AdditionalVisualizationFrame("Alocação de semestre", FilterChooser.SEMESTER,
				initialFiltering, 1);
		new AdditionalVisualizationFrame("Disponibilidade de Salas.", FilterChooser.ROOM, 
				initialFiltering, 2);
	}
	
	private void configureElementsData() {
		Vector<NamedPair<Class>> classesData = GuiUtil.createNamedPairs(classService.all());
		classesData.add(0, new NamedPair<Class>("Selecione uma disciplina", null));
		DefaultComboBoxModel<NamedPair<Class>> model = (DefaultComboBoxModel<NamedPair<Class>>) classesComboBox.getModel();
		for(NamedPair<Class> pair : classesData)
			model.addElement(pair);
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
		
		professorList.setChangeListener(updateTableOnChange);
		slotList.setChangeListener(updateTableOnChange);
	}
	
	private void saveChanges(){
		Class selected = GuiUtil.getSelectedItem(classesComboBox);
		if(selected != null){
			setValuesToClass(selected);
			classService.update(selected);
		} 
		RegistrationCentral.registerUpdate("Edição de turma");
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
	
	public void onDataUpdate(UpdateDescription desc){
		generateDisponibilityTable();
	}
}
