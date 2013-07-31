package professors;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.Thread.State;
import java.util.Collection;

import javax.swing.JLabel;

import statePersistence.StateService;

import edit.NamedPair;

import basic.Professor;

public class EditProfessorFrame extends ProfessorFramePattern {
	private DefaultComboBoxModel<NamedPair<Professor>> professorCBModel;
	JComboBox<NamedPair<Professor>> professors;
	private StateService stateService;
	
	public EditProfessorFrame() {
		stateService = StateService.getInstance();
		GridBagLayout gridBagLayout = (GridBagLayout) getContentPane().getLayout();
		gridBagLayout.rowHeights = new int[]{52, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0};
		
		JLabel lblEscolhaUmProfessor = new JLabel("Escolha um professor:");
		GridBagConstraints gbc_lblEscolhaUmProfessor = new GridBagConstraints();
		gbc_lblEscolhaUmProfessor.insets = new Insets(0, 0, 5, 5);
		gbc_lblEscolhaUmProfessor.anchor = GridBagConstraints.EAST;
		gbc_lblEscolhaUmProfessor.gridx = 1;
		gbc_lblEscolhaUmProfessor.gridy = 0;
		getContentPane().add(lblEscolhaUmProfessor, gbc_lblEscolhaUmProfessor);
		
		professorCBModel = new DefaultComboBoxModel<NamedPair<Professor>>();
		professorCBModel.addElement(new NamedPair<Professor>("Selecione um professor.", null));
		if(StateService.getInstance().hasValidState()){
			Collection<Professor> allProfs = StateService.getInstance().getCurrentState().professors.all();
			for(Professor p : allProfs) professorCBModel.addElement(new NamedPair<Professor>(p.getName(), p));
		}
		professors = new JComboBox<NamedPair<Professor>>(professorCBModel);
		professors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fillWithSelectedProfessor(getSelectedProfessor());
			}
		});
		GridBagConstraints gbc_professors = new GridBagConstraints();
		gbc_professors.insets = new Insets(0, 0, 5, 0);
		gbc_professors.fill = GridBagConstraints.HORIZONTAL;
		gbc_professors.gridx = 2;
		gbc_professors.gridy = 0;
		getContentPane().add(professors, gbc_professors);
		setVisible(true);
	}

	private static final long serialVersionUID = 3595460875461942003L;

	private Professor getSelectedProfessor(){
		return ((NamedPair<Professor>) professors.getSelectedItem()).data;
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
			stateService.getCurrentState().professors.update(toEdit);
		}
		setVisible(false);
	}

}
