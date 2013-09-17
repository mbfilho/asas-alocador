package logic.services;

import java.util.Comparator;
import java.util.TreeMap;
import java.util.Map.Entry;

import logic.dto.WorkloadReport;
import logic.dto.WorkloadReportList;

import data.persistentEntities.Class;
import data.persistentEntities.Professor;
import data.persistentEntities.State;


public class ProfessorWorkLoadService {
	private ClassService classService;
	private ProfessorService profService;
	private TreeMap<Professor, Double> profMapping;
	
	public static ProfessorWorkLoadService createServiceFromCurrentState(){
		return new ProfessorWorkLoadService(StateService.getInstance().getCurrentState());
	}
	
	public ProfessorWorkLoadService(State dataState){
		classService = new ClassService(dataState);
		profService = new ProfessorService(dataState);
	}
	
	public WorkloadReportList calculateProfessorWorkload(){
		profMapping = new TreeMap<Professor, Double>(new Comparator<Professor>() {
			public int compare(Professor p1, Professor p2) {
				return p1.getName().compareTo(p2.getName());
			}
		});
		WorkloadReportList result = new WorkloadReportList();
		
		for(Class c : classService.all()){
			for(Professor p : c.getProfessors()){
				double value = 0;
				value = c.getCh() / c.getProfessors().size();
				addWorkload(p, value);
			}
		}
		
		handleNonAllocatedProfessors();
		
		for(Entry<Professor, Double> pair : profMapping.entrySet())
			result.add(new WorkloadReport(pair.getKey(), pair.getValue()));
		
		return result;
	}
	
	private void addWorkload(Professor prof, double ch){
		Double currentValue = new Double(0);
		if(profMapping.containsKey(prof)) currentValue = profMapping.get(prof);
		currentValue += ch;
		profMapping.put(prof, currentValue);	
	}
	
	private void handleNonAllocatedProfessors(){
		for(Professor p : profService.all())
			addWorkload(p, 0);
	}
}
