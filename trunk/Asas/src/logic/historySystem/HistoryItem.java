package logic.historySystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import data.persistentEntities.State;
import data.writers.SingleObjectFileWriter;
import data.writers.Writer;

import exceptions.StateIOException;
import exceptions.WritingException;


public class HistoryItem {
	private String description;
	private File stateLocation;
	private Object stateSignature;
	private Date modificationTime;
	private static String HISTORY_FILES_LOCATION = "undoStates";
	private Writer<State> stateWriter;
	
	public HistoryItem(int id, State state){
		stateLocation = new File(String.format("%s%stemp%d", HISTORY_FILES_LOCATION, File.separator, id));
		stateWriter = new SingleObjectFileWriter<State>(stateLocation);
		stateSignature = state.getSignature();
		try {
			stateWriter.Write(state);
		} catch (WritingException e) {
			e.printStackTrace();
		}
	}
	

	@SuppressWarnings("resource")
	public State getState() throws StateIOException {
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new FileInputStream(stateLocation));
			State loaded = (State) in.readObject();
			in.close();
			return loaded;
		} catch (IOException e) {
			e.printStackTrace();
			throw new StateIOException("Erro ao acessar o arquivo temporÃ¡rio", e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new StateIOException("Erro ao processar o arquivo temporÃ¡rio. Ele estÃ¡, possivelmente, com dados antigos (de outra versÃ£o)", e);
		}
	}
	
	public Object getStateSignature(){
		return stateSignature;
	}

	public String getDescription() {
		return description;
	}

	public void registerModification(String desc) {
		description = desc;
		modificationTime = new Date();
	}
	
	public String getModificationTime(){
		return new SimpleDateFormat("HH:mm:ss").format(modificationTime);
	}
}
