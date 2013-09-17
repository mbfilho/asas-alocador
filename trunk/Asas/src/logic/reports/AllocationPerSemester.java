package logic.reports;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import logic.html.HtmlDocument;
import logic.services.ClassService;
import data.persistentEntities.Class;

public class AllocationPerSemester {
	private TreeMap<Integer, List<Class>> classesPerSemester;
	private boolean isCc;
	
	public AllocationPerSemester(boolean cc){
		isCc = cc;
		classesPerSemester = new TreeMap<Integer, List<Class>>();
		createReport();
	}
	
	private void createReport(){
		ClassService classService = ClassService.createServiceFromCurrentState();
		
		for(Class c : classService.all()){
			addToMapping(getSemesterForReport(c), c);
		}
	}
	
	private void addToMapping(int semester, Class c) {
		if(semester == -1) return;
		if(!classesPerSemester.containsKey(semester))
			classesPerSemester.put(semester, new LinkedList<Class>());
		
		classesPerSemester.get(semester).add(c);
	}

	private int getSemesterForReport(Class theClass){
		if(isCc) return theClass.getCcSemester();
		else return theClass.getEcSemester();
	}
	
	public HtmlDocument getHtmlRepresentation(){
		return new PerSemesterHtmlCreator(classesPerSemester).getHtmlRepresentation();
	}
	
}
