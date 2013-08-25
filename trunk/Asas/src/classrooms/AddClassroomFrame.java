package classrooms;

import data.persistentEntities.Classroom;

public class AddClassroomFrame extends ClassroomFramePattern {
	private static final long serialVersionUID = -8927378997497532099L;
	
	public AddClassroomFrame() {
		super();
		setTitle("Adicionar Sala");
	}

	public void onOkButton() {
		Classroom room = getClassroomFromFields();
		classroomService.add(room);
	}

}
