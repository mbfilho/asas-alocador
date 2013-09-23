package presentation.professors;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JButton;

import utilities.DisposableOnEscFrame;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import logic.services.ProfessorService;

public abstract class ProfessorLayout extends DisposableOnEscFrame {

	private static final long serialVersionUID = -1767573745824744588L;
	private JPanel contentPane;
	private JTextField nameText;
	private JTextField emailText;
	private JTextField cargoText;
	private JLabel cargoLabel;
	private JButton saveButton;
	private JLabel emailLabel;
	private JCheckBox profAwayChech;
	private JCheckBox tempProfChexBox;
	private JLabel nameLabel;
	private JLabel dptoLabel;
	private JTextField dptoText;
	protected ProfessorService professorService;
	
	protected abstract void onOkButton();
	
	public ProfessorLayout() {
		professorService = ProfessorService.createServiceFromCurrentState();
		configureElements();
	}

	private void configureElements() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setMinimumSize(new Dimension(523, 278));
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
		
		nameLabel = new JLabel("Nome");
		GridBagConstraints gbc_nameLabel = new GridBagConstraints();
		gbc_nameLabel.anchor = GridBagConstraints.WEST;
		gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nameLabel.gridx = 1;
		gbc_nameLabel.gridy = 1;
		contentPane.add(nameLabel, gbc_nameLabel);
		
		nameText = new JTextField();
		GridBagConstraints gbc_nameText = new GridBagConstraints();
		gbc_nameText.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameText.insets = new Insets(0, 0, 5, 0);
		gbc_nameText.gridx = 2;
		gbc_nameText.gridy = 1;
		contentPane.add(nameText, gbc_nameText);
		nameText.setColumns(10);
		
		emailLabel = new JLabel("Email");
		GridBagConstraints gbc_emailLabel = new GridBagConstraints();
		gbc_emailLabel.anchor = GridBagConstraints.WEST;
		gbc_emailLabel.insets = new Insets(0, 0, 5, 5);
		gbc_emailLabel.gridx = 1;
		gbc_emailLabel.gridy = 2;
		contentPane.add(emailLabel, gbc_emailLabel);
		
		emailText = new JTextField();
		GridBagConstraints gbc_emailText = new GridBagConstraints();
		gbc_emailText.fill = GridBagConstraints.HORIZONTAL;
		gbc_emailText.insets = new Insets(0, 0, 5, 0);
		gbc_emailText.gridx = 2;
		gbc_emailText.gridy = 2;
		contentPane.add(emailText, gbc_emailText);
		emailText.setColumns(10);
		
		cargoLabel = DefaultComponentFactory.getInstance().createLabel("Cargo");
		GridBagConstraints gbc_cargoLabel = new GridBagConstraints();
		gbc_cargoLabel.insets = new Insets(0, 0, 5, 5);
		gbc_cargoLabel.anchor = GridBagConstraints.WEST;
		gbc_cargoLabel.gridx = 1;
		gbc_cargoLabel.gridy = 3;
		contentPane.add(cargoLabel, gbc_cargoLabel);
		
		cargoText = new JTextField();
		GridBagConstraints gbc_cargoText = new GridBagConstraints();
		gbc_cargoText.fill = GridBagConstraints.HORIZONTAL;
		gbc_cargoText.insets = new Insets(0, 0, 5, 0);
		gbc_cargoText.gridx = 2;
		gbc_cargoText.gridy = 3;
		contentPane.add(cargoText, gbc_cargoText);
		cargoText.setColumns(10);
		
		dptoLabel = new JLabel("Dpto");
		GridBagConstraints gbc_dptoLabel = new GridBagConstraints();
		gbc_dptoLabel.anchor = GridBagConstraints.WEST;
		gbc_dptoLabel.insets = new Insets(0, 0, 5, 5);
		gbc_dptoLabel.gridx = 1;
		gbc_dptoLabel.gridy = 4;
		contentPane.add(dptoLabel, gbc_dptoLabel);
		
		dptoText = new JTextField();
		GridBagConstraints gbc_dptoText = new GridBagConstraints();
		gbc_dptoText.insets = new Insets(0, 0, 5, 0);
		gbc_dptoText.fill = GridBagConstraints.HORIZONTAL;
		gbc_dptoText.gridx = 2;
		gbc_dptoText.gridy = 4;
		contentPane.add(dptoText, gbc_dptoText);
		dptoText.setColumns(10);
		
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
		return nameText.getText();
	}
	
	protected void setNameText(String name){
		nameText.setText(name);
	}
	
	protected String getEmailText(){
		return emailText.getText();
	}
	
	protected void setEmailText(String email){
		emailText.setText(email);
	}
	
	protected String getCargoText(){
		return cargoText.getText();
	}
	
	protected void setCargoText(String cargo){
		cargoText.setText(cargo);
	}
	
	protected String getDptoText(){
		return dptoText.getText();
	}
	
	protected void setDptoText(String dpto){
		dptoText.setText(dpto);
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
}
