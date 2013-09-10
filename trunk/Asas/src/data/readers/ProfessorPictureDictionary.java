package data.readers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class ProfessorPictureDictionary {
	private HashMap<String,String> mapping;
	private final String DEFAULT_LOGIN = "default";
	
	public ProfessorPictureDictionary(){
		mapping = new HashMap<String, String>();
		mapping.put(DEFAULT_LOGIN, "");
		
		loadPictures();
	}
	
	private void loadPictures(){
		File mapFile = new File(String.format("configs%sprofsPics.config", File.separator));
		try {
			Scanner sc = new Scanner(mapFile);
			while(sc.hasNext()){
				String login = sc.next(), picture = sc.next();
				mapping.put(login, picture);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private String getLogin(String mail){
		if(mail.contains("@")) 
			return mail.split("@")[0];
		return mail;
	}
	
	public String getPicture(String email){
		String login = getLogin(email);
		if(mapping.containsKey(login))
			return mapping.get(login);
		return mapping.get(DEFAULT_LOGIN);
	}
}
