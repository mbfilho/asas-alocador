package logic.services;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.Map.Entry;

import logic.dto.ProfessorWorkload;

import data.persistentEntities.Class;
import data.persistentEntities.Professor;


public class ReportService {
	private ClassService classService;
	
	public ReportService(){
		classService = new ClassService();
	}
	
	public List<ProfessorWorkload> calculateProfessorWorkload(){
		TreeMap<Professor, Double> mapa = new TreeMap<Professor, Double>(new Comparator<Professor>() {
			public int compare(Professor p1, Professor p2) {
				return p1.getName().compareTo(p2.getName());
			}
		});
		List<ProfessorWorkload> result = new LinkedList<ProfessorWorkload>();
		
		for(Class c : classService.all()){
			int professorCnt = c.getProfessors().size();
			
			for(Professor p : c.getProfessors()){
				Double currentValue = new Double(0);
				if(mapa.containsKey(p)) currentValue = mapa.get(p);
				currentValue += c.getCh() / professorCnt;
				mapa.put(p, currentValue);
			}
		}
		
		for(Entry<Professor, Double> pair : mapa.entrySet())
			result.add(new ProfessorWorkload(pair.getKey(), pair.getValue()));
		
		return result;
	}
}
