package logic.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileBackupingService {

	private String getNameWithoutExtension(File arq){
		int pointIndex = arq.getName().lastIndexOf(".");
		if(pointIndex != -1)
			return arq.getName().substring(0, pointIndex);
		return arq.getName();
	}
	
	private String getExtension(File arq){
		int pointIndex = arq.getName().lastIndexOf(".");
		if(pointIndex != -1)
			return arq.getName().substring(pointIndex + 1);
		return "";
	}
	
	public File getBackupFile(File original){
		String name = getNameWithoutExtension(original), extension = getExtension(original);
		int index = 1;
		while(true){
			File backup = new File(String.format("%s%s%s(%d).%s", original.getParent(), File.separator, name, index, extension));
			if(!backup.exists())
				return backup;
			++index;
		}
	}
	
	public void createBackup(File original, File copy) throws IOException{
		FileInputStream in = new FileInputStream(original);
		FileOutputStream out = new FileOutputStream(copy);
		byte buffer[] = new byte[2048];
		int read;
		
		while((read = in.read(buffer)) != -1){
			out.write(buffer, 0, read);
		}
		out.close();
		in.close();
	}
	
}
