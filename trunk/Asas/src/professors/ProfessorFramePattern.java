package professors;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JButton;

import statePersistence.StateService;

import basic.Professor;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import data.Repository;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public abstract class ProfessorFramePattern extends JFrame {

	private static final long serialVersionUID = -1767573745824744588L;
	private JPanel contentPane;
	private JTextField txtName;
	private JTextField txtEmail;
	private JTextField txtCargo;
	private JLabel lblCargo;
	private JButton saveButton;
	private JLabel lblEmail;
	private JCheckBox profAwayChech;
	private JCheckBox tempProfChexBox;
	private JLabel lblName;
	private JLabel lblDpto;
	private JTextField txtDpto;
	protected Repository<Professor> professors;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProfessorFramePattern frame = new ProfessorFramePattern() {
						/**
						 * 
						 */
						private static final long serialVersionUID = -7312919083991397938L;

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
	
	public ProfessorFramePattern() {
		if(StateService.getInstance().hasValidState())
			this.professors = StateService.getInstance().getCurrentState().professors;
		configureElements();
	}

	private void configureElements() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 523, 278);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{32, 144, 289, 0};
		gbl_contentPane.rowHeights = new int[]{0, 19, 19, 19, 0, 23, 23, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		lblName = new JLabel("Nome");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.WEST;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 1;
		gbc_lblName.gridy = 1;
		contentPane.add(lblName, gbc_lblName);
		
		txtName = new JTextField();
		GridBagConstraints gbc_txtName = new GridBagConstraints();
		gbc_txtName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtName.insets = new Insets(0, 0, 5, 0);
		gbc_txtName.gridx = 2;
		gbc_txtName.gridy = 1;
		contentPane.add(txtName, gbc_txtName);
		txtName.setColumns(10);
		
		lblEmail = new JLabel("Email");
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.WEST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 1;
		gbc_lblEmail.gridy = 2;
		contentPane.add(lblEmail, gbc_lblEmail);
		
		txtEmail = new JTextField();
		GridBagConstraints gbc_txtEmail = new GridBagConstraints();
		gbc_txtEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEmail.insets = new Insets(0, 0, 5, 0);
		gbc_txtEmail.gridx = 2;
		gbc_txtEmail.gridy = 2;
		contentPane.add(txtEmail, gbc_txtEmail);
		txtEmail.setColumns(10);
		
		lblCargo = DefaultComponentFactory.getInstance().createLabel("Cargo");
		GridBagConstraints gbc_lblCargo = new GridBagConstraints();
		gbc_lblCargo.insets = new Insets(0, 0, 5, 5);
		gbc_lblCargo.anchor = GridBagConstraints.WEST;
		gbc_lblCargo.gridx = 1;
		gbc_lblCargo.gridy = 3;
		contentPane.add(lblCargo, gbc_lblCargo);
		
		txtCargo = new JTextField();
		GridBagConstraints gbc_txtCargo = new GridBagConstraints();
		gbc_txtCargo.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCargo.insets = new Insets(0, 0, 5, 0);
		gbc_txtCargo.gridx = 2;
		gbc_txtCargo.gridy = 3;
		contentPane.add(txtCargo, gbc_txtCargo);
		txtCargo.setColumns(10);
		
		lblDpto = new JLabel("Dpto");
		GridBagConstraints gbc_lblDpto = new GridBagConstraints();
		gbc_lblDpto.anchor = GridBagConstraints.WEST;
		gbc_lblDpto.insets = new Insets(0, 0, 5, 5);
		gbc_lblDpto.gridx = 1;
		gbc_lblDpto.gridy = 4;
		contentPane.add(lblDpto, gbc_lblDpto);
		
		txtDpto = new JTextField();
		GridBagConstraints gbc_dptoText = new GridBagConstraints();
		gbc_dptoText.insets = new Insets(0, 0, 5, 0);
		gbc_dptoText.fill = GridBagConstraints.HORIZONTAL;
		gbc_dptoText.gridx = 2;
		gbc_dptoText.gridy = 4;
		contentPane.add(txtDpto, gbc_dptoText);
		txtDpto.setColumns(10);
		
		tempProfChexBox = new JCheckBox("Prof. Temporário");
		GridBagConstraints gbc_tempProfChexBox = new GridBagConstraints();
		gbc_tempProfChexBox.anchor = GridBagConstraints.WEST;
		gbc_tempProfChexBox.insets = new Insets(0, 0, 5, 5);
		gbc_tempProfChexBox.gridx = 1;
		gbc_tempProfChexBox.gridy = 5;
		contentPane.add(tempProfChexBox, gbc_tempProfChexBox);
		
		profAwayChech = new JCheckBox("Afastado ");
		GridBagConstraints gbc_profAwayChech = new GridBagConstraints();
		gbc_profAwayChech.anchor = GridBagConstraints.WEST;
		gbc_profAwayChech.insets = new Insets(0, 0, 5, 5);
		gbc_profAwayChech.gridx = 1;
		gbc_profAwayChech.gridy = 6;
		contentPane.add(profAwayChech, gbc_profAwayChech);
		
		saveButton = new JButton("Salvar");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onOkButton();
			}
		});
		GridBagConstraints gbc_saveButton = new GridBagConstraints();
		gbc_saveButton.anchor = GridBagConstraints.WEST;
		gbc_saveButton.insets = new Insets(0, 0, 0, 5);
		gbc_saveButton.gridx = 1;
		gbc_saveButton.gridy = 8;
		contentPane.add(saveButton, gbc_saveButton);
	}
	
	protected String getNameText(){
		return txtName.getText();
	}
	
	protected void setNameText(String name){
		txtName.setText(name);
	}
	
	protected String getEmailText(){
		return txtEmail.getText();
	}
	
	protected void setEmailText(String email){
		txtEmail.setText(email);
	}
	
	protected String getCargoText(){
		return txtCargo.getText();
	}
	
	protected void setCargoText(String cargo){
		txtCargo.setText(cargo);
	}
	
	protected String getDptoText(){
		return txtDpto.getText();
	}
	
	protected void setDptoText(String dpto){
		txtDpto.setText(dpto);
	}
	
	protected boolean isAway(){
		return profAwayChech.isSelected();
	}
	
	protected void setAway(boolean isAway){
		profAwayChech.setSelected(isAway);
	}
	
	protected boolean isTemp(){
		return tempProfChexBox.isSelected();
	}
	
	protected void setTemp(boolean isTemp){
		tempProfChexBox.setSelected(isTemp);
	}
	
	protected abstract void onOkButton();

}
