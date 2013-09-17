package logic.dto;

import data.persistentEntities.Professor;

public class WorkloadReport {
	public Professor professor;
	public double workload;
	
	public WorkloadReport(){}
	
	public WorkloadReport(Professor prof, double sum){
		professor = prof;
		workload = sum;
	}
}
