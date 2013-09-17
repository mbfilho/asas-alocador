package presentation.classrooms;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JButton;

import logic.services.ClassroomService;

import data.persistentEntities.Classroom;

import utilities.DisposableOnEscFrame;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

public abstract class ClassroomLayout extends DisposableOnEscFrame {

	private static final long serialVersionUID = 9039274792283711446L;
	
	private JPanel contentPane;
	private JTextField nameText;
	private JTextField capacityText;
	protected ClassroomService classroomService;
	private JCheckBox externalCheck;

	public ClassroomLayout() {
		classroomService = ClassroomService.createServiceFromCurrentState();
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 350, 176);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{19, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{17, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel nameLabel = new JLabel("Nome:");
		GridBagConstraints gbc_nameLabel = new GridBagConstraints();
		gbc_nameLabel.anchor = GridBagConstraints.EAST;
		gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nameLabel.gridx = 1;
		gbc_nameLabel.gridy = 1;
		contentPane.add(nameLabel, gbc_nameLabel);
		
		nameText = new JTextField();
		GridBagConstraints gbc_nameText = new GridBagConstraints();
		gbc_nameText.gridwidth = 2;
		gbc_nameText.insets = new Insets(0, 0, 5, 5);
		gbc_nameText.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameText.gridx = 2;
		gbc_nameText.gridy = 1;
		contentPane.add(nameText, gbc_nameText);
		nameText.setColumns(10);
		
		JLabel capacityLabel = new JLabel("Capacidade:");
		GridBagConstraints gbc_capacityLabel = new GridBagConstraints();
		gbc_capacityLabel.anchor = GridBagConstraints.EAST;
		gbc_capacityLabel.insets = new Insets(0, 0, 5, 5);
		gbc_capacityLabel.gridx = 1;
		gbc_capacityLabel.gridy = 2;
		contentPane.add(capacityLabel, gbc_capacityLabel);
		
		capacityText = new JTextField();
		GridBagConstraints gbc_capacityText = new GridBagConstraints();
		gbc_capacityText.insets = new Insets(0, 0, 5, 5);
		gbc_capacityText.fill = GridBagConstraints.HORIZONTAL;
		gbc_capacityText.gridx = 2;
		gbc_capacityText.gridy = 2;
		contentPane.add(capacityText, gbc_capacityText);
		capacityText.setColumns(10);
		
		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onOkButton();
				dispose();
			}
		});
		
		externalCheck = new JCheckBox("Sala externa");
		externalCheck.setHorizontalTextPosition(SwingConstants.LEADING);
		GridBagConstraints gbc_externalCheck = new GridBagConstraints();
		gbc_externalCheck.insets = new Insets(0, 0, 5, 5);
		gbc_externalCheck.gridx = 2;
		gbc_externalCheck.gridy = 3;
		contentPane.add(externalCheck, gbc_externalCheck);
		GridBagConstraints gbc_okButton = new GridBagConstraints();
		gbc_okButton.insets = new Insets(0, 0, 0, 5);
		gbc_okButton.gridx = 1;
		gbc_okButton.gridy = 4;
		contentPane.add(okButton, gbc_okButton);
		setVisible(true);
	}
	
	public abstract void onOkButton();
	
	protected Classroom getClassroomFromFields(){
		Classroom room = new Classroom(nameText.getText(), capacityText.getText());
		room.setExternal(externalCheck.isSelected());
		
		return room;
	}
	
	protected void setFieldsFromClassroom(Classroom room){
		nameText.setText(room.getName());
		if(room.getCapacity() == -1) capacityText.setText("");
		else capacityText.setText(room.getCapacity() + "");
		externalCheck.setSelected(room.isExternal());
	}

}
