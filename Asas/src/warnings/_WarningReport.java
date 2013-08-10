package warnings;

import java.util.Vector;

import classEditor.NamedPair;

public class _WarningReport {

	private Vector<NamedPair<Vector<_Warning>>> reports;
	
	public _WarningReport(){
		reports = new Vector<NamedPair<Vector<_Warning>>>();
	}
	
	public void addReport(String title, Vector<_Warning> wars){
		reports.add(new NamedPair<Vector<_Warning>>(title, wars));
	}
	
	public Vector<NamedPair<Vector<_Warning>>> getAllReports(){
		return reports;
	}
}
