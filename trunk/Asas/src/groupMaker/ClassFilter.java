package groupMaker;
import basic.Class;
import basic.Professor;

public class ClassFilter {

	private int semester;
	private Professor professor;
	private String area;
	
	public ClassFilter(String semester, Professor prof, String area){
		setSemester(semester);
		setProfessor(prof);
		setArea(area);
	}
	
	public ClassFilter(){
		this(null, null, null);
	}
	
	public void setSemester(String semester) {
		this.semester = semester == null ? -1 : Integer.parseInt(semester);
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public boolean isInRole(Class c){
		if(professor != null &&  !c.getProfessors().contains(professor)) return false;
		if(semester != -1 && c.getCcSemester() != semester && c.getEcSemester() != semester) return false;
		return true;
	}
}
