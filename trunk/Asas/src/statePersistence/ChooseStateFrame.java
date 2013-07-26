package statePersistence;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JList;
import java.awt.Insets;
import com.jgoodies.forms.factories.DefaultComponentFactory;

import edit.NamedPair;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class ChooseStateFrame extends JFrame {

	private JPanel contentPane;
	protected JTextArea description = new JTextArea();
	protected JCheckBox draftCheck;
	protected JScrollPane listScrollPane;
	protected JLabel descriptionLabel;
	protected JScrollPane descTextScrollPanel;
	protected JButton loadButton;
	protected JList<NamedPair<StateDescription>> stateList;
	private DefaultListModel<NamedPair<StateDescription>> stateListModel;
	private JLabel stateListLabel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChooseStateFrame frame = new ChooseStateFrame(){
						protected void onOkButton() {
						}
					};
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ChooseStateFrame() {
		setTitle("Carregar estado...");
		setResizable(true);
		setBounds(100, 100, 279, 537);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{263, 0};
		gbl_contentPane.rowHeights = new int[] {43, 155, 33, 45, 30, 83, 30};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		contentPane.setLayout(gbl_contentPane);
		
		stateListLabel = new JLabel("Estados salvos anteriormente");
		GridBagConstraints gbc_stateListLabel = new GridBagConstraints();
		gbc_stateListLabel.anchor = GridBagConstraints.WEST;
		gbc_stateListLabel.insets = new Insets(0, 0, 5, 0);
		gbc_stateListLabel.gridx = 0;
		gbc_stateListLabel.gridy = 0;
		contentPane.add(stateListLabel, gbc_stateListLabel);
		
		listScrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		contentPane.add(listScrollPane, gbc_scrollPane);
		
		stateListModel = new DefaultListModel();
		stateList = new JList(stateListModel);
		refreshStateList();
		stateList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int index = stateList.locationToIndex(e.getPoint());
					if (index >= 0){
						stateList.setSelectedIndex(index);
						onOkButton();
					}
				}
			}
		});

		listScrollPane.setViewportView(stateList);
		
		draftCheck = new JCheckBox("Draft?");
		GridBagConstraints gbc_draftCheckBox = new GridBagConstraints();
		gbc_draftCheckBox.anchor = GridBagConstraints.WEST;
		gbc_draftCheckBox.insets = new Insets(0, 0, 5, 0);
		gbc_draftCheckBox.gridx = 0;
		gbc_draftCheckBox.gridy = 2;
		contentPane.add(draftCheck, gbc_draftCheckBox);
		
		descriptionLabel = DefaultComponentFactory.getInstance().createLabel("Descrição");
		GridBagConstraints gbc_lblDescrio = new GridBagConstraints();
		gbc_lblDescrio.anchor = GridBagConstraints.WEST;
		gbc_lblDescrio.insets = new Insets(0, 0, 5, 0);
		gbc_lblDescrio.gridx = 0;
		gbc_lblDescrio.gridy = 4;
		contentPane.add(descriptionLabel, gbc_lblDescrio);
		
		descTextScrollPanel = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 5;
		contentPane.add(descTextScrollPanel, gbc_scrollPane_1);
		descTextScrollPanel.setViewportView(description);
		
		loadButton = new JButton("Carregar");
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onOkButton();
			}
		});
		GridBagConstraints gbc_btnCarregar = new GridBagConstraints();
		gbc_btnCarregar.gridx = 0;
		gbc_btnCarregar.gridy = 6;
		contentPane.add(loadButton, gbc_btnCarregar);
	}
	
	protected void refreshStateList() {
		stateListModel.clear();
		StateService service = StateService.getInstance();
		for(StateDescription s : service.allStates()) stateListModel.addElement(new NamedPair(s.getName(), s));
	}

	protected DefaultListModel<NamedPair<StateDescription>> getStateListModel(){
		return stateListModel;
	}
	
	protected void setEditable(boolean value){
		description.setEditable(value);
		draftCheck.setEnabled(value);
	}
	
	protected NamedPair<StateDescription> getSelected(){
		return stateList.getSelectedValue();
	}
	
	protected String getDescriptionText(){
		return description.getText();
	}
	
	protected boolean getDraftCheckValue(){
		return draftCheck.isSelected();
	}
	
	protected abstract void onOkButton();
	
	protected JLabel getStateListLabel() {
		return stateListLabel;
	}
}
