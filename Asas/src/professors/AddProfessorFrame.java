package professors;

import data.persistentEntities.Professor;

public class AddProfessorFrame extends ProfessorFramePattern{
	private static final long serialVersionUID = 2807943615463738926L;
	
	public AddProfessorFrame() {
		setVisible(true);
		setTitle("Adicionar professor");
	}

	protected void onOkButton() {
		Professor toAdd = new Professor(getNameText(), getEmailText(), getCargoText(),  getDptoText(), isTemp(), isAway());
		professorService.add(toAdd);
		setVisible(false);
	}
}
