package warnings;

import java.util.Vector;

import classEditor.NamedPair;

public class WarningReport {

	private Vector<NamedPair<Vector<Warning>>> reports;
	
	public WarningReport(){
		reports = new Vector<NamedPair<Vector<Warning>>>();
	}
	
	public void addReport(String title, Vector<Warning> wars){
		reports.add(new NamedPair<Vector<Warning>>(title, wars));
	}
	
	public Vector<NamedPair<Vector<Warning>>> getAllReports(){
		return reports;
	}
}
