package edit;
/*
import htmlGenerator.PageWithTables;
import htmlGenerator.ScheduleTable;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.xhtmlrenderer.resource.XMLResource;
import org.xhtmlrenderer.simple.XHTMLPanel;

import statePersistence.State;
import statePersistence.StateService;
import validation.WarningService;
import visualizer.EditableJList;

import basic.Class;
import basic.Classroom;
import basic.NamedEntity;
import basic.Professor;
import basic.SlotRange;

public abstract class ClassEditor extends JFrame{
	private int X1 = 30;
	
	private JLabel selectLabel;
	private JComboBox classesComboBox;
	
	private JLabel nameLabel;
	private JTextField nameText;
	
	private JLabel codeLabel;
	private JTextField codeText;
	
	private JLabel classRoomsLabel;
	private JComboBox classRooms;

	private EditableJList<Professor> professorList;
	
	private JLabel semesterLabel;
	
	private JLabel ccLabel;
	private JTextField ccText;
	
	private JLabel ecLabel;
	private JTextField ecText;
	
	private JLabel courseLabel;
	private JTextField courseText;
	
	private EditableJList<SlotRange> slotList; 
	
	private JButton saveButton;
	
	private XHTMLPanel htmlPanel;
	
	private WarningService warningService;
		
	public ClassEditor(WarningService warServ){
		this.warningService = warServ;
		
		this.setLayout(null);
		this.setMinimumSize(new Dimension(1000, 800));
		if(StateService.getInstance().hasValidState()){
			configureElements();
			this.setVisible(true);
		}else{
			JOptionPane.showMessageDialog(null, "Nenhum estado válido foi carregado.");
		}
	}
	
	public abstract void classInformationEdited();
	
	private <T extends NamedEntity> Vector<NamedPair<T>> createNamedPairs(Collection<T> objs){
		Vector<NamedPair<T>> namedPairs = new Vector<NamedPair<T>>();
		for(T obj : objs)
			namedPairs.add(new NamedPair<T>(obj.getName(), obj));
		return namedPairs;
	}
	
	private void configureElements(){
		State currentState = StateService.getInstance().getCurrentState();
		int y = 40, x = 30, height = 20, space = 10;
		ActionListener updateTableOnChange = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				generateDisponibilityTable();
			}};
			
		selectLabel = new JLabel("Selecione uma disciplina:");
		selectLabel.setBounds(x, y, 300, height);
		add(selectLabel);
		
		y += height + space;
		Vector<NamedPair<Class>> classesData = createNamedPairs(currentState.classes.all());
		classesData.add(0, new NamedPair<Class>("Selecione uma disciplina", null));
		classesComboBox = new JComboBox(classesData);
		classesComboBox.setBounds(x, y, 300+space, height);
		classesComboBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				setSelectedClass(((NamedPair<Class>)classesComboBox.getSelectedItem()).data);
			}});
		add(classesComboBox);
		
		y += height + 4 * space;
		nameLabel = new JLabel("Nome");
		nameLabel.setBounds(x, y, 100, height);
		add(nameLabel);
		
		nameText = new JTextField();
		nameText.setBounds(x+100+space, y, 200, height);
		add(nameText);
		
		y += height + space;
		codeLabel = new JLabel("Codigo");
		codeLabel.setBounds(x, y, 100, height);
		add(codeLabel);
		
		codeText = new JTextField();
		codeText.setBounds(x+100+space, y, 200, height);
		add(codeText);
		
		y += height + space;
		classRoomsLabel = new JLabel("Sala");
		classRoomsLabel.setBounds(x,y,200,height);
		add(classRoomsLabel);
		
		Vector<NamedPair<Classroom>> roomsData = createNamedPairs(currentState.classrooms.all());
		roomsData.add(0, new NamedPair<Classroom>("Selecione uma sala", null));
		classRooms = new JComboBox(roomsData);
		classRooms.setBounds(x+100+space, y, 200, height);
		classRooms.addActionListener(updateTableOnChange);
		add(classRooms);
		
		y += height + space;
		semesterLabel = new JLabel("Periodo");
		semesterLabel.setBounds(x, y, 100, height);
		add(semesterLabel);
		
		x += 100 + space;
		ccLabel = new JLabel("cc");
		ccLabel.setBounds(x, y, 20, height);
		add(ccLabel);
		
		ccText = new JTextField();
		ccText.setBounds(x+20, y, 30, 20);
		add(ccText);
		
		ecLabel = new JLabel("ec");
		ecLabel.setBounds((x+20)+30+space, y, 20, 20);
		add(ecLabel);
		
		ecText = new JTextField();
		ecText.setBounds((x+50+space)+20, y, 30, 20);
		add(ecText);
		x -= 100 + space;
		
		y += height + space;
		courseLabel = new JLabel("Curso");
		courseLabel.setBounds(x,y,100,height);
		add(courseLabel);
		
		courseText = new JTextField("");
		courseText.setBounds(x+100+space, y, 200, height);
		add(courseText);
		
		y += height + space;
		professorList = new EditableJList("Professores:", currentState.professors.all());
		professorList.setBounds(x, y, professorList.getWidth(), professorList.getHeight());
		professorList.addActionListener(updateTableOnChange);
		add(professorList);
				
		y += professorList.getHeight() + space;
		slotList = new EditableJList<SlotRange>("Horários", SlotRange.all());
		slotList.setBounds(x, y, slotList.getWidth(), slotList.getHeight());
		slotList.addActionListener(updateTableOnChange);
		add(slotList);
		
		y += slotList.getHeight() + space;
		saveButton = new JButton("Salvar");
		saveButton.setBounds(x, y, 100, height);
		saveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				saveChanges();
				classInformationEdited();
			}
		});
		add(saveButton);
		
		htmlPanel = new XHTMLPanel();
		JScrollPane jp = new JScrollPane(htmlPanel);
		jp.setBounds(400, 40, 590, 610);
		add(jp);
	}
	
	private void setValuesToClass(Class toBeSeted){
		toBeSeted.setName(nameText.getText());
		toBeSeted.setCode(codeText.getText());
		toBeSeted.setClassroom(((NamedPair<Classroom>)classRooms.getSelectedItem()).data);
		toBeSeted.setCcSemester(ccText.getText());
		toBeSeted.setEcSemester(ecText.getText());
		toBeSeted.setCourse(courseText.getText());
		
		toBeSeted.setProfessors(professorList.getItens());
		toBeSeted.setSlots(slotList.getItens());	
	}
	
	private void saveChanges(){
		Class selected = ((NamedPair<Class>)classesComboBox.getSelectedItem()).data;
		setValuesToClass(selected);
		StateService.getInstance().getCurrentState().classes.update(selected);
	}
	
	private void setSelectedClass(Class selected){
		if(selected == null) return;
		
		nameText.setText(selected.getName());
		codeText.setText(selected.getCode());
		ccText.setText(selected.getCcSemester() == -1 ? "" : selected.getCcSemester() + "");
		ecText.setText(selected.getEcSemester() == -1 ? "" : selected.getEcSemester() + "");
		courseText.setText(selected.getCourse());
		
		slotList.clear();
		slotList.addElements(selected.getSlots());

		professorList.clear();
		professorList.addElements(selected.getProfessors());
		
		for(int i = 0; i < classRooms.getItemCount(); ++i){
			NamedPair<Classroom> item = (NamedPair<Classroom>)classRooms.getItemAt(i);
			if(item.data == selected.getClassroom()){
				classRooms.setSelectedIndex(i);
				break;
			}
		}
		
		generateDisponibilityTable();
	}

	private void generateDisponibilityTable() {
		PageWithTables page = new PageWithTables(50, 30);
		page.addStyle(".professorColision{background-color:red;min-width:50px;height:30px;}\n");
		page.addStyle(".roomColision{background-color:yellow;min-width:50px;height:30px;}\n");
		page.addStyle(".noconflict{background-color:green;min-width:50px;height:30px;}\n");
		page.addStyle(".allocated{background-color:blue;min-width:50px;height:30px;}\n");
		ScheduleTable table = new ScheduleTable("Disponibilidade de horários");
		Class current = new Class();
		setValuesToClass(current);
		
		for(int slot = 0; slot < 15; ++slot){
			for(int day = 0; day < 7; ++day){
				SlotRange s = new SlotRange(day, slot);
				if(current.getSlots().contains(s)) table.setSlot(slot, day, "<div class=\"allocated\"></div>");
				else{
					int report = warningService.hasConflit(s, current); 
					if(report == WarningService.NO_CONFLICT)
						table.setSlot(slot, day, "<div class=\"noconflict\"></div>");
					else if(report == WarningService.SAME_PROFESSOR)
						table.setSlot(slot, day, "<div class=\"professorColision\">Prof.</div>");
					else if(report == WarningService.SAME_ROOM)
						table.setSlot(slot, day, "<div class=\"roomColision\">Sala</div>");
				}
			}
		}
		page.addTable(table);
		try{
			PrintWriter pw = new PrintWriter("disp.html");
			pw.write(page.generateHtml());
			pw.close();
		}catch(Exception e){}
		
		ByteArrayInputStream input = new ByteArrayInputStream(page.generateHtml().getBytes());
		XMLResource document = XMLResource.load(input);
		htmlPanel.setDocument(document.getDocument());
	}
}
*/
