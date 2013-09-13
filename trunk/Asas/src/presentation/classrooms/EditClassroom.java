package presentation.classrooms;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JComboBox;

import presentation.NamedPair;
import utilities.GuiUtil;

import data.persistentEntities.Classroom;



import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditClassroom extends ClassroomLayout {
	private static final long serialVersionUID = -1080535206754133507L;
	private JComboBox<NamedPair<Classroom>> classroomsCBox;
	private DefaultComboBoxModel<NamedPair<Classroom>> classroomsCBModel;
	
	public EditClassroom(){
		GridBagLayout gridBagLayout = (GridBagLayout) getContentPane().getLayout();
		gridBagLayout.columnWidths = new int[]{-9, 122, 123};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0};
		setTitle("Editar Sala");
		
		classroomsCBModel = new DefaultComboBoxModel<NamedPair<Classroom>>();
		classroomsCBModel.addElement(new NamedPair<Classroom>("Selecione uma sala.", null));
		for(Classroom room : classroomService.all())
			classroomsCBModel.addElement(new NamedPair<Classroom>(room.getName(), room));
		classroomsCBox = new JComboBox<NamedPair<Classroom>>(classroomsCBModel);
		classroomsCBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Classroom selected = GuiUtil.getSelectedItem(classroomsCBox);
				if(selected != null) setFieldsFromClassroom(selected);
			}
		});
		
		JLabel chooseAClassroomLabel = new JLabel("Selecione uma sala");
		GridBagConstraints gbc_chooseAClassroomLabel = new GridBagConstraints();
		gbc_chooseAClassroomLabel.anchor = GridBagConstraints.EAST;
		gbc_chooseAClassroomLabel.insets = new Insets(0, 0, 5, 5);
		gbc_chooseAClassroomLabel.gridx = 1;
		gbc_chooseAClassroomLabel.gridy = 0;
		getContentPane().add(chooseAClassroomLabel, gbc_chooseAClassroomLabel);
		GridBagConstraints gbc_classroomsCBox = new GridBagConstraints();
		gbc_classroomsCBox.gridwidth = 2;
		gbc_classroomsCBox.insets = new Insets(0, 0, 5, 5);
		gbc_classroomsCBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_classroomsCBox.gridx = 2;
		gbc_classroomsCBox.gridy = 0;
		getContentPane().add(classroomsCBox, gbc_classroomsCBox);
	}
	
	public void onOkButton() {
		Classroom toEdit = GuiUtil.getSelectedItem(classroomsCBox);
		if(toEdit != null){
			Classroom values = getClassroomFromFields();
			toEdit.setCapacity(values.getCapacity());
			toEdit.setName(values.getName());
			toEdit.setExternal(values.isExternal());
			classroomService.update(toEdit);
		}
		dispose();
	}
}
