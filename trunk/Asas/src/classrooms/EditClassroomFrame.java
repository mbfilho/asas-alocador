package classrooms;

import statePersistence.StateService;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JComboBox;

import classEditor.NamedPair;

import basic.Classroom;


import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditClassroomFrame extends ClassroomFramePattern {
	private static final long serialVersionUID = -1080535206754133507L;
	private JComboBox classrooms;
	private DefaultComboBoxModel<NamedPair<Classroom>> classroomsCBModel;
	
	public EditClassroomFrame(){
		GridBagLayout gridBagLayout = (GridBagLayout) getContentPane().getLayout();
		gridBagLayout.columnWidths = new int[]{-9, 122, 123};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0};
		setTitle("Editar Sala");
		
		classroomsCBModel = new DefaultComboBoxModel<NamedPair<Classroom>>();
		classroomsCBModel.addElement(new NamedPair<Classroom>("Selecione uma sala.", null));
		for(Classroom room : classroomService.all())
			classroomsCBModel.addElement(new NamedPair<Classroom>(room.getName(), room));
		classrooms = new JComboBox(classroomsCBModel);
		classrooms.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Classroom selected = getSelectedClassroom();
				if(selected != null) setFieldsFromClassroom(selected);
			}
		});
		
		JLabel lblSelecioneUmaSala = new JLabel("Selecione uma sala");
		GridBagConstraints gbc_lblSelecioneUmaSala = new GridBagConstraints();
		gbc_lblSelecioneUmaSala.anchor = GridBagConstraints.EAST;
		gbc_lblSelecioneUmaSala.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelecioneUmaSala.gridx = 1;
		gbc_lblSelecioneUmaSala.gridy = 0;
		getContentPane().add(lblSelecioneUmaSala, gbc_lblSelecioneUmaSala);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 2;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 0;
		getContentPane().add(classrooms, gbc_comboBox);
	}
	
	private Classroom getSelectedClassroom(){
		return ((NamedPair<Classroom>) classrooms.getSelectedItem()).data;
	}
	
	public void onOkButton() {
		Classroom toEdit = getSelectedClassroom();
		if(toEdit != null){
			Classroom values = getClassroomFromFields();
			toEdit.setCapacity(values.getCapacity());
			toEdit.setName(values.getName());
			classroomService.update(toEdit);
		}
		dispose();
	}
}
