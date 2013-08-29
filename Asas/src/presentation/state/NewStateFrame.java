package presentation.state;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import data.persistentEntities.State;
import exceptions.StateIOException;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.util.Date;

import javax.swing.JTextField;

import logic.services.StateService;



public class NewStateFrame extends ChooseStateLayout{
	private static final long serialVersionUID = 8910633735786246632L;
	private JTextField nameText;
	
	public NewStateFrame() {
		super();
		setEditable(true);
		setTitle("Criar novo estado");
		setBounds(100, 100, 264, 497);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		loadButton.setText("Criar");
		setResizable(false);
		getStateListLabel().setText("Escolha um estado base:");
		
		GridBagLayout gridBagLayout = (GridBagLayout) getContentPane().getLayout();
		gridBagLayout.rowHeights = new int[]{0, 170, 26, 140, 0, 121, 0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0};
		gridBagLayout.columnWeights = new double[]{1.0};
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 3;
		getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {76, 133};
		gbl_panel.rowHeights = new int[] {26};
		gbl_panel.columnWeights = new double[]{0.0, 1.0};
		gbl_panel.rowWeights = new double[]{0.0};
		panel.setLayout(gbl_panel);
		
		JLabel lblNome = new JLabel("Nome");
		GridBagConstraints gbc_lblNome = new GridBagConstraints();
		gbc_lblNome.fill = GridBagConstraints.BOTH;
		gbc_lblNome.insets = new Insets(0, 0, 0, 5);
		gbc_lblNome.gridx = 0;
		gbc_lblNome.gridy = 0;
		panel.add(lblNome, gbc_lblNome);
		
		String suggested = new Date(System.currentTimeMillis()).toString().replace(" ", "_").replace(":", "-");
		nameText = new JTextField(suggested);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.BOTH;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		panel.add(nameText, gbc_textField);
		nameText.setColumns(10);
		setVisible(true);
	}

	protected String getNameText(){
		return nameText.getText();
	}
	
	protected void onOkButton() {
		if(getSelected() == null) return;
		StateService stateService = StateService.getInstance();
		if(stateService.existName(getNameText())){
			JOptionPane.showMessageDialog(this, "Já existe um estado salvo com o nome \"" + getName() + "\".\nPor favor escolha outro", "Nome já existente", JOptionPane.ERROR_MESSAGE);
		}else{
			State base;
			try {
				base = stateService.loadState(getSelected().data);
				base.setStateDescription(getNameText(), getDescriptionText(), getDraftCheckValue());
				stateService.saveNewState(base);
				setVisible(false);
			} catch (StateIOException e1) {
				JOptionPane.showMessageDialog(this, e1.getMessage());
				e1.printStackTrace();
			}
		}
	}
	
}
