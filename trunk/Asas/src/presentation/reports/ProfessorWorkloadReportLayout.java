package presentation.reports;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import logic.dto.WorkloadReport;
import logic.services.ProfessorWorkLoadService;
import utilities.DisposableOnEscFrame;

public class ProfessorWorkloadReportLayout extends DisposableOnEscFrame{
	private static final long serialVersionUID = -1174604750699309229L;
	
	public ProfessorWorkloadReportLayout(){
		setTitle("Relatório de Carga Horária");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().add(new JScrollPane(createReportTable()));
		setVisible(true);
	}
	
	private JTable createReportTable(){
		ProfessorWorkLoadService reportService = ProfessorWorkLoadService.createServiceFromCurrentState();
		List<WorkloadReport> workload = reportService.calculateProfessorWorkload();
		Object rowData[][] = new Object[workload.size()][3];
		int row = 0;
		for(WorkloadReport load : workload){
			rowData[row][0] = load.professor.getName();
			rowData[row][1] = load.workload;
			rowData[row][2] = load.professor.getLastSemesterWorkload();
			row++;
		}
		
		JTable table = new JTable(rowData, new String[]{"Professor", "Carga Atual", "Carga Anterior"}){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int col){
				return false;
			}
		};
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
		table.setRowSorter(sorter);
		return table;
	}

}
