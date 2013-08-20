package history;

import java.util.Collection;
import java.util.LinkedList;

import dataUpdateSystem.CustomerType;
import dataUpdateSystem.RegistrationCentral;
import dataUpdateSystem.Updatable;
import dataUpdateSystem.UpdateDescription;
import exceptions.StateIOException;

import services.StateService;
import state.State;

public class HistoryService implements Updatable{
	private static int MAXIMUM_HISTORY_SIZE = 20;
	
	private HistoryItem current;
	private LinkedList<HistoryItem> history;
	private static HistoryService _instance = new HistoryService();
	
	public static HistoryService getInstance(){
		return _instance;
	}
	
	private HistoryService(){
		RegistrationCentral.signIn(this, CustomerType.Service);
		history = new LinkedList<HistoryItem>();
		current = null;
	}
	
	public void registerChange(String desc){
		addState(current, desc);
		current = new HistoryItem(history.size(), StateService.getInstance().getCurrentState());
	}

	private void addState(HistoryItem obj, String desc){
		if(obj != null){
			obj.registerModification(desc);
			history.add(obj);
			trimList();
		}
	}
	
	public void goToSelectedState(HistoryItem selected) throws StateIOException{
		while(!history.isEmpty() && history.peekLast() != selected)
			history.pollLast();
		current = history.pollLast();
		StateService.getInstance().setCurrentState(current.getState());
	}
	
	private void trimList(){
		while(history.size() > MAXIMUM_HISTORY_SIZE)
			history.pollFirst();
	}

	private boolean currentStateDifferent(){
		StateService service = StateService.getInstance();
		if(!service.hasValidState()) return false;
		if(this.current == null) return true;
		State current = service.getCurrentState();
		return !current.getSignature().equals(this.current.getStateSignature());
	}
	
	public void onDataUpdate(UpdateDescription desc) {
		if(currentStateDifferent()){
			history.clear();
			this.current = new HistoryItem(0, StateService.getInstance().getCurrentState());
		}
	}

	public Collection<HistoryItem> getAllItens() {
		return history;
	}
}

