package logic.dto;
import java.util.LinkedList;

import data.persistentEntities.Professor;

public class WorkloadReportList extends LinkedList<WorkloadReport>{
	private static final long serialVersionUID = 815583732825940338L;

	public WorkloadReport getReportFor(Professor prof) {
		for(WorkloadReport report : this){
			if(report.professor == prof)
				return report;
		}
		
		return null;
	}
	
	
}
