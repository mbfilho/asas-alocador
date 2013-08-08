package services;

import java.util.Collection;
import java.util.Vector;

import basic.ElectiveClass;
import basic.ElectiveClassPreferences;
import basic.Professor;
import basic.SlotRange;
import data.Repository;
import data.SimpleRepository;
import statePersistence.StateService;
import utilities.StringUtil;

public class ElectivePreferencesService{

	private StateService stateService;
	
	public ElectivePreferencesService() {
		stateService = StateService.getInstance();
	}
	
	private Repository<ElectiveClassPreferences> list(){
		if(stateService.hasValidState()) return stateService.getCurrentState().electivePreferences;
		else return new SimpleRepository<ElectiveClassPreferences>();
	}
	
	public Collection<ElectiveClassPreferences> all(){
		return list().all();
	}
	
	public void add(ElectiveClassPreferences pref){
		list().addInOrder(pref);
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
