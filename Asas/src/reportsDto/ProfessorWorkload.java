package reportsDto;

import basic.Professor;

public class ProfessorWorkload {
	public Professor professor;
	public double workload;
	
	public ProfessorWorkload(){}
	
	public ProfessorWorkload(Professor prof, double sum){
		professor = prof;
		workload = sum;
	}
}
