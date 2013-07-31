package classrooms;

import statePersistence.StateService;
import basic.Classroom;
import java.awt.Dimension;

public class AddClassroomFrame extends ClassroomFramePattern {
	private static final long serialVersionUID = -8927378997497532099L;
	private StateService stateService;
	
	public AddClassroomFrame() {
		super();
		setTitle("Adicionar Sala");
		stateService = StateService.getInstance();
	}

	public void onOkButton() {
		Classroom room = getClassroomFromFields();
		stateService.getCurrentState().classrooms.addInOrder(room);
	}

}
