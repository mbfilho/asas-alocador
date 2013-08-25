package classEditor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JSpinner;
import javax.swing.JComboBox;

import java.util.Collection;
import java.util.Vector;


import javax.swing.JButton;

import utilities.Constants;
import utilities.DisposableOnEscFrame;

import javax.swing.DefaultComboBoxModel;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import presentation.NamedPair;

import logic.services.ConflictService;
import logic.services.StateService;
import logic.services.WarningGeneratorService;

import data.persistentEntities.Class;
import data.persistentEntities.Classroom;
import data.persistentEntities.SlotRange;

public abstract class SlotChooser extends DisposableOnEscFrame {

	private static final long serialVersionUID = -2203432919581579448L;
	private JPanel contentPane;
	private DefaultComboBoxModel<NamedPair<Classroom>> classroomCBModel;
	private Class selectedClass;
	private JSpinner endHour, iniHour;
	private JComboBox<NamedPair<Classroom>> classrooms;
	private JComboBox<String> days;
	private SlotRange slotToEdit;
	private ConflictService conflictService;
	
	private void configureElements(){
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 68, 76, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 32, 69, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblSala = new JLabel("Sala");
		GridBagConstraints gbc_lblSala = new GridBagConstraints();
		gbc_lblSala.anchor = GridBagConstraints.WEST;
		gbc_lblSala.insets = new Insets(0, 0, 5, 5);
		gbc_lblSala.gridx = 1;
		gbc_lblSala.gridy = 0;
		contentPane.add(lblSala, gbc_lblSala);

		classrooms = new JComboBox<NamedPair<Classroom>>();
		classroomCBModel = new DefaultComboBoxModel<NamedPair<Classroom>>(); 
		classrooms.setModel(classroomCBModel);
		GridBagConstraints gbc_classrooms = new GridBagConstraints();
		gbc_classrooms.insets = new Insets(0, 0, 5, 0);
		gbc_classrooms.gridwidth = 6;
		gbc_classrooms.fill = GridBagConstraints.HORIZONTAL;
		gbc_classrooms.gridx = 2;
		gbc_classrooms.gridy = 0;
		contentPane.add(classrooms, gbc_classrooms);
		
		JLabel lblDia = new JLabel("Dia");
		GridBagConstraints gbc_lblDia = new GridBagConstraints();
		gbc_lblDia.anchor = GridBagConstraints.WEST;
		gbc_lblDia.insets = new Insets(0, 0, 5, 5);
		gbc_lblDia.gridx = 1;
		gbc_lblDia.gridy = 1;
		contentPane.add(lblDia, gbc_lblDia);
		
		days = new JComboBox<String>();
		days.setModel(new DefaultComboBoxModel<String>(Constants.days));
		days.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateClassroomsDisponibility();
			}
		});
		GridBagConstraints gbc_days = new GridBagConstraints();
		gbc_days.gridwidth = 5;
		gbc_days.insets = new Insets(0, 0, 5, 5);
		gbc_days.fill = GridBagConstraints.HORIZONTAL;
		gbc_days.gridx = 2;
		gbc_days.gridy = 1;
		contentPane.add(days, gbc_days);
		
		JLabel lblDas = new JLabel("Das");
		GridBagConstraints gbc_lblDas = new GridBagConstraints();
		gbc_lblDas.anchor = GridBagConstraints.WEST;
		gbc_lblDas.insets = new Insets(0, 0, 5, 5);
		gbc_lblDas.gridx = 1;
		gbc_lblDas.gridy = 2;
		contentPane.add(lblDas, gbc_lblDas);
		
		iniHour = new JSpinner();
		iniHour.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				updateClassroomsDisponibility();
			}
		});
		iniHour.setModel(new SpinnerNumberModel(7, 7, 21, 1));
		GridBagConstraints gbc_iniHour = new GridBagConstraints();
		gbc_iniHour.insets = new Insets(0, 0, 5, 5);
		gbc_iniHour.gridx = 2;
		gbc_iniHour.gridy = 2;
		contentPane.add(iniHour, gbc_iniHour);
		
		JLabel lblH = new JLabel("h às");
		GridBagConstraints gbc_lblH = new GridBagConstraints();
		gbc_lblH.insets = new Insets(0, 0, 5, 5);
		gbc_lblH.gridx = 3;
		gbc_lblH.gridy = 2;
		contentPane.add(lblH, gbc_lblH);
		
		endHour = new JSpinner();
		endHour.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				updateClassroomsDisponibility();
			}
		});
		endHour.setModel(new SpinnerNumberModel(8, 8, 22, 1));
		GridBagConstraints gbc_endHour = new GridBagConstraints();
		gbc_endHour.insets = new Insets(0, 0, 5, 5);
		gbc_endHour.gridx = 4;
		gbc_endHour.gridy = 2;
		contentPane.add(endHour, gbc_endHour);
		
		JLabel lblH_1 = new JLabel("h");
		GridBagConstraints gbc_lblH_1 = new GridBagConstraints();
		gbc_lblH_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblH_1.gridx = 5;
		gbc_lblH_1.gridy = 2;
		contentPane.add(lblH_1, gbc_lblH_1);
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SlotRange selected = getSelectedSlot();
				
				if(selected.isValid()){
					if(isEditingExistingSlot()){
						slotToEdit.setClassroom(selected.getClassroom());
						slotToEdit.setDay(selected.getDay());
						slotToEdit.setStartSlot(selected.getStartSlot());
						slotToEdit.setEndSlot(selected.getEndSlot());
						onChooseSlot(slotToEdit);
					}else onChooseSlot(selected);
				}
				dispose();
			}
		});
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.anchor = GridBagConstraints.SOUTH;
		gbc_btnOk.insets = new Insets(0, 0, 0, 5);
		gbc_btnOk.gridx = 1;
		gbc_btnOk.gridy = 3;
		contentPane.add(btnOk, gbc_btnOk);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SlotChooser.this.dispose();
			}
		});
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.anchor = GridBagConstraints.SOUTH;
		gbc_btnCancelar.gridwidth = 3;
		gbc_btnCancelar.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancelar.gridx = 3;
		gbc_btnCancelar.gridy = 3;
		contentPane.add(btnCancelar, gbc_btnCancelar);
	}
	
	public SlotChooser (WarningGeneratorService warning, Class selectedClass){
		this(warning, selectedClass, null);
	}
	
	public SlotChooser(WarningGeneratorService warning, Class selectedClass, SlotRange toEdit) {
		this.slotToEdit = toEdit;
		conflictService = new ConflictService();
		this.selectedClass = selectedClass;
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 398, 208);
		configureElements();
		initClassroomBox();
		
		if(isEditingExistingSlot()) fillWithSelectedSlot();
		else{
			Vector<Classroom> rooms = selectedClass.getAllRooms();
			if(!rooms.isEmpty()) selectClassroom(rooms.firstElement());
		}
		
		setVisible(true);
	}
	
	private void selectClassroom(Classroom room){
		for(int i = 0; i < classroomCBModel.getSize(); ++i){
			NamedPair<Classroom> pair = classroomCBModel.getElementAt(i);
			if(pair.data == room){
				classrooms.setSelectedItem(pair);
				break;
			}
		}
	}
	
	private boolean isEditingExistingSlot(){
		return slotToEdit != null;
	}
	
	private void fillWithSelectedSlot(){
		selectClassroom(slotToEdit.getClassroom());
		iniHour.setValue(slotToEdit.getStartSlot() + 7);
		endHour.setValue(slotToEdit.getEndSlot() + 8);
		days.setSelectedIndex(slotToEdit.getDay());
	}
	
	private SlotRange getSelectedSlot(){
		int ini = ((Integer) iniHour.getValue()) - 7, end = ((Integer) endHour.getValue()) - 8;
		@SuppressWarnings("unchecked")
		NamedPair<Classroom> obj = (NamedPair<Classroom>) classrooms.getSelectedItem();
		SlotRange range = new SlotRange(days.getSelectedIndex(), ini, end, obj == null ? null : (Classroom) obj.data);
		return range;
	}
	
	private void updateClassroomsDisponibility(){
		for(int i = 0; i < classroomCBModel.getSize(); ++i){
			Classroom room = classroomCBModel.getElementAt(i).data;
			if(room == null) continue;
			
			String fontColor = "green";
			SlotRange slot = getSelectedSlot();
			if(!conflictService.isClassroomFreeForThisClass(selectedClass, room, slot))
				fontColor = "red";
			
			classroomCBModel.getElementAt(i).name = "<html><p style='color:" + fontColor + "'>"+room.getName()+"</p></html>";
		}
		classrooms.repaint();
	}

	private void initClassroomBox() {
		Collection<Classroom> allRooms = StateService.getInstance().getCurrentState().classrooms.all();
		classroomCBModel.addElement(new NamedPair<Classroom>("Escolher depois...", null));
		for(Classroom room : allRooms) classroomCBModel.addElement(new NamedPair<Classroom>(room.getName(), room));
		updateClassroomsDisponibility();
	}

	public abstract void onChooseSlot(SlotRange chosen);
}
