package warnings;

import java.util.List;

import warnings.types.Warning;


public class WarningReport {
	private String title;
	private List<Warning> warnings;
	
	public WarningReport(String title, List<Warning> wars){
		this.title = title;
		this.warnings = wars;
	}
	
	public List<Warning> getAllWarnings(){
		return warnings;
	}
	
	public String getTitle(){
		return title;
	}
}
