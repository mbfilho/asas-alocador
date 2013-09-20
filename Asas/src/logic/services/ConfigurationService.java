package logic.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.List;

import data.configurations.ExcelPreferences;
import data.persistentEntities.Classroom;

public class ConfigurationService {
	private static final String EXCEL_PREFERENCES_FILENAME = String.format("configs%sexcelPreferences.config", File.separator);
	public static final String CLASSROOMS_FILENAME = String.format("configs%sclassrooms.in", File.separator);

	private static ConfigurationService _instance;

	public static ConfigurationService getInstance(){
		if(_instance == null)
			_instance = new ConfigurationService();
		return _instance;
	}

	private ConfigurationService(){

	}

	public void saveExcelPreferences(ExcelPreferences prefs) throws IOException{
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(EXCEL_PREFERENCES_FILENAME));
		out.writeObject(prefs);
		out.close();
	}

	public ExcelPreferences loadExcelPreferencesOrDefault(){
		ExcelPreferences prefs = ExcelPreferences.defaultPreferences();
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(EXCEL_PREFERENCES_FILENAME));
			prefs = (ExcelPreferences) in.readObject();
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return prefs;
	}

	public void saveClassrooms(){
		List<Classroom> rooms = StateService.getInstance().getCurrentState().classrooms.all();
		try {
			PrintWriter writer = new PrintWriter(new File(CLASSROOMS_FILENAME));
			for(Classroom room : rooms){
				int 	external = room.isExternal() ? 1 : 0;
				writer.println(String.format("%s#%d#%d#", room.getName(), room.getCapacity(), external));
			}
			writer.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
