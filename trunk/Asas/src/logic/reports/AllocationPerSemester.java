package logic.reports;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import utilities.CollectionUtil;
import utilities.StringUtil;

import logic.ColorPoolForNames;
import logic.html.CssConstants;
import logic.html.HTag;
import logic.html.HtmlDocument;
import logic.html.HtmlElement;
import logic.html.TableTag;
import logic.html.TdTag;
import logic.html.TrTag;
import logic.services.ClassService;
import data.persistentEntities.Class;
import data.persistentEntities.Classroom;
import data.persistentEntities.SlotRange;

public class AllocationPerSemester {
	private TreeMap<Integer, List<Class>> classesPerSemester;
	private boolean isCc;
	
	public AllocationPerSemester(boolean cc){
		isCc = cc;
		classesPerSemester = new TreeMap<Integer, List<Class>>();
		createReport();
	}
	
	private void createReport(){
		ClassService classService = new ClassService();
		
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
