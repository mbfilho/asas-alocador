package logic.services;

import data.persistentEntities.ExcelMetadata;
import data.persistentEntities.Professor;
import data.persistentEntities.State;
import data.repository.Repository;


public class ProfessorService extends BasicDataAccessService<Professor> {

	public static ProfessorService createServiceFromCurrentState(){
		return new ProfessorService(StateService.getInstance().getCurrentState());
	}
	
	public ProfessorService(State dataState){
		super(dataState);
	}
	
	protected Repository<Professor> list(){
		return getState().professors;
	}
	
	public boolean exists(String profName) {
		return list().exists(profName);
	}

	public void setPreviousLoad(String profName, double workload) {
		Professor prof = getByName(profName);
		if(prof != null)
			prof.setLastSemesterWorkload(workload);
	}
	
	private int getLastUsedRow(){
		int lastRow = 0;
		for(Professor p : list())
			lastRow = Math.max(lastRow, p.getExcelMetadata().getRow());
		return lastRow;
	}
	
	public void add(Professor prof){
		if(prof.getExcelMetadata() == null){
			prof.setExcelMetadata(new ExcelMetadata(getLastUsedRow() + 1));
		}
		super.add(prof);
	}
}
