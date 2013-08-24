package classEditor.swapper;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import java.awt.Insets;
import javax.swing.JList;
import javax.swing.JCheckBox;

import classEditor.NamedPair;
import classEditor.OrderedJListModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import basic.Class;
import basic.Professor;
import basic.SlotRange;
import javax.swing.border.TitledBorder;

public class SwapperLayout extends JDialog {
	private static final long serialVersionUID = -1469164478795471032L;
	
	private final JPanel contentPanel = new JPanel();
	private JCheckBox swapTimeCheck;
	private JCheckBox swapRoomCheck;
	private JButton swapProfessorsButton;
	private JButton swapSlotsButton;
	private JComboBox<NamedPair<Class>> otherClassCBox;
	private JList<NamedPair<Professor>> selectedClassProfessorList;
	private JList<NamedPair<SlotRange>> selectedClassSlotsList;
	private JList<NamedPair<Professor>> otherClassProfessorList;
	private JList<NamedPair<SlotRange>> otherClassSlotsList;
	private JButton okButton;

	public SwapperLayout(JFrame parent, Class selectedClass) {
		super(parent);
		setTitle("Swaps");
		setBounds(100, 100, 492, 495);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{228, 0, 210, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 40, 39, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel currentSelectedClassLabel = new JLabel(selectedClass.completeName());
			GridBagConstraints gbc_currentSelectedClassLabel = new GridBagConstraints();
			gbc_currentSelectedClassLabel.insets = new Insets(0, 0, 5, 5);
			gbc_currentSelectedClassLabel.gridx = 0;
			gbc_currentSelectedClassLabel.gridy = 0;
			contentPanel.add(currentSelectedClassLabel, gbc_currentSelectedClassLabel);
		}
		{
			otherClassCBox = new JComboBox<NamedPair<Class>>(new DefaultComboBoxModel<NamedPair<Class>>());
			otherClassCBox.setPrototypeDisplayValue(new NamedPair<Class>("Selecione outra turma", null));
			GridBagConstraints gbc_otherClassCBox = new GridBagConstraints();
			gbc_otherClassCBox.insets = new Insets(0, 0, 5, 0);
			gbc_otherClassCBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_otherClassCBox.gridx = 2;
			gbc_otherClassCBox.gridy = 0;
			contentPanel.add(otherClassCBox, gbc_otherClassCBox);
		}
		{
			selectedClassProfessorList = new JList<NamedPair<Professor>>(new OrderedJListModel<NamedPair<Professor>>());
			selectedClassProfessorList.setBorder(new TitledBorder(null, "Professores", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			GridBagConstraints gbc_selectedClassProfessorList = new GridBagConstraints();
			gbc_selectedClassProfessorList.insets = new Insets(0, 0, 5, 5);
			gbc_selectedClassProfessorList.fill = GridBagConstraints.BOTH;
			gbc_selectedClassProfessorList.gridx = 0;
			gbc_selectedClassProfessorList.gridy = 1;
			contentPanel.add(selectedClassProfessorList, gbc_selectedClassProfessorList);
		}
		{
			swapProfessorsButton = new JButton("<>");
			GridBagConstraints gbc_swapProfessorsButton = new GridBagConstraints();
			gbc_swapProfessorsButton.insets = new Insets(0, 0, 5, 5);
			gbc_swapProfessorsButton.gridx = 1;
			gbc_swapProfessorsButton.gridy = 1;
			contentPanel.add(swapProfessorsButton, gbc_swapProfessorsButton);
			swapProfessorsButton.setToolTipText("Trocar!");
		}
		{
			otherClassProfessorList = new JList<NamedPair<Professor>>(new OrderedJListModel<NamedPair<Professor>>());
			otherClassProfessorList.setBorder(new TitledBorder(null, "Professores", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			GridBagConstraints gbc_otherClassProfessorList = new GridBagConstraints();
			gbc_otherClassProfessorList.insets = new Insets(0, 0, 5, 0);
			gbc_otherClassProfessorList.fill = GridBagConstraints.BOTH;
			gbc_otherClassProfessorList.gridx = 2;
			gbc_otherClassProfessorList.gridy = 1;
			contentPanel.add(otherClassProfessorList, gbc_otherClassProfessorList);
		}
		{
			selectedClassSlotsList = new JList<NamedPair<SlotRange>>(new OrderedJListModel<NamedPair<SlotRange>>());
			selectedClassSlotsList.setBorder(new TitledBorder(null, "Hor\u00E1rios", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			GridBagConstraints gbc_selectedClassSlotsList = new GridBagConstraints();
			gbc_selectedClassSlotsList.insets = new Insets(0, 0, 5, 5);
			gbc_selectedClassSlotsList.fill = GridBagConstraints.BOTH;
			gbc_selectedClassSlotsList.gridx = 0;
			gbc_selectedClassSlotsList.gridy = 2;
			contentPanel.add(selectedClassSlotsList, gbc_selectedClassSlotsList);
		}
		{
			swapSlotsButton = new JButton("<>");
			GridBagConstraints gbc_swapSlotsButton = new GridBagConstraints();
			gbc_swapSlotsButton.insets = new Insets(0, 0, 5, 5);
			gbc_swapSlotsButton.gridx = 1;
			gbc_swapSlotsButton.gridy = 2;
			contentPanel.add(swapSlotsButton, gbc_swapSlotsButton);
			swapSlotsButton.setToolTipText("Trocar!");
		}
		{
			otherClassSlotsList = new JList<NamedPair<SlotRange>>(new OrderedJListModel<NamedPair<SlotRange>>());
			otherClassSlotsList.setBorder(new TitledBorder(null, "Hor\u00E1rios", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			GridBagConstraints gbc_otherClassSlotsList = new GridBagConstraints();
			gbc_otherClassSlotsList.insets = new Insets(0, 0, 5, 0);
			gbc_otherClassSlotsList.fill = GridBagConstraints.BOTH;
			gbc_otherClassSlotsList.gridx = 2;
			gbc_otherClassSlotsList.gridy = 2;
			contentPanel.add(otherClassSlotsList, gbc_otherClassSlotsList);
		}
		{
			swapRoomCheck = new JCheckBox("Trocar sala");
			GridBagConstraints gbc_swapRoomCheck = new GridBagConstraints();
			gbc_swapRoomCheck.anchor = GridBagConstraints.WEST;
			gbc_swapRoomCheck.insets = new Insets(0, 0, 5, 5);
			gbc_swapRoomCheck.gridx = 0;
			gbc_swapRoomCheck.gridy = 3;
			contentPanel.add(swapRoomCheck, gbc_swapRoomCheck);
			swapRoomCheck.setSelected(true);
		}
		{
			swapTimeCheck = new JCheckBox("Trocar dia e horário");
			GridBagConstraints gbc_swapTimeCheck = new GridBagConstraints();
			gbc_swapTimeCheck.anchor = GridBagConstraints.WEST;
			gbc_swapTimeCheck.insets = new Insets(0, 0, 0, 5);
			gbc_swapTimeCheck.gridx = 0;
			gbc_swapTimeCheck.gridy = 4;
			contentPanel.add(swapTimeCheck, gbc_swapTimeCheck);
			swapTimeCheck.setSelected(true);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			okButton = new JButton("OK");
			okButton.setActionCommand("OK");
			buttonPane.add(okButton);
			getRootPane().setDefaultButton(okButton);
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	protected JList<NamedPair<Professor>> getSelectedClassProfessorList() {
		return selectedClassProfessorList;
	}
	protected JCheckBox getSwapTimeCheck() {
		return swapTimeCheck;
	}
	protected JCheckBox getSwapRoomCheck() {
		return swapRoomCheck;
	}
	protected JButton getSwapProfessorsButton() {
		return swapProfessorsButton;
	}
	protected JButton getSwapSlotsButton() {
		return swapSlotsButton;
	}
	protected JComboBox<NamedPair<Class>> getOtherClassCBox() {
		return otherClassCBox;
	}
	protected JList<NamedPair<SlotRange>> getSelectedClassSlotsList() {
		return selectedClassSlotsList;
	}
	protected JList<NamedPair<Professor>> getOtherClassProfessorList() {
		return otherClassProfessorList;
	}
	protected JList<NamedPair<SlotRange>> getOtherClassSlotsList() {
		return otherClassSlotsList;
	}
	protected JButton getOkButton() {
		return okButton;
	}
}
