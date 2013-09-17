package logic.services;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Timer;

import utilities.CollectionUtil;

import logic.dataUpdateSystem.DataUpdateCentral;

import data.DataValidation;
import data.configurations.ExcelPreferences;
import data.configurations.StateDescription;
import data.persistentEntities.Class;
import data.persistentEntities.State;
import data.readers.excelReaders.ExcelClassReader;
import data.readers.excelReaders.ExcelProfessorReader;
import data.readers.excelReaders.WorkbookReader;
import data.readers.fileReaders.FileClassRoomReader;
import data.repository.Repository;
import data.writers.SingleObjectFileWriter;
import data.writers.Writer;





import exceptions.StateIOException;
import exceptions.WritingException;

public class StateService {
	private final State currentState;
	private boolean hasValidState;
	
	private final String fileName = String.format("configs%ssavedStates.config", File.separator);
	private List<StateDescription> states;
	private static StateService _instance = null;
	
	public static StateService getInstance(){
		if(_instance == null) _instance = new StateService();
		return _instance;
	}
	
	@SuppressWarnings("unchecked")
	private StateService(){
		currentState = new State();
		hasValidState = false;
		
		states = new LinkedList<StateDescription>();
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
			states = (List<StateDescription>) in.readObject();
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
					saveCurrentState();
				} catch (StateIOException e1) {
				}
			}
		});
		timerToSave.setRepeats(true);
		timerToSave.start();
	}

	public boolean hasValidState(){
		return hasValidState;
	}
	
	public State getCurrentState(){
		return currentState;
	}
	
	private void setState(State theState){
		hasValidState = true;
		currentState.allowedWarnings = theState.allowedWarnings;
		currentState.classes = theState.classes;
		currentState.classrooms = theState.classrooms;
		currentState.description = theState.description;
		currentState.excelFileHash = theState.excelFileHash;
		currentState.excelPrefs = theState.excelPrefs;
		currentState.professors = theState.professors;
	}
	
	public synchronized void setCurrentState(StateDescription s) throws StateIOException{
		setState(loadState(s));
		DataUpdateCentral.registerUpdate("Novo estado");
	}
	
	public List<StateDescription> allStates(){
		return states;
	}
	
	private void flushDescriptions() throws StateIOException{
		try{
			Writer<List<StateDescription>> stateWriter = new SingleObjectFileWriter<List<StateDescription>>(fileName);
			stateWriter.Write(states);
		}catch(WritingException ex){
			throw new StateIOException("Ocorreu um erro ao salvar o estado atual.", ex);
		}
	}
	
	private void flushState(State s) throws StateIOException{
		Writer<State> stateWriter = new SingleObjectFileWriter<State>(s.description.getFile());
		try {
			stateWriter.Write(s);
			ConfigurationService.getInstance().saveClassrooms();
		} catch (WritingException e) {
			e.printStackTrace();
			throw new StateIOException("Ocorreu um erro ao salvar o estado atual.", e);
		}
	}

	public synchronized void saveCurrentState() throws StateIOException {
		if(hasValidState())
			flushState(currentState);
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
	
	private void completeSwitchingToExcelState(State read) throws IOException, StateIOException{
		setState(read);
		states.add(currentState.description);
		
		saveCurrentState();
		flushDescriptions();
		
		DataUpdateCentral.registerUpdate("Estado carregado do excel");
		ConfigurationService.getInstance().saveExcelPreferences(read.excelPrefs);
	}
	
	public List<String> loadStateFromExcel(ExcelPreferences prefs, StateDescription desc) throws StateIOException{
		try{
			State theNewState = new State(desc);
			theNewState.setExcelPreferences(prefs);
			
			WorkbookReader excelReader = new WorkbookReader(prefs.getFileLocation());
			ExcelClassReader classReader = new ExcelClassReader(theNewState, prefs, excelReader);
			new ExcelProfessorReader(theNewState, prefs, excelReader).read();
			new FileClassRoomReader(theNewState).read();
			DataValidation<Repository<Class>> validation = classReader.read();
			
			completeSwitchingToExcelState(theNewState);
			return validation.validation;
		}catch(Exception ex){
			ex.printStackTrace();
			return CollectionUtil.createList("Não foi possível carregar o novo estado a partir do excel. O estado anterior foi restaurado.");
		}
	}

	public synchronized void setCurrentState(State state) {
		setState(state);
		DataUpdateCentral.registerUpdate("Novo estado setado");
	}
	
	public void updateExcelHash(){
		currentState.updateExcelHash();
	}
}
