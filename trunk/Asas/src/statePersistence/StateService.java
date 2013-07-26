package statePersistence;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.swing.Timer;

import exceptions.StateIOException;

public class StateService {
	private State currentState = null;
	
	private final String fileName = "config.asas";
	private Vector<StateDescription> states;
	private static StateService _instance = null;
	
	public static StateService getInstance(){
		if(_instance == null) _instance = new StateService();
		return _instance;
	}
	
	private StateService(){
		states = new Vector<StateDescription>();
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
			Object read;
			while((read = in.readObject()) != null) states.add((StateDescription) read);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		configureTimerToSave();
	}
	
	private void configureTimerToSave(){
		Timer timerToSave = new Timer(5000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					save();
				} catch (StateIOException e1) {
				}
			}
		});
		timerToSave.setRepeats(true);
		timerToSave.start();
	}

	public boolean hasValidState(){
		return currentState != null;
	}
	
	public State getCurrentState(){
		return currentState;
	}
	
	public synchronized void setCurrentState(StateDescription s) throws StateIOException{
		currentState =  loadState(s);
	}
	
	public Vector<StateDescription> allStates(){
		return states;
	}
	
	public void saveNewState(State s) throws StateIOException{
		states.add(s.description);
		flushState(s);
		flushDescriptions();
	}
	
	public void remove(State s) throws StateIOException{
		states.remove(s);
		flushDescriptions();
	}
	
	private void flushDescriptions() throws StateIOException{
		try{
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
			for(StateDescription desc : states) out.writeObject(desc);
			out.writeObject(null);
			out.close();
		}catch(Exception ex){
			throw new StateIOException("Ocorreu um erro ao salvar o estado atual.", ex);
		}
	}
	
	private void flushState(State s) throws StateIOException{
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(s.description.getFile()));
			out.writeObject(s);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			throw new StateIOException("Ocorreu um erro ao salvar o estado atual.", e);
		}
	}

	public boolean existName(String name){
		for(StateDescription desc : states) if(desc.getName().equals(name)) return true;
		return false;
	}
	
	//flush
	public synchronized void save() throws StateIOException {
		if(getCurrentState() != null){
			flushState(getCurrentState());
		}
	}

	public State loadState(StateDescription stateDescription) throws StateIOException {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(stateDescription.getFile()));
			State tmp = (State) in.readObject();
			in.close();
			return tmp; 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new StateIOException("Estado não encontrado", e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new StateIOException("Não foi possivel carregar o estado\"" + stateDescription.getName() + "\"", e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new StateIOException("Ocorreu um erro interno e não foi possivel carregar o estado\"" + stateDescription.getName() + "\"", e);
		}
	}
}
