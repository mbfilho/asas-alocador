package history;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import exceptions.StateIOException;

import state.State;

public class HistoryItem {
	private String description;
	private File stateLocation;
	private Object stateSignature;
	private Date modificationTime;
	private static String HISTORY_FILES_LOCATION = "undoStates";
	
	public HistoryItem(int id, State state){
		stateLocation = new File(String.format("%s%stemp%d", HISTORY_FILES_LOCATION, File.separator, id));
		stateSignature = state.getSignature();
		saveState(state);
	}
	
	private void saveState(State state){
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(stateLocation));
			out.writeObject(state);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public State getState() throws StateIOException {
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new FileInputStream(stateLocation));
			State loaded = (State) in.readObject();
			in.close();
			return loaded;
		} catch (IOException e) {
			e.printStackTrace();
			throw new StateIOException("Erro ao acessar o arquivo temporário", e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new StateIOException("Erro ao processar o arquivo temporário. Ele está, possivelmente, com dados antigos (de outra versão)", e);
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
		return new SimpleDateFormat("HH:mm").format(modificationTime);
	}
	
	
}
