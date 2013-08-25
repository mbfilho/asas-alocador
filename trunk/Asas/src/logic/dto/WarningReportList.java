package logic.dto;
import java.util.LinkedList;
import java.util.List;


public class WarningReportList extends LinkedList<WarningReport>{

	private static final long serialVersionUID = -3188614102447240893L;

	public void addReport(WarningReport report){
		add(report);
	}
	
	public List<WarningReport> getAllReports(){
		return this;
	}
}
