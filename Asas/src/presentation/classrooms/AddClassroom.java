package presentation.classrooms;

import data.persistentEntities.Classroom;

public class AddClassroom extends ClassroomLayout {
	private static final long serialVersionUID = -8927378997497532099L;
	
	public AddClassroom() {
		super();
		setTitle("Adicionar Sala");
	}

	public void onOkButton() {
		Classroom room = getClassroomFromFields();
		classroomService.add(room);
	}

}
