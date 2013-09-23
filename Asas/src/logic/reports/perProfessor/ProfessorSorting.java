package logic.reports.perProfessor;

public class ProfessorSorting {
	private boolean sortByName;
	private boolean sortByLoad;
	private boolean sortAscending;
	
	public ProfessorSorting(boolean name, boolean load, boolean asc){
		sortByName = name;
		sortByLoad = load;
		sortAscending = asc;
	}
	
	public boolean isSortingByName(){
		return sortByName;
	}
	
	public boolean isSortingByLoad(){
		return sortByLoad;
	}
	
	public boolean isSortingAscending(){
		return sortAscending;
	}
}
