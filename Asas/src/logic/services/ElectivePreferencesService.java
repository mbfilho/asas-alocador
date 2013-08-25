package logic.services;

import java.util.Vector;

import data.persistentEntities.ElectiveClass;
import data.persistentEntities.ElectiveClassPreferences;
import data.persistentEntities.Professor;
import data.persistentEntities.SlotRange;
import data.repository.Repository;
import data.repository.SimpleRepository;


import utilities.StringUtil;

public class ElectivePreferencesService extends BasicService<ElectiveClassPreferences>{

	private StateService stateService;
	
	public ElectivePreferencesService() {
		stateService = StateService.getInstance();
	}
	
	protected Repository<ElectiveClassPreferences> list(){
		if(stateService.hasValidState()) return stateService.getCurrentState().electivePreferences;
		else return new SimpleRepository<ElectiveClassPreferences>();
	}
	
	public String getHtmlTableForPreferences(Iterable<ElectiveClassPreferences> prefs){
		String text = "<html><table cellspacing=\"0\" cellpadding=\"0\"><tr>";
		text += "<th>Turma</th><th>Alunos</th><th>Prof. 1</th><th>Prof. 2</th><th>Prof. 3</th>";
		text +=  "<th>Slot 1</th><th>Slot 2</th><th>Slot 3</th></tr>";
		
		for(ElectiveClassPreferences pref : prefs){
			text += "<tr style='border:solid thin'>";
			text +=  "<td>"+ pref.getName() + "</td>";
			text +=  "<td>" + pref.getStudentCount() + "</td>";
			for(Professor p : pref.getProfessors()) text += "<td>" + p.getName() + "</td>";
			for(int i = 0; i < 3 - pref.getProfessors().size(); ++i) text += "<td>-</td>";
			
			for(Vector<SlotRange> r : pref.getSlots()) text += "<td>" + StringUtil.joinListWithSeparator(r, "/") + "</td>";
			for(int i = 0; i < 3 - pref.getSlots().size(); ++i) text += "<td>-</td>";
			
			text += "</tr>";
		}
		text += "</table></html>";
		return text;
	}
	
	public boolean existPreference(ElectiveClass cls){
		for(ElectiveClassPreferences pref : all()) if(pref.getElectiveClass() == cls) return true;
		return false;
	}
	
}
