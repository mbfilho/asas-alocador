package excelPreferences.gui;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JList;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class RoomListEditorInterfaceLayout extends JDialog{
	private static final long serialVersionUID = -8612878915736343843L;
	
	private JComboBox<String> allRoomsCBox;
	private JButton downButton;
	private JButton addButton;
	private JList<String> roomsList;
	private JButton okButton;
	private JButton removeButton;
	private JButton upButton;
	/**
	 * Create the frame.
	 */
	public RoomListEditorInterfaceLayout(JFrame parent) {
		super(parent, true);
		setResizable(false);
		setTitle("Editar Salas Correspondentes.");
		setBounds(100, 100, 342, 300);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{64, 59, 58, 0, 74, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		roomsList = new JList<String>(new DefaultListModel<String>());
		GridBagConstraints gbc_roomsList = new GridBagConstraints();
		gbc_roomsList.gridheight = 3;
		gbc_roomsList.insets = new Insets(0, 0, 5, 5);
		gbc_roomsList.fill = GridBagConstraints.BOTH;
		gbc_roomsList.gridx = 0;
		gbc_roomsList.gridy = 0;
		getContentPane().add(roomsList, gbc_roomsList);
		
		upButton = new JButton("/\\");
		GridBagConstraints gbc_upButton = new GridBagConstraints();
		gbc_upButton.anchor = GridBagConstraints.SOUTH;
		gbc_upButton.insets = new Insets(0, 0, 5, 0);
		gbc_upButton.gridx = 1;
		gbc_upButton.gridy = 0;
		getContentPane().add(upButton, gbc_upButton);
		
		downButton = new JButton("\\/");
		GridBagConstraints gbc_downButton = new GridBagConstraints();
		gbc_downButton.anchor = GridBagConstraints.NORTH;
		gbc_downButton.insets = new Insets(0, 0, 5, 0);
		gbc_downButton.gridx = 1;
		gbc_downButton.gridy = 1;
		getContentPane().add(downButton, gbc_downButton);
		
		removeButton = new JButton("Remover");
		GridBagConstraints gbc_removeButton = new GridBagConstraints();
		gbc_removeButton.insets = new Insets(0, 0, 5, 0);
		gbc_removeButton.gridx = 1;
		gbc_removeButton.gridy = 2;
		getContentPane().add(removeButton, gbc_removeButton);
		
		allRoomsCBox = new JComboBox<String>();
		GridBagConstraints gbc_allRoomsCBox = new GridBagConstraints();
		gbc_allRoomsCBox.insets = new Insets(0, 0, 5, 5);
		gbc_allRoomsCBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_allRoomsCBox.gridx = 0;
		gbc_allRoomsCBox.gridy = 3;
		getContentPane().add(allRoomsCBox, gbc_allRoomsCBox);
		
		addButton = new JButton("Adicionar");
		GridBagConstraints gbc_addButton = new GridBagConstraints();
		gbc_addButton.insets = new Insets(0, 0, 5, 0);
		gbc_addButton.gridx = 1;
		gbc_addButton.gridy = 3;
		getContentPane().add(addButton, gbc_addButton);
		
		okButton = new JButton("Ok");
		GridBagConstraints gbc_okButton = new GridBagConstraints();
		gbc_okButton.gridwidth = 2;
		gbc_okButton.gridx = 0;
		gbc_okButton.gridy = 4;
		getContentPane().add(okButton, gbc_okButton);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	protected JComboBox<String> getAllRoomsCBox() {
		return allRoomsCBox;
	}
	protected JButton getDownButton() {
		return downButton;
	}
	protected JButton getAddButton() {
		return addButton;
	}
	protected JList<String> getRoomsList() {
		return roomsList;
	}
	protected JButton getOkButton() {
		return okButton;
	}
	protected JButton getRemoveButton() {
		return removeButton;
	}
	protected JButton getUpButton() {
		return upButton;
	}
}
