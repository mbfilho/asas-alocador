package presentation.professors;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.JLabel;

import presentation.NamedPair;

import logic.dataUpdateSystem.DataUpdateCentral;

import data.persistentEntities.Professor;


public class EditProfessor extends ProfessorLayout {
	private DefaultComboBoxModel<NamedPair<Professor>> professorCBModel;
	private JComboBox<NamedPair<Professor>> professorsCBox;
	
	public EditProfessor() {
		setTitle("Editar Professores");
		setMinimumSize(new Dimension(523, 300));
		setBounds(100, 100, 523, 300);
		
		GridBagLayout gridBagLayout = (GridBagLayout) getContentPane().getLayout();
		gridBagLayout.rowHeights = new int[]{52, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0};
		
		JLabel chooseProfessorLabel = new JLabel("Escolha um professor:");
		GridBagConstraints gbc_chooseProfessorLabel = new GridBagConstraints();
		gbc_chooseProfessorLabel.insets = new Insets(0, 0, 5, 5);
		gbc_chooseProfessorLabel.anchor = GridBagConstraints.WEST;
		gbc_chooseProfessorLabel.gridx = 1;
		gbc_chooseProfessorLabel.gridy = 0;
		getContentPane().add(chooseProfessorLabel, gbc_chooseProfessorLabel);
		
		professorCBModel = new DefaultComboBoxModel<NamedPair<Professor>>();
		professorCBModel.addElement(new NamedPair<Professor>("Selecione um professor.", null));
		Collection<Professor> allProfs = professorService.all();
		for(Professor p : allProfs) professorCBModel.addElement(new NamedPair<Professor>(p.getName(), p));
		professorsCBox = new JComboBox<NamedPair<Professor>>(professorCBModel);
		professorsCBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fillWithSelectedProfessor(getSelectedProfessor());
			}
		});
		GridBagConstraints gbc_professorsCBox = new GridBagConstraints();
		gbc_professorsCBox.insets = new Insets(0, 0, 5, 0);
		gbc_professorsCBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_professorsCBox.gridx = 2;
		gbc_professorsCBox.gridy = 0;
		getContentPane().add(professorsCBox, gbc_professorsCBox);
		setVisible(true);
	}

	private static final long serialVersionUID = 3595460875461942003L;

	@SuppressWarnings("unchecked")
	private Professor getSelectedProfessor(){
		return ((NamedPair<Professor>) professorsCBox.getSelectedItem()).data;
	}
	
	private void fillWithSelectedProfessor(Professor selected){
		if(selected == null) return;
		setNameText(selected.getName());
		setCargoText(selected.getCargo());
		setDptoText(selected.getDpto());
		setEmailText(selected.getEmail());
		setAway(selected.isAway());
		setTemp(selected.isTemporary());
	}
	
	private void editWithFieldValues(Professor toEdit){
		toEdit.setAway(isAway());
		toEdit.setCargo(getCargoText());
		toEdit.setDpto(getDptoText());
		toEdit.setEmail(getEmailText());
		toEdit.setName(getNameText());
		toEdit.setTemporary(isTemp());
	}
	
	protected void onOkButton() {
		Professor toEdit = getSelectedProfessor();
		if(toEdit != null){
			editWithFieldValues(toEdit);
			professorService.update(toEdit);
		}
		setVisible(false);
		DataUpdateCentral.registerUpdate("Edição de professor");
	}

}
